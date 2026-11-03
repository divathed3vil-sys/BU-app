package com.bu.app.plan

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.core.command.BuCommand
import com.bu.core.command.BtkGateway
import com.bu.core.command.CareTypes
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.command.CommandStatus
import com.bu.core.ledger.ReminderRow
import com.bu.core.ledger.TrackWithObject
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import com.bu.designsystem.component.BuArcDial
import com.bu.designsystem.component.BuCard
import com.bu.designsystem.theme.BuDanger
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuTextDim
import com.bu.identity.ritual.RitualHealth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** PLAN — the temporal workspace: tracks (capacity), care engine v3, alarm health. */
@HiltViewModel
class PlanViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
    trackDao: com.bu.core.ledger.TrackDao,
    reminderDao: com.bu.core.ledger.ReminderDao,
    settingsDao: com.bu.core.ledger.SettingsDao,
    private val clock: BuClock,
) : ViewModel() {

    val tracks: StateFlow<List<TrackWithObject>> = trackDao.observeTracksWithMeta()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val care: StateFlow<List<ReminderRow>> = reminderDao.observeActive()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /** The interrupt budget — owner-adjustable, kernel-validated. */
    val notifBudget: StateFlow<Int> = settingsDao.observeAll().map { rows ->
        rows.firstOrNull { it.key == BtkGateway.SETTING_NOTIF_BUDGET }?.value?.toIntOrNull()
            ?: RitualHealth.NotificationBudget.DEFAULT_BUDGET
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RitualHealth.NotificationBudget.DEFAULT_BUDGET)

    fun nowMs(): Long = clock.nowMs()

    fun submit(command: BuCommand, onResult: (CommandReceipt) -> Unit = {}) {
        viewModelScope.launch {
            val receipt = gateway.submit(command)
            outboxProcessor.drain()
            onResult(receipt)
        }
    }
}

@Composable
fun PlanTab(viewModel: PlanViewModel = hiltViewModel()) {
    val tracks by viewModel.tracks.collectAsStateWithLifecycle()
    val care by viewModel.care.collectAsStateWithLifecycle()
    val budget by viewModel.notifBudget.collectAsStateWithLifecycle()

    var trackEditorOpen by remember { mutableStateOf(false) }
    var trackEditing by remember { mutableStateOf<TrackWithObject?>(null) }
    var careEditorOpen by remember { mutableStateOf(false) }
    val nowMs = remember { viewModel.nowMs() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = 148.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            "PLAN",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 4.dp),
        )

        // ── Tracks — user-built, never preset (the absolute law) ──────────────
        BuCard(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Tracks", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Streams of work you define. BU never presets one — yours only.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BuTextDim,
                    )
                }
                TextButton(onClick = {
                    trackEditing = null
                    trackEditorOpen = true
                }) { Text("New") }
            }
            if (tracks.isEmpty()) {
                Text(
                    "No tracks yet. Name the first thing you are building — an exam, a skill, a project.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    tracks.forEach { track ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(track.title, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "${track.track_estimate} min estimate · ${track.track_samples} samples",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = BuTextDim,
                                )
                            }
                            TextButton(onClick = {
                                trackEditing = track
                                trackEditorOpen = true
                            }) { Text("Edit") }
                        }
                    }
                }
            }
        }

        // ── Care — the reminders BU may interrupt for ─────────────────────────
        BuCard(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Care", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "The one list allowed to ring. SQLite decides, the alarm only delivers.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BuTextDim,
                    )
                }
                TextButton(onClick = { careEditorOpen = true }) { Text("New") }
            }
            if (care.isEmpty()) {
                Text(
                    "Nothing pending. Set a time — an exact one when the OS allows, an honest window when it does not.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    care.forEach { reminder ->
                        CareRow(
                            reminder = reminder,
                            nowMs = nowMs,
                            onDone = {
                                viewModel.submit(BuCommand.CompleteReminder(reminder.reminderId))
                            },
                            onSnooze = {
                                viewModel.submit(BuCommand.SnoozeReminder(reminder.reminderId, 10))
                            },
                            onCancel = {
                                viewModel.submit(BuCommand.CancelReminder(reminder.reminderId))
                            },
                        )
                    }
                }
            }
        }

        // ── Care engine v3 — alarm health and the interrupt budget ───────────
        AlarmHealthCard()
        InterruptBudgetCard(
            budget = budget,
            onBudget = { value ->
                viewModel.submit(BuCommand.SetSetting(key = BtkGateway.SETTING_NOTIF_BUDGET, value = value.toString()))
            },
        )

        Text(
            "Blocks live on NOW · the day timeline and capacity bar land with the planner (P7).",
            style = MaterialTheme.typography.labelSmall,
            color = BuTextDim,
        )
        Spacer(Modifier.height(8.dp))
    }

    if (trackEditorOpen) {
        TrackEditorSheet(
            existing = trackEditing,
            onDismiss = { trackEditorOpen = false },
            onSave = { name, estimate, onReceipt ->
                val command = if (trackEditing != null) {
                    BuCommand.UpdateTrack(trackId = trackEditing!!.objectId, name = name, estimateMinutes = estimate)
                } else {
                    BuCommand.CreateTrack(name = name, estimateMinutes = estimate)
                }
                viewModel.submit(command) { receipt -> onReceipt(receipt) }
            },
            onArchive = if (trackEditing == null) {
                null
            } else {
                {
                    viewModel.submit(BuCommand.ArchiveTrack(trackEditing!!.objectId)) {
                        trackEditorOpen = false
                    }
                }
            },
        )
    }

    if (careEditorOpen) {
        CareEditorSheet(
            onDismiss = { careEditorOpen = false },
            onSave = { title, dueAtMs, careType ->
                viewModel.submit(
                    BuCommand.CreateReminder(title = title, dueAtMs = dueAtMs, careType = careType),
                ) { receipt ->
                    if (receipt.status == CommandStatus.COMMITTED) careEditorOpen = false
                }
            },
        )
    }
}

@Composable
private fun CareRow(
    reminder: ReminderRow,
    nowMs: Long,
    onDone: () -> Unit,
    onSnooze: () -> Unit,
    onCancel: () -> Unit,
) {
    val zone = ZoneId.systemDefault()
    val why = RitualHealth.whyNow(reminder.careType, reminder.dueAtMs, zone, LocalDate.now(zone))
    val waiting = RitualHealth.waitingLine(reminder.dueAtMs, nowMs, zone)

    Column(Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(reminder.title, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.padding(start = 6.dp))
                    CareTypeChip(reminder.careType)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        formatDue(reminder.dueAtMs),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (reminder.status == "DUE") BuMint else BuTextDim,
                    )
                    Spacer(Modifier.padding(start = 8.dp))
                    AlarmHonestyChip(reminder.alarmExact)
                }
                // Why-now — the line that can never be absent (locked plan §2).
                Text(
                    why,
                    style = MaterialTheme.typography.labelSmall,
                    color = BuTextDim,
                )
                waiting?.let {
                    Text(it, style = MaterialTheme.typography.labelSmall, color = BuMint)
                }
            }
            TextButton(onClick = onDone) { Text("✓") }
            TextButton(onClick = onSnooze) { Text("+10m") }
            TextButton(onClick = onCancel) { Text("✕", color = BuDanger) }
        }
    }
}

/** The care item's type — quiet, informative, never a chore. */
@Composable
private fun CareTypeChip(careType: String?) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            CareTypes.label(careType),
            style = MaterialTheme.typography.labelSmall,
            color = BuTextDim,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

/**
 * Exact-alarm health (locked plan §10, §13 P2): the card says plainly whether
 * the OS grants exact alarms, with a one-tap deep link when it does not.
 * A restricted alarm is never a failure — it degrades honestly.
 */
@Composable
private fun AlarmHealthCard() {
    val context = LocalContext.current
    val canExact = remember {
        if (Build.VERSION.SDK_INT >= 31) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.canScheduleExactAlarms()
        } else {
            true
        }
    }
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Alarm health", style = MaterialTheme.typography.titleMedium)
                Text(
                    if (canExact) {
                        "Exact alarms allowed — care rings when it should."
                    } else {
                        "Exact alarms restricted — care arrives in a 10-minute window, honestly labelled."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (canExact) BuMint else BuTextDim,
                )
            }
            if (!canExact && Build.VERSION.SDK_INT >= 31) {
                TextButton(onClick = {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    runCatching { context.startActivity(intent) }
                }) { Text("Allow") }
            }
        }
    }
}

/** The interrupt budget — care may ring N times a day; the rest waits quietly. */
@Composable
private fun InterruptBudgetCard(budget: Int, onBudget: (Int) -> Unit) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Text("Interrupt budget", style = MaterialTheme.typography.titleMedium)
        Text(
            "Care may interrupt $budget times a day. Anything beyond waits in the quiet digest — nothing is lost.",
            style = MaterialTheme.typography.bodyMedium,
            color = BuTextDim,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp),
        ) {
            items(listOf(3, 5, 8, 12)) { value ->
                FilterChip(
                    selected = budget == value,
                    onClick = { onBudget(value) },
                    label = { Text("$value/day") },
                )
            }
        }
    }
}

/** I9 — an inexact alarm never poses as exact; a pending one says so. */
@Composable
private fun AlarmHonestyChip(exact: Boolean?) {
    val label = when (exact) {
        null -> "scheduling…"
        true -> "exact"
        false -> "inexact window"
    }
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (exact == true) BuMint.copy(alpha = 0.14f) else MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = if (exact == true) BuMint else BuTextDim,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
    }
}

private val dueFormat = DateTimeFormatter.ofPattern("EEE d MMM · HH:mm")

internal fun errorFor(reason: String?): String? = when (reason) {
    "TITLE_TOO_LONG" -> "Keep the name short — it is a label, not a note."
    "ESTIMATE_OUT_OF_RANGE" -> "Estimates live between 5 and 600 minutes."
    else -> null
}

internal fun formatDue(dueAtMs: Long): String {
    val zone = ZoneId.systemDefault()
    val due = LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(dueAtMs), zone)
    val today = LocalDate.now(zone)
    return when (due.toLocalDate()) {
        today -> "today " + due.format(DateTimeFormatter.ofPattern("HH:mm"))
        today.plusDays(1) -> "tomorrow " + due.format(DateTimeFormatter.ofPattern("HH:mm"))
        else -> due.format(dueFormat)
    }
}

/** Tracks are created and edited in one sheet — name + honest estimate. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackEditorSheet(
    existing: TrackWithObject?,
    onDismiss: () -> Unit,
    onSave: (String, Int, onReceipt: (CommandReceipt) -> Unit) -> Unit,
    onArchive: (() -> Unit)?,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var name by remember(existing) { mutableStateOf(existing?.title ?: "") }
    var estimate by remember(existing) { mutableIntStateOf(existing?.track_estimate ?: 60) }
    var error by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(if (existing == null) "New track" else "Edit track", style = MaterialTheme.typography.headlineSmall)
            Text(
                "An estimate is a guess BU will learn to correct — never a promise.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    error = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g. ACS exam prep") },
                singleLine = true,
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(listOf(15, 30, 60, 120, 240)) { minutes ->
                    FilterChip(
                        selected = estimate == minutes,
                        onClick = { estimate = minutes },
                        label = { Text("$minutes min") },
                    )
                }
            }
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (onArchive != null) {
                    TextButton(onClick = onArchive) {
                        Text("Archive", color = BuDanger)
                    }
                } else {
                    Spacer(Modifier.weight(1f))
                }
                Row {
                    TextButton(onClick = onDismiss) { Text("Not now") }
                    TextButton(
                        enabled = name.isNotBlank() && !saving,
                        onClick = {
                            saving = true
                            onSave(name.trim(), estimate) { receipt ->
                                saving = false
                                when (receipt.status) {
                                    CommandStatus.COMMITTED -> onDismiss()
                                    CommandStatus.REJECTED ->
                                        error = errorFor(receipt.reason) ?: "BU could not save that."
                                    else -> error = "BU could not save that."
                                }
                            }
                        },
                    ) { Text(if (saving) "Saving…" else "Save") }
                }
            }
        }
    }
}

/** Care item: title, type, one dial decision. Exactness is the platform's honesty. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CareEditorSheet(
    onDismiss: () -> Unit,
    onSave: (String, Long, String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var title by remember { mutableStateOf("") }
    var dayOffset by remember { mutableIntStateOf(0) }
    var minuteOfDay by remember { mutableIntStateOf(18 * 60) }
    var careType by remember { mutableStateOf(CareTypes.CONTEXTUAL) }
    var error by remember { mutableStateOf<String?>(null) }
    val zone = ZoneId.systemDefault()

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text("Care item", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    error = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("What should BU hold for you?") },
                singleLine = true,
            )
            Text("Type", style = MaterialTheme.typography.titleSmall)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(CareTypes.ALL.toList()) { candidate ->
                    FilterChip(
                        selected = careType == candidate,
                        onClick = { careType = candidate },
                        label = { Text(CareTypes.label(candidate)) },
                    )
                }
            }
            if (CareTypes.isRecurring(careType)) {
                Text(
                    "Repeats daily — close one round and the next quietly arms itself.",
                    style = MaterialTheme.typography.labelSmall,
                    color = BuMint,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = dayOffset == 0,
                    onClick = { dayOffset = 0 },
                    label = { Text("Today") },
                )
                FilterChip(
                    selected = dayOffset == 1,
                    onClick = { dayOffset = 1 },
                    label = { Text("Tomorrow") },
                )
            }
            BuArcDial(
                valueMinutes = minuteOfDay,
                onValueChange = { minuteOfDay = it },
                rangeStartMinute = 0,
                rangeEndMinute = 24 * 60 - 15,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            )
            Text(
                "Rings ${formatMinuteOfDay(minuteOfDay)} " + if (dayOffset == 0) "today" else "tomorrow",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDismiss) { Text("Not now") }
                TextButton(
                    enabled = title.isNotBlank(),
                    onClick = {
                        val due = LocalDate.now(zone)
                            .plusDays(dayOffset.toLong())
                            .atTime(minuteOfDay / 60, minuteOfDay % 60)
                            .atZone(zone)
                            .toInstant()
                            .toEpochMilli()
                        onSave(title.trim(), due, careType)
                    },
                ) { Text("Set") }
            }
        }
    }
}

internal fun formatMinuteOfDay(minute: Int): String {
    val h = minute / 60
    val m = minute % 60
    return "$h:" + m.toString().padStart(2, '0')
}
