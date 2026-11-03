package com.bu.app.ritual

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.core.command.BuCommand
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.command.CommandStatus
import com.bu.core.command.IfThen
import com.bu.core.ledger.ContractDao
import com.bu.core.ledger.TaskWithTitle
import com.bu.core.outbox.OutboxProcessor
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuCooling
import com.bu.designsystem.theme.BuLayer
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuTextDim
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Dawn Push (locked plan §2) — the morning ritual, four beats, ≤60 seconds:
 * Wins → Front → If-Then recall → First move. Reads the contract sealed last
 * night; records one event; the first move becomes today's first spark.
 */
@HiltViewModel
class DawnViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
    taskDao: com.bu.core.ledger.TaskDao,
    contractDao: ContractDao,
) : ViewModel() {

    val todayIso: String = LocalDate.now().toString()
    val yesterdayIso: String = LocalDate.now().minusDays(1).toString()

    /** Yesterday's landed list — the Wins beat reads the ledger, never memory. */
    val wins: StateFlow<List<TaskWithTitle>> = combine(
        taskDao.observeDayTasks("BIG", yesterdayIso),
        taskDao.observeDayTasks("BLOCK", yesterdayIso),
    ) { big, blocks -> (big + blocks).filter { it.status == "DONE" || it.status == "PARTIAL" } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** Today's contract — the Front and the shield were sealed last night. */
    val contract: StateFlow<com.bu.core.ledger.DayContractRow?> =
        contractDao.observeByDate(todayIso)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    /** The Front's title, resolved from the ledger — never from memory. */
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val frontTitle: StateFlow<String?> = contract
        .flatMapLatest { row ->
            val frontId = row?.mainFrontTaskId
            if (frontId == null) {
                kotlinx.coroutines.flow.flowOf(null)
            } else {
                taskDao.observeDayTasks("BIG", todayIso)
                    .map { list -> list.firstOrNull { it.objectId == frontId }?.title }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun submit(command: BuCommand, onResult: (CommandReceipt) -> Unit = {}) {
        viewModelScope.launch {
            val receipt = gateway.submit(command)
            outboxProcessor.drain()
            onResult(receipt)
        }
    }

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun parseIfThen(raw: String?): IfThen? = raw?.let {
            runCatching { json.decodeFromString(IfThen.serializer(), it) }.getOrNull()
        }
    }
}

@Composable
fun DawnPushFlow(
    onDone: () -> Unit,
    viewModel: DawnViewModel = hiltViewModel(),
) {
    val wins by viewModel.wins.collectAsStateWithLifecycle()
    val contract by viewModel.contract.collectAsStateWithLifecycle()
    val frontTitle by viewModel.frontTitle.collectAsStateWithLifecycle()

    var step by remember { mutableIntStateOf(0) }
    var firstMove by remember { mutableStateOf("") }
    var recording by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDone,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = BuBackground) {
            AnimatedContent(
                targetState = step,
                transitionSpec = {
                    if (targetState > initialState) {
                        (slideInHorizontally { it } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()) togetherWith
                            (slideOutHorizontally { it } + fadeOut())
                    }
                },
                label = "dawn",
            ) { current ->
                when (current) {
                    0 -> DawnScaffold(
                        beatLabel = "DAWN PUSH · 1 of 4",
                        title = "Wins first",
                        subtitle = "Read from yesterday's ledger — evidence, not memory.",
                    ) {
                        if (wins.isEmpty()) {
                            Text(
                                "Yesterday's ledger holds no landed tallies. Today is fresh — the first one is free.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = BuTextDim,
                            )
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                wins.forEach { win ->
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = BuLayer,
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            "✓  ${win.title}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        DawnPrimaryButton("Continue") { step = 1 }
                    }

                    1 -> DawnScaffold(
                        beatLabel = "DAWN PUSH · 2 of 4",
                        title = "The Front",
                        subtitle = "One thing carries the day. Everything else serves it.",
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = BuLayer,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                if (frontTitle != null) {
                                    Text(frontTitle!!, style = MaterialTheme.typography.headlineSmall)
                                    Text(
                                        "This is the Main Front. Start there — the rest follows.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = BuTextDim,
                                    )
                                } else {
                                    Text(
                                        "No Front was set for today.",
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                    Text(
                                        "You can set it on NOW any moment. The day survives either way.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = BuTextDim,
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        DawnPrimaryButton("Continue") { step = 2 }
                    }

                    2 -> {
                        val ifThen = DawnViewModel.parseIfThen(contract?.ifThenJson)
                        DawnScaffold(
                            beatLabel = "DAWN PUSH · 3 of 4",
                            title = "The shield, recalled",
                            subtitle = "The if-then you sealed last night — seen once now, so it fires later.",
                        ) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = BuLayer,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    if (ifThen != null) {
                                        Text("If ${ifThen.obstacle.lowercase()}…", style = MaterialTheme.typography.titleLarge)
                                        Text("…then ${ifThen.plan}", style = MaterialTheme.typography.bodyLarge, color = BuMint)
                                    } else {
                                        Text(
                                            "No shield was set. That's fine — one can be added at tonight's close.",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = BuTextDim,
                                        )
                                    }
                                }
                            }
                            Spacer(Modifier.weight(1f))
                            DawnPrimaryButton("Continue") { step = 3 }
                        }
                    }

                    3 -> DawnScaffold(
                        beatLabel = "DAWN PUSH · 4 of 4",
                        title = "The first move",
                        subtitle = "The smallest piece of the Front you can do in the next hour. Optional — but it starts the day.",
                    ) {
                        OutlinedTextField(
                            value = firstMove,
                            onValueChange = { firstMove = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("e.g. Open the notes, read one page") },
                            singleLine = true,
                        )
                        Spacer(Modifier.weight(1f))
                        DawnPrimaryButton(if (recording) "Recording…" else "Record the dawn", enabled = !recording) {
                            recording = true
                            viewModel.submit(
                                BuCommand.RecordDawnPush(
                                    dateIso = viewModel.todayIso,
                                    firstMove = firstMove.trim().takeIf { it.isNotEmpty() },
                                ),
                            ) { receipt ->
                                recording = false
                                if (receipt.status == CommandStatus.COMMITTED) step = 4
                            }
                        }
                        TextButton(
                            onClick = {
                                recording = true
                                viewModel.submit(BuCommand.RecordDawnPush(dateIso = viewModel.todayIso)) { _ ->
                                    recording = false
                                    onDone()
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                        ) { Text("Skip the move — just record", color = BuTextDim) }
                    }

                    else -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 28.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Day started.", style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "The Front is waiting on NOW. Everything you decide today lands in the ledger.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = BuTextDim,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(Modifier.height(28.dp))
                        DawnPrimaryButton("To the day") { onDone() }
                    }
                }
            }
        }
    }
}

@Composable
private fun DawnScaffold(
    beatLabel: String,
    title: String,
    subtitle: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp)
            .padding(top = 72.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Text(beatLabel, style = MaterialTheme.typography.labelMedium, color = BuMint)
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = BuTextDim)
        content()
    }
}

@Composable
private fun DawnPrimaryButton(label: String, enabled: Boolean = true, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        color = if (enabled) BuMint else BuCooling.copy(alpha = 0.4f),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                label,
                style = MaterialTheme.typography.titleMedium,
                color = if (enabled) Color(0xFF0A0B0D) else BuTextDim,
            )
        }
    }
}
