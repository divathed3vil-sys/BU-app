package com.bu.core.command

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Typed commands (locked plan §5.2). Every state change in BU enters
 * through [CommandGateway] as one of these.
 *
 * P1 vocabulary: capture → interpret/promote · tracks CRUD · tasks
 * (Big Three / Blocks / Sparks) · tallies (the Tally Trio) · care reminders ·
 * monotonic focus sessions · settings (day window, onboarding).
 */
sealed interface BuCommand {
    /** Client-supplied key; retries with the same key never duplicate effects (I5). */
    val idempotencyKey: String?
    val source: CommandSource
    val commandType: String

    data class SaveCapture(
        val text: String,
        val captureSource: String = "PEBBLE",
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "SAVE_CAPTURE"
    }

    /** Applied by the outbox: the deterministic parser's verdict on a capture. */
    data class InterpretCapture(
        val captureId: String,
        val isTask: Boolean,
        val title: String,
        val scheduledDateIso: String? = null,
        val startMinute: Int? = null,
        val scheduledAtMs: Long? = null,
        override val source: CommandSource = CommandSource.SYSTEM_RECONCILIATION,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "INTERPRET_CAPTURE"
    }

    /** Owner taps "make it a task" on a captured spark. */
    data class PromoteCapture(
        val captureId: String,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "PROMOTE_CAPTURE"
    }

    data class CreateTrack(
        val name: String,
        val estimateMinutes: Int,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "CREATE_TRACK"
    }

    data class UpdateTrack(
        val trackId: String,
        val name: String? = null,
        val estimateMinutes: Int? = null,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "UPDATE_TRACK"
    }

    data class ArchiveTrack(
        val trackId: String,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "ARCHIVE_TRACK"
    }

    /**
     * One command for every task class: BIG (Big Three), BLOCK (time-boxed),
     * SPARK (captured small thing), EXAM.
     */
    data class CreateTask(
        val title: String,
        val taskClass: String,
        val trackId: String? = null,
        val scheduledDateIso: String? = null,
        val startMinute: Int? = null,
        val durationMinutes: Int? = null,
        val dueAtMs: Long? = null,
        val sourceCaptureId: String? = null,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "CREATE_TASK"
    }

    /** The Tally Trio — DONE · CARRIED (toDate = tomorrow) · PARTIAL. */
    data class TallyTask(
        val taskId: String,
        val tally: String, // DONE | PARTIAL | CARRIED
        val toDateIso: String? = null, // required when CARRIED
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "TALLY_TASK"
    }

    /** Replaces today's Big Three; old OPEN slots are superseded, never shamed. */
    data class SetBigThree(
        val dateIso: String,
        val slots: List<BigThreeSlotInput>,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "SET_BIG_THREE"
    }

    data class CreateReminder(
        val title: String,
        val dueAtMs: Long,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "CREATE_REMINDER"
    }

    data class CompleteReminder(
        val reminderId: String,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "COMPLETE_REMINDER"
    }

    data class SnoozeReminder(
        val reminderId: String,
        val minutes: Int,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "SNOOZE_REMINDER"
    }

    data class CancelReminder(
        val reminderId: String,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "CANCEL_REMINDER"
    }

    data class StartFocus(
        val trackId: String? = null,
        val plannedMinutes: Int = 25, // 0 = open-ended
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "START_FOCUS"
    }

    data class EndFocus(
        val sessionId: String,
        val completedAsPlanned: Boolean,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "END_FOCUS"
    }

    data class SetDayWindow(
        val startMinute: Int,
        val endMinute: Int,
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "SET_DAY_WINDOW"
    }

    data class MarkOnboarded(
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "MARK_ONBOARDED"
    }
}

@Serializable
data class BigThreeSlotInput(
    val title: String,
    val startMinute: Int? = null,
    val isMainFront: Boolean = false,
)

// ── Durable payloads ─────────────────────────────────────────────────────────

@Serializable
data class SaveCapturePayload(val text: String, val captureSource: String)

@Serializable
data class InterpretCapturePayload(
    val captureId: String,
    val isTask: Boolean,
    val title: String,
    val scheduledDateIso: String?,
    val startMinute: Int?,
    val scheduledAtMs: Long?,
)

@Serializable
data class PromoteCapturePayload(val captureId: String)

@Serializable
data class CreateTrackPayload(val name: String, val estimateMinutes: Int)

@Serializable
data class UpdateTrackPayload(val trackId: String, val name: String?, val estimateMinutes: Int?)

@Serializable
data class ArchiveTrackPayload(val trackId: String)

@Serializable
data class CreateTaskPayload(
    val title: String,
    val taskClass: String,
    val trackId: String?,
    val scheduledDateIso: String?,
    val startMinute: Int?,
    val durationMinutes: Int?,
    val dueAtMs: Long?,
    val sourceCaptureId: String?,
)

@Serializable
data class TallyTaskPayload(val taskId: String, val tally: String, val toDateIso: String?)

@Serializable
data class SetBigThreePayload(val dateIso: String, val slots: List<BigThreeSlotInput>)

@Serializable
data class CreateReminderPayload(val title: String, val dueAtMs: Long)

@Serializable
data class ReminderRefPayload(val reminderId: String)

@Serializable
data class SnoozeReminderPayload(val reminderId: String, val minutes: Int)

@Serializable
data class StartFocusPayload(val trackId: String?, val plannedMinutes: Int)

@Serializable
data class EndFocusPayload(val sessionId: String, val completedAsPlanned: Boolean)

@Serializable
data class SetDayWindowPayload(val startMinute: Int, val endMinute: Int)

@Serializable
data class MarkOnboardedPayload(val onboarded: Boolean)

/** Outbox ticket for the interpreter — carries the capture id after commit. */
@Serializable
data class InterpretTicketPayload(val captureId: String)

/** Outbox ticket for the platform alarm projection. */
@Serializable
data class AlarmTicketPayload(val reminderId: String, val dueAtMs: Long)

private val json = Json { encodeDefaults = true }

/** Serializes a command into its durable envelope payload. */
fun BuCommand.payloadJson(): String = when (this) {
    is BuCommand.SaveCapture ->
        json.encodeToString(SaveCapturePayload(text = text, captureSource = captureSource))
    is BuCommand.InterpretCapture ->
        json.encodeToString(
            InterpretCapturePayload(
                captureId = captureId,
                isTask = isTask,
                title = title,
                scheduledDateIso = scheduledDateIso,
                startMinute = startMinute,
                scheduledAtMs = scheduledAtMs,
            ),
        )
    is BuCommand.PromoteCapture -> json.encodeToString(PromoteCapturePayload(captureId))
    is BuCommand.CreateTrack -> json.encodeToString(CreateTrackPayload(name, estimateMinutes))
    is BuCommand.UpdateTrack ->
        json.encodeToString(UpdateTrackPayload(trackId, name, estimateMinutes))
    is BuCommand.ArchiveTrack -> json.encodeToString(ArchiveTrackPayload(trackId))
    is BuCommand.CreateTask ->
        json.encodeToString(
            CreateTaskPayload(
                title = title,
                taskClass = taskClass,
                trackId = trackId,
                scheduledDateIso = scheduledDateIso,
                startMinute = startMinute,
                durationMinutes = durationMinutes,
                dueAtMs = dueAtMs,
                sourceCaptureId = sourceCaptureId,
            ),
        )
    is BuCommand.TallyTask -> json.encodeToString(TallyTaskPayload(taskId, tally, toDateIso))
    is BuCommand.SetBigThree -> json.encodeToString(SetBigThreePayload(dateIso, slots))
    is BuCommand.CreateReminder -> json.encodeToString(CreateReminderPayload(title, dueAtMs))
    is BuCommand.CompleteReminder -> json.encodeToString(ReminderRefPayload(reminderId))
    is BuCommand.SnoozeReminder -> json.encodeToString(SnoozeReminderPayload(reminderId, minutes))
    is BuCommand.CancelReminder -> json.encodeToString(ReminderRefPayload(reminderId))
    is BuCommand.StartFocus -> json.encodeToString(StartFocusPayload(trackId, plannedMinutes))
    is BuCommand.EndFocus ->
        json.encodeToString(EndFocusPayload(sessionId, completedAsPlanned))
    is BuCommand.SetDayWindow -> json.encodeToString(SetDayWindowPayload(startMinute, endMinute))
    is BuCommand.MarkOnboarded -> json.encodeToString(MarkOnboardedPayload(true))
}
