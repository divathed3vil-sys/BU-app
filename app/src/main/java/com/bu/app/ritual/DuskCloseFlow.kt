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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
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
import com.bu.core.command.BigThreeSlotInput
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.command.CommandStatus
import com.bu.core.event.EventTypes
import com.bu.core.ledger.EventDao
import com.bu.core.ledger.TaskWithTitle
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuCooling
import com.bu.designsystem.theme.BuDanger
import com.bu.designsystem.theme.BuLayer
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuTextDim
import com.bu.identity.ritual.RitualHealth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Dusk Close (locked plan §2, §13 P2) — the nightly ritual, five steps:
 * Receipt → Offload → Triage (Do / Shrink / Kill) → Commit → Seal.
 * 60–90 seconds by design; one decision per screen; zero-guilt copy.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DuskViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
    taskDao: com.bu.core.ledger.TaskDao,
    eventDao: EventDao,
    private val clock: BuClock,
) : ViewModel() {

    /** The day being closed — after midnight (before 4 am) this is yesterday. */
    val closeDateIso: String = run {
        val zone = ZoneId.systemDefault()
        val now = java.time.LocalDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(clock.nowMs()),
            zone,
        )
        if (now.hour < 4) LocalDate.now(zone).minusDays(1) else LocalDate.now(zone)
    }.toString()

    val targetDateIso: String = LocalDate.parse(closeDateIso).plusDays(1).toString()

    /** Everything scheduled on the closed day, any class, any resting state. */
    val dayTasks: StateFlow<List<TaskWithTitle>> = combine(
        taskDao.observeDayTasks("BIG", closeDateIso),
        taskDao.observeDayTasks("BLOCK", closeDateIso),
        taskDao.observeDayTasks("SPARK", closeDateIso),
    ) { big, blocks, sparks -> big + blocks + sparks }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** The triage field: every item still open on the closed day. */
    val openTasks: StateFlow<List<TaskWithTitle>> =
        taskDao.observeOpenOnDate(closeDateIso)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** A seal performed tonight (this dusk window) — the ritual's done state. */
    val sealedTonight: StateFlow<Boolean> = eventDao.eventsSince(
        EventTypes.RITUAL_SEALED,
        duskWindowStartMs(),
    ).map { it.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    private fun duskWindowStartMs(): Long {
        val zone = ZoneId.systemDefault()
        val now = java.time.LocalDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(clock.nowMs()),
            zone,
        )
        val day = if (now.hour < 4) LocalDate.now(zone).minusDays(1) else LocalDate.now(zone)
        // The dusk window opens two hours before the ritual's natural close —
        // any seal after 20:00 (or after midnight, closing yesterday) counts.
        return day.atTime(20, 0).atZone(zone).toInstant().toEpochMilli()
    }

    fun submit(command: BuCommand, onResult: (CommandReceipt) -> Unit = {}) {
        viewModelScope.launch {
            val receipt = gateway.submit(command)
            outboxProcessor.drain()
            onResult(receipt)
        }
    }
}

private enum class Triage { TOMORROW, SHRINK, LET_GO }

@Composable
fun DuskCloseFlow(
    onDone: () -> Unit,
    viewModel: DuskViewModel = hiltViewModel(),
) {
    val dayTasks by viewModel.dayTasks.collectAsStateWithLifecycle()
    val openTasks by viewModel.openTasks.collectAsStateWithLifecycle()

    var step by remember { mutableIntStateOf(0) }
    var mood by remember { mutableStateOf<Int?>(null) }

    // Triage state — commands fire the moment a decision is made.
    var decisions by remember { mutableStateOf<Map<String, Triage>>(emptyMap()) }
    var shrinkMinutes by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    val draftTitles = remember { mutableStateOf(listOf<String>()) }

    // Commit state
    var slot0 by remember(draftTitles.value) { mutableStateOf(draftTitles.value.getOrNull(0) ?: "") }
    var slot1 by remember(draftTitles.value) { mutableStateOf(draftTitles.value.getOrNull(1) ?: "") }
    var slot2 by remember(draftTitles.value) { mutableStateOf(draftTitles.value.getOrNull(2) ?: "") }
    var front by remember { mutableIntStateOf(0) }
    var minimumDay by remember { mutableStateOf("") }
    var obstacle by remember { mutableStateOf<String?>(null) }
    var ifThenPlan by remember { mutableStateOf("") }
    var ceiling by remember { mutableIntStateOf(2) }
    var sealing by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDone,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BuBackground,
        ) {
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
                label = "dusk",
            ) { current ->
                when (current) {
                    0 -> ReceiptStep(
                        dayTasks = dayTasks,
                        mood = mood,
                        onMood = { mood = it },
                        onNext = { step = 1 },
                    )

                    1 -> OffloadStep(
                        onCaptured = { text ->
                            if (text.isNotBlank()) {
                                viewModel.submit(BuCommand.SaveCapture(text = text, captureSource = "DUSK_OFFLOAD"))
                            }
                            step = 2
                        },
                        onSkip = { step = 2 },
                    )

                    2 -> TriageStep(
                        openTasks = openTasks,
                        decisions = decisions,
                        shrinkMinutes = shrinkMinutes,
                        onDecide = { task, decision ->
                            decisions = decisions + (task.objectId to decision)
                            when (decision) {
                                Triage.TOMORROW -> {
                                    draftTitles.value = draftTitles.value + task.title
                                    viewModel.submit(
                                        BuCommand.TallyTask(task.objectId, "CARRIED", viewModel.targetDateIso),
                                    )
                                }

                                Triage.SHRINK -> Unit // committed from the minutes chips

                                Triage.LET_GO -> viewModel.submit(BuCommand.KillTask(task.objectId))
                            }
                        },
                        onShrinkTo = { task, minutes ->
                            shrinkMinutes = shrinkMinutes + (task.objectId to minutes)
                            draftTitles.value = draftTitles.value + task.title
                            viewModel.submit(
                                BuCommand.ShrinkTask(
                                    taskId = task.objectId,
                                    toDateIso = viewModel.targetDateIso,
                                    newEstimateMinutes = minutes,
                                ),
                            )
                        },
                        onNext = { step = 3 },
                    )

                    3 -> CommitStep(
                        slot0 = slot0,
                        slot1 = slot1,
                        slot2 = slot2,
                        front = front,
                        minimumDay = minimumDay,
                        obstacle = obstacle,
                        ifThenPlan = ifThenPlan,
                        ceiling = ceiling,
                        sealing = sealing,
                        error = error,
                        onSlot0 = { slot0 = it },
                        onSlot1 = { slot1 = it },
                        onSlot2 = { slot2 = it },
                        onFront = { front = it },
                        onMinimumDay = { minimumDay = it },
                        onObstacle = { obstacle = it },
                        onIfThenPlan = { ifThenPlan = it },
                        onCeiling = { ceiling = it },
                        onSeal = {
                            val titles = listOf(slot0, slot1, slot2)
                            val filled = titles.withIndex().filter { it.value.isNotBlank() }.map { it.index }
                            val chosen = if (front in filled) front else filled.firstOrNull()
                            val slots = filled.map { i ->
                                BigThreeSlotInput(title = titles[i].trim(), isMainFront = i == chosen)
                            }
                            if (obstacle != null && ifThenPlan.isBlank()) {
                                error = "The if-then needs its plan — what will you do when it shows up?"
                                return@CommitStep
                            }
                            sealing = true
                            viewModel.submit(
                                BuCommand.SealTomorrow(
                                    closeDateIso = viewModel.closeDateIso,
                                    targetDateIso = viewModel.targetDateIso,
                                    slots = slots,
                                    minimumDay = minimumDay.trim().takeIf { it.isNotEmpty() },
                                    obstacle = obstacle?.takeIf { ifThenPlan.isNotBlank() },
                                    ifThenPlan = ifThenPlan.trim().takeIf { it.isNotEmpty() && obstacle != null },
                                    ceilingRung = ceiling,
                                    mood = mood,
                                ),
                            ) { receipt ->
                                sealing = false
                                when (receipt.status) {
                                    CommandStatus.COMMITTED -> step = 4
                                    CommandStatus.REJECTED -> error = when (receipt.reason) {
                                        "EXACTLY_ONE_MAIN_FRONT" -> "Mark exactly one line as the Front."
                                        "MAX_THREE" -> "Tomorrow carries at most three lines."
                                        "IF_THEN_NEEDS_BOTH" -> "The if-then needs both halves — or neither."
                                        else -> "BU could not seal that yet."
                                    }

                                    else -> error = "BU could not seal that yet."
                                }
                            }
                        },
                    )

                    else -> SealedStep(
                        targetDate = viewModel.targetDateIso,
                        onDone = onDone,
                    )
                }
            }
        }
    }
}

@Composable
private fun DuskScaffold(
    stepLabel: String,
    title: String,
    subtitle: String,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp)
            .padding(top = 72.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Text(stepLabel, style = MaterialTheme.typography.labelMedium, color = BuMint)
        Text(title, style = MaterialTheme.typography.headlineMedium)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = BuTextDim)
        content()
    }
}

/** Step 1 — Receipt: auto-filled from the ledger, one tap for the mood. */
@Composable
private fun ReceiptStep(
    dayTasks: List<TaskWithTitle>,
    mood: Int?,
    onMood: (Int?) -> Unit,
    onNext: () -> Unit,
) {
    val planned = dayTasks.size
    val landed = dayTasks.count { it.status == "DONE" || it.status == "PARTIAL" }
    val carried = dayTasks.count { it.status == "OPEN" && it.movedCount > 0 }
    val receipt = RitualHealth.Receipt(
        planned = planned,
        landed = landed,
        carried = carried,
        letGo = 0,
    )

    DuskScaffold(
        stepLabel = "DUSK CLOSE · 1 of 5",
        title = "The day, as it landed",
        subtitle = "Read straight from the ledger — nothing to type.",
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = BuLayer,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(receipt.headline(), style = MaterialTheme.typography.titleLarge)
                if (planned - landed > 0) {
                    Text(
                        "${planned - landed} still open — the next step decides each one, kindly.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BuTextDim,
                    )
                }
            }
        }

        Text("How did the day feel?", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items((1..5).toList()) { value ->
                FilterChip(
                    selected = mood == value,
                    onClick = { onMood(if (mood == value) null else value) },
                    label = { Text(RitualHealth.moodLabel(value)) },
                )
            }
        }

        Spacer(Modifier.weight(1f))
        DuskPrimaryButton("Continue", onClick = onNext)
    }
}

/** Step 2 — Offload: empty the head before sleep. One field, skippable. */
@Composable
private fun OffloadStep(onCaptured: (String) -> Unit, onSkip: () -> Unit) {
    var text by remember { mutableStateOf("") }

    DuskScaffold(
        stepLabel = "DUSK CLOSE · 2 of 5",
        title = "Empty your head",
        subtitle = "Anything still circling — a thought, a task, a worry. BU holds it; you sleep.",
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("It can be one word. That counts.") },
            minLines = 3,
        )
        Spacer(Modifier.weight(1f))
        DuskPrimaryButton("Capture & continue") { onCaptured(text) }
        TextButton(onClick = onSkip, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Nothing circling — skip", color = BuTextDim)
        }
    }
}

/** Step 3 — Triage: every open item gets one of the three honest exits. */
@Composable
private fun TriageStep(
    openTasks: List<TaskWithTitle>,
    decisions: Map<String, Triage>,
    shrinkMinutes: Map<String, Int>,
    onDecide: (TaskWithTitle, Triage) -> Unit,
    onShrinkTo: (TaskWithTitle, Int) -> Unit,
    onNext: () -> Unit,
) {
    DuskScaffold(
        stepLabel = "DUSK CLOSE · 3 of 5",
        title = "Triage",
        subtitle = "Tomorrow, smaller, or let go. A carried item cools — it never shames.",
    ) {
        if (openTasks.isEmpty()) {
            Text(
                "Nothing left open — the day cleared itself. Straight to tomorrow's shape.",
                style = MaterialTheme.typography.bodyLarge,
                color = BuTextDim,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                openTasks.forEach { task ->
                    val decided = decisions[task.objectId]
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = BuLayer,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(task.title, style = MaterialTheme.typography.bodyLarge)
                                    if (task.movedCount > 0) {
                                        Text(
                                            "carried ${task.movedCount}× · cooling",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = BuTextDim,
                                        )
                                    }
                                    if (RitualHealth.carryBlocked(task.movedCount)) {
                                        Text(
                                            "3 moves — this one wants a decision tonight.",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = BuMint,
                                        )
                                    }
                                }
                                if (decided != null) {
                                    Text(
                                        when (decided) {
                                            Triage.TOMORROW -> "→ tomorrow"
                                            Triage.SHRINK -> "↘ smaller"
                                            Triage.LET_GO -> "let go"
                                        },
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (decided == Triage.LET_GO) BuTextDim else BuMint,
                                    )
                                }
                            }

                            if (decided == null) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    FilterChip(
                                        selected = false,
                                        onClick = { onDecide(task, Triage.TOMORROW) },
                                        label = { Text("Tomorrow") },
                                    )
                                    FilterChip(
                                        selected = false,
                                        onClick = { onDecide(task, Triage.SHRINK) },
                                        label = { Text("Smaller") },
                                    )
                                    FilterChip(
                                        selected = false,
                                        onClick = { onDecide(task, Triage.LET_GO) },
                                        label = { Text("Let go", color = BuDanger) },
                                    )
                                }
                            }

                            // Shrink: pick what actually fits; the estimate lands honestly.
                            if (decided == Triage.SHRINK && shrinkMinutes[task.objectId] == null) {
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(listOf(10, 15, 25, 45)) { minutes ->
                                        FilterChip(
                                            selected = false,
                                            onClick = { onShrinkTo(task, minutes) },
                                            label = { Text("$minutes min") },
                                        )
                                    }
                                }
                                Text(
                                    "What would fit on a tired day?",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = BuTextDim,
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))
        DuskPrimaryButton("Continue") { onNext() }
    }
}

private val OBSTACLES = listOf("Phone", "Fatigue", "Interruption", "Ambiguity", "People")

/** Step 4 — Commit: tomorrow's shape, minimum day, if-then shield, ceiling. */
@Composable
private fun CommitStep(
    slot0: String,
    slot1: String,
    slot2: String,
    front: Int,
    minimumDay: String,
    obstacle: String?,
    ifThenPlan: String,
    ceiling: Int,
    sealing: Boolean,
    error: String?,
    onSlot0: (String) -> Unit,
    onSlot1: (String) -> Unit,
    onSlot2: (String) -> Unit,
    onFront: (Int) -> Unit,
    onMinimumDay: (String) -> Unit,
    onObstacle: (String?) -> Unit,
    onIfThenPlan: (String) -> Unit,
    onCeiling: (Int) -> Unit,
    onSeal: () -> Unit,
) {
    DuskScaffold(
        stepLabel = "DUSK CLOSE · 4 of 5",
        title = "Tomorrow's contract",
        subtitle = "Auto-drafted from your triage. Edit freely — zero lines is a legal rest day.",
    ) {
        OutlinedTextField(
            value = slot0,
            onValueChange = onSlot0,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Main Front — the one thing") },
            trailingIcon = {
                FilterChip(selected = front == 0, onClick = { onFront(0) }, label = { Text("Front") })
            },
            singleLine = true,
        )
        OutlinedTextField(
            value = slot1,
            onValueChange = onSlot1,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Priority 2") },
            trailingIcon = {
                FilterChip(selected = front == 1, onClick = { onFront(1) }, label = { Text("Front") })
            },
            singleLine = true,
        )
        OutlinedTextField(
            value = slot2,
            onValueChange = onSlot2,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Priority 3") },
            trailingIcon = {
                FilterChip(selected = front == 2, onClick = { onFront(2) }, label = { Text("Front") })
            },
            singleLine = true,
        )

        Text("The Minimum Day", style = MaterialTheme.typography.titleMedium)
        Text(
            "The one thing that makes tomorrow a success if all else collapses.",
            style = MaterialTheme.typography.labelSmall,
            color = BuTextDim,
        )
        OutlinedTextField(
            value = minimumDay,
            onValueChange = onMinimumDay,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g. One page of revision") },
            singleLine = true,
        )

        Text("The If-Then shield", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(OBSTACLES) { candidate ->
                FilterChip(
                    selected = obstacle == candidate,
                    onClick = { onObstacle(if (obstacle == candidate) null else candidate) },
                    label = { Text(candidate) },
                )
            }
        }
        if (obstacle != null) {
            OutlinedTextField(
                value = ifThenPlan,
                onValueChange = onIfThenPlan,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("When $obstacle shows up, I will…") },
                singleLine = true,
            )
        }

        Text("Shield ceiling for tomorrow", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0, 1, 2, 3).forEach { rung ->
                FilterChip(
                    selected = ceiling == rung,
                    onClick = { onCeiling(rung) },
                    label = { Text("L$rung") },
                )
            }
        }

        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Spacer(Modifier.weight(1f))
        DuskPrimaryButton(
            label = if (sealing) "Sealing…" else "Seal the day",
            enabled = !sealing,
            onClick = onSeal,
        )
    }
}

/** Step 5 — Seal: the receipt of the ritual itself. */
@Composable
private fun SealedStep(targetDate: String, onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Sealed.", style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(10.dp))
        Text(
            "$targetDate has its contract. Sleep — the morning push will be waiting.",
            style = MaterialTheme.typography.bodyLarge,
            color = BuTextDim,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(28.dp))
        DuskPrimaryButton("Good night") { onDone() }
    }
}

/** The one big button per screen — the ritual's only affordance. */
@Composable
private fun DuskPrimaryButton(label: String, enabled: Boolean = true, onClick: () -> Unit) {
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
