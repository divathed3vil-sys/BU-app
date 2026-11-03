package com.bu.core.event

/**
 * Domain events — append-only truth of what happened (locked plan §6.1).
 * Never updated, never deleted; projections are rebuilt from these.
 */
data class DomainEvent(
    val eventId: String,
    val type: String,
    val aggregateId: String,
    val aggregateType: String,
    val occurredAtMs: Long,
    val recordedAtMs: Long,
    val actor: String,
    val payloadJson: String,
    val schemaVersion: Int = SCHEMA_VERSION,
    val correlationId: String? = null,
    val idempotencyKey: String? = null,
) {
    companion object {
        const val SCHEMA_VERSION = 1
    }
}

object EventTypes {
    const val COMMAND_RECEIVED = "COMMAND_RECEIVED"
    const val COMMAND_REJECTED = "COMMAND_REJECTED"
    const val COMMAND_UNDONE = "COMMAND_UNDONE"

    // Capture plane
    const val CAPTURE_SAVED = "CAPTURE_SAVED"
    const val CAPTURE_DISCARDED = "CAPTURE_DISCARDED"
    const val CAPTURE_INTERPRETED = "CAPTURE_INTERPRETED"
    const val CAPTURE_PROMOTED = "CAPTURE_PROMOTED"

    // Object plane (P1)
    const val TRACK_CREATED = "TRACK_CREATED"
    const val TRACK_UPDATED = "TRACK_UPDATED"
    const val TRACK_ARCHIVED = "TRACK_ARCHIVED"
    const val TASK_CREATED = "TASK_CREATED"
    const val TASK_TALLIED = "TASK_TALLIED"
    const val TASK_CARRIED = "TASK_CARRIED"
    const val TASK_SHRUNK = "TASK_SHRUNK"
    const val TASK_KILLED = "TASK_KILLED"
    const val BIG_THREE_SET = "BIG_THREE_SET"

    // Care (P1 round-trip)
    const val REMINDER_CREATED = "REMINDER_CREATED"
    const val REMINDER_COMPLETED = "REMINDER_COMPLETED"
    const val REMINDER_SNOOZED = "REMINDER_SNOOZED"
    const val REMINDER_CANCELLED = "REMINDER_CANCELLED"
    const val REMINDER_DUE = "REMINDER_DUE"
    const val REMINDER_RECURRED = "REMINDER_RECURRED"

    // Rituals (P2 — Dusk Close · Dawn Push)
    const val RITUAL_SEALED = "RITUAL_SEALED"
    const val DAWN_PUSH_DONE = "DAWN_PUSH_DONE"

    // Focus (P1 — monotonic clock)
    const val FOCUS_STARTED = "FOCUS_STARTED"
    const val FOCUS_ENDED = "FOCUS_ENDED"

    // Settings
    const val SETTINGS_UPDATED = "SETTINGS_UPDATED"
}

object AggregateTypes {
    const val COMMAND = "COMMAND"
    const val CAPTURE = "CAPTURE"
    const val OBJECT = "OBJECT"
    const val TASK = "TASK"
    const val TRACK = "TRACK"
    const val CONTRACT = "DAY_CONTRACT"
    const val REMINDER = "REMINDER"
    const val FOCUS_SESSION = "FOCUS_SESSION"
    const val SETTINGS = "SETTINGS"
}

object EffectTypes {
    const val PROJECTIONS_DIRTY = "PROJECTIONS_DIRTY"
    const val CAPTURE_INTERPRET = "CAPTURE_INTERPRET"
    const val SCHEDULE_REMINDER_ALARM = "SCHEDULE_REMINDER_ALARM"
    const val CANCEL_REMINDER_ALARM = "CANCEL_REMINDER_ALARM"
    const val WIDGET_DIRTY = "WIDGET_DIRTY"
}
