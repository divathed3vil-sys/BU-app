package com.bu.app.now

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.core.command.BuCommand
import com.bu.core.command.BigThreeSlotInput
import com.bu.core.command.BtkGateway
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.command.CommandStatus
import com.bu.core.ledger.FocusSessionRow
import com.bu.core.ledger.TaskWithTitle
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import com.bu.designsystem.component.BuArcDial
import com.bu.designsystem.component.BuCard
import com.bu.designsystem.component.BuDayArc
import com.bu.designsystem.motion.rememberBuHaptics
import com.bu.designsystem.theme.BuCooling
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuMintDim
import com.bu.designsystem.theme.BuTextDim
import com.bu.identity.loop.Momentum
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * NOW — the living cockpit (locked plan §3). Everything here reads the ledger
 * directly (read-side projections) and writes only through the kernel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NowViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
    private val taskDao: com.bu.core.ledger.TaskDao,
    contractDao: com.bu.core.ledger.ContractDao,
    settingsDao: com.bu.core.ledger.SettingsDao,
    momentumDao: com.bu.core.ledger.MomentumDao,
    focusDao: com.bu.core.ledger.FocusDao,
    eventDao: com.bu.core.ledger.EventDao,
    outboxDao: com.bu.core.ledger.OutboxDao,
    private val clock: BuClock,
) : ViewModel() {

    val todayIso: String = LocalDate.now().toString()

    private val dayStartSetting = settingsDao.observeAll().map { rows ->
        rows.firstOrNull { it.key == BtkGateway.SETTING_DAY_START }?.value?.toIntOrNull() ?: 5 * 60
    }
    private val dayEndSetting = settingsDao.observeAll().map { rows ->
        rows.firstOrNull { it.key == BtkGateway.SETTING_DAY_END }?.value?.toIntOrNull() ?: 23 * 60
    }

    val dayWindow: StateFlow<Pair<Int, Int>> =
        combine(dayStartSetting, dayEndSetting) { s, e -> s to e }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 5 * 60 to 23 * 60)

    val bigThree: StateFlow<List<TaskWithTitle>> =
        taskDao.observeDayTasks("BIG", todayIso)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val blocks: StateFlow<List<TaskWithTitle>> =
        taskDao.observeDayTasks("BLOCK", todayIso)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val sparks: StateFlow<List<TaskWithTitle>> =
        taskDao.observeOpenSparksWithTitle()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val contract: StateFlow<com.bu.core.ledger.DayContractRow?> =
        contractDao.observeByDate(todayIso)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val momentum: StateFlow<Momentum.Snapshot?> =
        momentumDao.observeRecent(7).map { rows ->
            Momentum.snapshot(
                todayIso,
                rows.map { Momentum.Day(it.dateIso, it.doneCount, it.tallyCount) },
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val runningFocus: StateFlow<FocusSessionRow?> =
        focusDao.observeRunning()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    /** Monotonic elapsed seconds for the running session — a 1 s ticker. */
    val focusElapsedSeconds: StateFlow<Int> = runningFocus
        .flatMapLatest { row ->
            if (row == null) {
                flowOf(0)
            } else {
                flow {
                    while (true) {
                        val elapsed = ((clock.elapsedRealtimeMs() - row.startedElapsedMs) / 1000L)
                            .toInt().coerceAtLeast(0)
                        emit(elapsed)
                        delay(1_000)
                    }
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    /** Captures the kernel committed today — proof the loop is alive. */
    val capturesToday: StateFlow<Int> = eventDao.captureCountSince(
        LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli(),
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val pendingEffects: StateFlow<Int> = outboxDao.pendingCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    // ── Commands — every change enters the kernel, then the outbox drains ────

    fun submit(command: BuCommand, onResult: (CommandReceipt) -> Unit = {}) {
        viewModelScope.launch {
            val receipt = gateway.submit(command)
            outboxProcessor.drain()
            onResult(receipt)
        }
    }
}

@Composable
fun NowTab(viewModel: NowViewModel = hiltViewModel()) {
    val bigThree by viewModel.bigThree.collectAsStateWithLifecycle()
    val blocks by viewModel.blocks.collectAsStateWithLifecycle()
    val sparks by viewModel.sparks.collectAsStateWithLifecycle()
    val contract by viewModel.contract.collectAsStateWithLifecycle()
    val momentum by viewModel.momentum.collectAsStateWithLifecycle()
    val window by viewModel.dayWindow.collectAsStateWithLifecycle()
    val runningFocus by viewModel.runningFocus.collectAsStateWithLifecycle()
    val focusSeconds by viewModel.focusElapsedSeconds.collectAsStateWithLifecycle()
    val captures by viewModel.capturesToday.collectAsStateWithLifecycle()
    val pending by viewModel.pendingEffects.collectAsStateWithLifecycle()

    var bigThreeEditorOpen by remember { mutableStateOf(false) }
    var blockEditorOpen by remember { mutableStateOf(false) }
    var captureOpen by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 148.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        DayArcCard(
            windowMinutes = window,
            progress = tallyRatio(bigThree),
        )

        MomentumCard(momentum)

        BigThreeCard(
            tasks = bigThree,
            mainFrontId = contract?.mainFrontTaskId,
            onTally = { taskId, tally, toDate ->
                viewModel.submit(BuCommand.TallyTask(taskId, tally, toDate))
            },
            onEdit = { bigThreeEditorOpen = true },
        )

        BlocksCard(
            blocks = blocks,
            onTally = { taskId, tally, toDate ->
                viewModel.submit(BuCommand.TallyTask(taskId, tally, toDate))
            },
            onAdd = { blockEditorOpen = true },
        )

        SparksCard(
            sparks = sparks,
            onTally = { taskId, tally, toDate ->
                viewModel.submit(BuCommand.TallyTask(taskId, tally, toDate))
            },
            onCapture = { captureOpen = true },
        )

        FocusCard(
            running = runningFocus,
            elapsedSeconds = focusSeconds,
            onStart = { minutes -> viewModel.submit(BuCommand.StartFocus(plannedMinutes = minutes)) },
            onEnd = { sessionId, completed ->
                viewModel.submit(BuCommand.EndFocus(sessionId, completed))
            },
        )

        KernelPulseCard(captures = captures, pending = pending)
    }

    if (bigThreeEditorOpen) {
        BigThreeEditorSheet(
            onDismiss = { bigThreeEditorOpen = false },
            onSave = { slots, onReceipt ->
                viewModel.submit(
                    BuCommand.SetBigThree(
                        dateIso = viewModel.todayIso,
                        slots = slots,
                    ),
                ) { receipt -> onReceipt(receipt) }
            },
        )
    }

    if (blockEditorOpen) {
        BlockEditorSheet(
            onDismiss = { blockEditorOpen = false },
            onSave = { title, startMinute, duration, onReceipt ->
                viewModel.submit(
                    BuCommand.CreateTask(
                        title = title,
                        taskClass = "BLOCK",
                        scheduledDateIso = viewModel.todayIso,
                        startMinute = startMinute,
                        durationMinutes = duration,
                    ),
                ) { receipt -> onReceipt(receipt) }
            },
        )
    }

    if (captureOpen) {
        com.bu.app.capture.CaptureSheet(
            captureSource = "SPARKS",
            onDismiss = { captureOpen = false },
            onSaved = { captureOpen = false },
        )
    }
}

private fun tallyRatio(tasks: List<TaskWithTitle>): Float {
    val tallied = tasks.count { it.status != "OPEN" }
    if (tasks.isEmpty()) return 0f
    return tallied.toFloat() / tasks.size
}

@Composable
private fun DayArcCard(windowMinutes: Pair<Int, Int>, progress: Float) {
    val zone = java.time.ZoneId.systemDefault()
    val now = java.time.LocalTime.now(zone)
    val nowMinute = now.hour * 60 + now.minute
    val (start, end) = windowMinutes
    val nowProgress = ((nowMinute - start).toFloat() / (end - start).coerceAtLeast(1)).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        BuDayArc(
            progress = progress,
            nowProgress = nowProgress,
            dayStartHour = start / 60,
            dayEndHour = end / 60,
            modifier = Modifier.size(264.dp),
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Day Arc", style = MaterialTheme.typography.headlineSmall)
            Text(
                "${formatMinute(start)} → ${formatMinute(end)} · live",
                style = MaterialTheme.typography.labelSmall,
                color = BuTextDim,
            )
        }
    }
}

internal fun formatMinute(minute: Int): String {
    val h = minute / 60
    val m = minute % 60
    return "$h:" + m.toString().padStart(2, '0')
}

@Composable
private fun MomentumCard(snapshot: Momentum.Snapshot?) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Text("Momentum", style = MaterialTheme.typography.titleMedium)
        Text(
            snapshot?.let { Momentum.headline(it) } ?: "No tallies yet — the first one is free.",
            style = MaterialTheme.typography.bodyMedium,
            color = BuTextDim,
        )
    }
}

@Composable
private fun BigThreeCard(
    tasks: List<TaskWithTitle>,
    mainFrontId: String?,
    onTally: (String, String, String?) -> Unit,
    onEdit: () -> Unit,
) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Big Three", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Up to three priorities. One Main Front. The Tally Trio closes them.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            }
            TextButton(onClick = onEdit) { Text("Set") }
        }
        if (tasks.isEmpty()) {
            Text(
                "Empty on purpose. Choose what today is about — one decision, three lines.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                tasks.forEach { task ->
                    TallyRow(
                        task = task,
                        badge = if (task.objectId == mainFrontId) "Main Front" else null,
                        onTally = onTally,
                    )
                }
            }
        }
    }
}

@Composable
private fun BlocksCard(
    blocks: List<TaskWithTitle>,
    onTally: (String, String, String?) -> Unit,
    onAdd: () -> Unit,
) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Blocks", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Time-boxed pieces of the day. They refuse to overlap.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            }
            TextButton(onClick = onAdd) { Text("Add") }
        }
        if (blocks.isEmpty()) {
            Text(
                "No blocks yet — the day is open. Place the first one on the Arc-Dial.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                blocks.forEach { block ->
                    TallyRow(
                        task = block,
                        badge = block.startMinute?.let { formatMinute(it) },
                        onTally = onTally,
                    )
                }
            }
        }
    }
}

@Composable
private fun SparksCard(
    sparks: List<TaskWithTitle>,
    onTally: (String, String, String?) -> Unit,
    onCapture: () -> Unit,
) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Sparks", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Small things waiting. Capture anywhere; they cool here until promoted or done.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            }
            TextButton(onClick = onCapture) { Text("Capture") }
        }
        if (sparks.isEmpty()) {
            Text(
                "Nothing waiting. The Pebble catches the next thought the moment it appears.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                sparks.take(5).forEach { spark ->
                    TallyRow(task = spark, badge = null, onTally = onTally, compact = true)
                }
            }
        }
    }
}

/**
 * The Tally Trio (locked plan §4.3): swipe right = Done, swipe left = Carry,
 * buttons for everything else. Colour intent while dragging; a carry never
 * shames — it cools. Buttons stay visible: the accessible, discoverable path.
 */
@Composable
private fun TallyRow(
    task: TaskWithTitle,
    badge: String?,
    onTally: (String, String, String?) -> Unit,
    compact: Boolean = false,
) {
    val haptics = rememberBuHaptics()
    var dragX by remember(task.objectId) { mutableStateOf(0f) }
    val done = task.status == "DONE"
    val partial = task.status == "PARTIAL"

    val bg = when {
        dragX > 40f -> BuMintDim
        dragX < -40f -> BuCooling.copy(alpha = 0.35f)
        else -> Color.Transparent
    }

    val tomorrowIso = remember(task.objectId) { LocalDate.now().plusDays(1).toString() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg, RoundedCornerShape(14.dp))
            .pointerInput(task.objectId) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        when {
                            dragX > 90f -> {
                                haptics.win()
                                onTally(task.objectId, "DONE", null)
                            }
                            dragX < -90f -> {
                                haptics.tick()
                                onTally(task.objectId, "CARRIED", tomorrowIso)
                            }
                        }
                        dragX = 0f
                    },
                ) { change, dragAmount ->
                    change.consume()
                    dragX = (dragX + dragAmount).coerceIn(-140f, 140f)
                }
            }
            .graphicsLayer { translationX = dragX }
            .padding(horizontal = 8.dp, vertical = if (compact) 6.dp else 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (badge != null) {
                    Text(
                        badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = BuMint,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                }
                Text(
                    task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (done) BuTextDim else MaterialTheme.colorScheme.onSurface,
                    textDecoration = if (done) TextDecoration.LineThrough else null,
                )
            }
            if (task.movedCount > 0 && !done) {
                Text(
                    "carried ${task.movedCount}× · cooling",
                    style = MaterialTheme.typography.labelSmall,
                    color = BuTextDim,
                )
            }
        }

        if (!done) {
            TextButton(onClick = {
                haptics.win()
                onTally(task.objectId, "DONE", null)
            }) { Text("✓") }
            if (!compact) {
                TextButton(onClick = {
                    haptics.tick()
                    onTally(task.objectId, "PARTIAL", null)
                }) { Text("◐") }
                TextButton(onClick = {
                    haptics.tick()
                    onTally(task.objectId, "CARRIED", tomorrowIso)
                }) { Text("→") }
            }
        } else {
            Text("landed", style = MaterialTheme.typography.labelSmall, color = BuMint)
        }
    }
}

@Composable
private fun FocusCard(
    running: FocusSessionRow?,
    elapsedSeconds: Int,
    onStart: (Int) -> Unit,
    onEnd: (String, Boolean) -> Unit,
) {
    var selected by remember { mutableIntStateOf(25) }
    BuCard(modifier = Modifier.fillMaxWidth()) {
        if (running == null) {
            Text("Focus", style = MaterialTheme.typography.titleMedium)
            Text(
                "One track at a time, measured on the monotonic clock. A reboot is recorded, never invented.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                items(listOf(15, 25, 45, 0)) { minutes ->
                    FilterChip(
                        selected = selected == minutes,
                        onClick = { selected = minutes },
                        label = { Text(if (minutes == 0) "Open" else "$minutes min") },
                    )
                }
            }
            TextButton(onClick = { onStart(selected) }, modifier = Modifier.padding(top = 4.dp)) {
                Text("Start focus")
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Focus running", style = MaterialTheme.typography.titleMedium)
                    Text(
                        if (running.plannedMinutes == 0) "open-ended" else "${running.plannedMinutes} planned",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BuTextDim,
                    )
                }
                Text(
                    formatSeconds(elapsedSeconds),
                    style = MaterialTheme.typography.headlineSmall,
                    color = BuMint,
                )
            }
            Row(modifier = Modifier.padding(top = 4.dp)) {
                TextButton(onClick = { onEnd(running.sessionId, true) }) { Text("Completed") }
                TextButton(onClick = { onEnd(running.sessionId, false) }) { Text("End early") }
            }
        }
    }
}

internal fun formatSeconds(total: Int): String {
    val m = total / 60
    val s = total % 60
    return "$m:" + s.toString().padStart(2, '0')
}

@Composable
private fun KernelPulseCard(captures: Int, pending: Int) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(BuMint, CircleShape),
            )
            Column {
                Text("Kernel live", style = MaterialTheme.typography.titleMedium)
                Text(
                    "captures today: $captures · async work queued: $pending",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            }
        }
    }
}

/** One decision per screen: three lines, one front, save — the contract lands. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BigThreeEditorSheet(
    onDismiss: () -> Unit,
    onSave: (List<BigThreeSlotInput>, onReceipt: (CommandReceipt) -> Unit) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var slot0 by remember { mutableStateOf("") }
    var slot1 by remember { mutableStateOf("") }
    var slot2 by remember { mutableStateOf("") }
    var front by remember { mutableIntStateOf(0) }
    var saving by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Today's Big Three", style = MaterialTheme.typography.headlineSmall)
            Text(
                "The first line you mark becomes the Main Front.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
            listOf(slot0 to 0, slot1 to 1, slot2 to 2).forEach { (value, index) ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { text ->
                        if (index == 0) slot0 = text
                        if (index == 1) slot1 = text
                        if (index == 2) slot2 = text
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(if (index == 0) "Main Front — the one thing" else "Priority ${index + 1}") },
                    trailingIcon = {
                        FilterChip(
                            selected = front == index,
                            onClick = { front = index },
                            label = { Text("Front") },
                        )
                    },
                    singleLine = true,
                )
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) { Text("Not now") }
                TextButton(
                    enabled = !saving,
                    onClick = {
                        val titles = listOf(slot0, slot1, slot2)
                        val indices = titles.withIndex().filter { it.value.isNotBlank() }.map { it.index }
                        if (indices.isEmpty()) {
                            error = "Nothing to commit yet — write at least one line."
                            return@TextButton
                        }
                        saving = true
                        val chosen = if (front in indices) front else indices.first()
                        onSave(
                            indices.map { i ->
                                BigThreeSlotInput(title = titles[i].trim(), isMainFront = i == chosen)
                            },
                        ) { receipt ->
                            saving = false
                            when (receipt.status) {
                                CommandStatus.COMMITTED -> onDismiss()
                                CommandStatus.REJECTED -> error = when (receipt.reason) {
                                    "EXACTLY_ONE_MAIN_FRONT" -> "Exactly one line carries the Front mark."
                                    "MAX_THREE" -> "The Big Three never exceed three."
                                    else -> "BU could not commit that."
                                }
                                else -> error = "BU could not commit that."
                            }
                        }
                    },
                ) { Text(if (saving) "Committing…" else "Commit") }
            }
        }
    }
}

/** A block needs a day, a start (Arc-Dial) and a snapped duration. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlockEditorSheet(
    onDismiss: () -> Unit,
    onSave: (title: String, startMinute: Int, duration: Int, onReceipt: (CommandReceipt) -> Unit) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember { mutableStateOf("") }
    var start by remember { mutableIntStateOf(9 * 60) }
    var duration by remember { mutableIntStateOf(60) }
    var saving by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Place a block", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    error = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("What is this block for?") },
                singleLine = true,
            )
            BuArcDial(
                valueMinutes = start,
                onValueChange = { start = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            )
            Text(
                "Starts ${formatMinute(start)}",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf(15, 25, 45, 60, 90)) { minutes ->
                    FilterChip(
                        selected = duration == minutes,
                        onClick = { duration = minutes },
                        label = { Text("$minutes min") },
                    )
                }
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) { Text("Not now") }
                TextButton(
                    enabled = title.isNotBlank() && !saving,
                    onClick = {
                        saving = true
                        onSave(title.trim(), start, duration) { receipt ->
                            saving = false
                            when (receipt.status) {
                                CommandStatus.COMMITTED -> onDismiss()
                                CommandStatus.REJECTED -> error = when (receipt.reason) {
                                    "OVERLAP_BLOCK" -> "Another block already lives there."
                                    "BLOCK_NEEDS_A_START", "BLOCK_NEEDS_A_DURATION" -> "Give the block a start and a length."
                                    "TIME_NOT_SNAPPED" -> "Times move in 15-minute steps."
                                    else -> "BU could not place that block."
                                }
                                else -> error = "BU could not place that block."
                            }
                        }
                    },
                ) { Text(if (saving) "Placing…" else "Place") }
            }
        }
    }
}
