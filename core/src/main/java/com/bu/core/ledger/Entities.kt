package com.bu.core.ledger

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ─────────────────────────────────────────────────────────────────────────────
// BTK kernel tables (P0) — unchanged schema, the ledger's spine.
// ─────────────────────────────────────────────────────────────────────────────

@Entity(
    tableName = "command_inbox",
    indices = [Index(value = ["idempotencyKey"], unique = true)],
)
data class CommandRow(
    @PrimaryKey val commandId: String,
    val idempotencyKey: String,
    val commandType: String,
    val status: String,
    val source: String,
    val payloadJson: String,
    val createdAtMs: Long,
    val updatedAtMs: Long,
    val errorCode: String? = null,
)

@Entity(
    tableName = "domain_events",
    indices = [
        Index(value = ["eventId"], unique = true),
        Index(value = ["aggregateId", "sequenceNo"]),
        Index(value = ["type"]),
    ],
)
data class EventRow(
    @PrimaryKey(autoGenerate = true) val sequenceNo: Long = 0,
    val eventId: String,
    val type: String,
    val aggregateId: String,
    val aggregateType: String,
    val occurredAtMs: Long,
    val recordedAtMs: Long,
    val actor: String,
    val payloadJson: String,
    val schemaVersion: Int,
    val correlationId: String? = null,
    val idempotencyKey: String? = null,
)

@Entity(
    tableName = "outbox",
    indices = [Index(value = ["effectKey"], unique = true)],
)
data class OutboxRow(
    @PrimaryKey val outboxId: String,
    val effectType: String,
    val effectKey: String,
    val payloadJson: String,
    val status: String,
    val attempts: Int,
    val createdAtMs: Long,
    val lastErrorCode: String? = null,
)

@Entity(tableName = "capture_inbox", indices = [Index(value = ["linkedCommandId"])])
data class CaptureRow(
    @PrimaryKey val captureId: String,
    val rawText: String,
    val capturedAtMs: Long,
    val source: String,
    val status: String,
    val linkedCommandId: String?,
    val interpretedAtMs: Long? = null,
    // P1 — set when the deterministic parser promotes the capture into a task.
    val taskObjectId: String? = null,
)

// ─────────────────────────────────────────────────────────────────────────────
// P1 — object plane: every durable thing is an object with stable identity
// (locked plan §6.1). Objects are soft-deleted; revisions keep snapshots;
// relationships are the Constellation's typed edges.
// ─────────────────────────────────────────────────────────────────────────────

@Entity(tableName = "objects", indices = [Index("kind"), Index("deletedAtMs")])
data class ObjectRow(
    @PrimaryKey val objectId: String,
    val kind: String, // TRACK · TASK (NOTE/THREAD/EXAM/PERSON/VIRTUE arrive P3)
    val title: String,
    val createdAtMs: Long,
    val updatedAtMs: Long,
    val deletedAtMs: Long? = null,
)

@Entity(tableName = "object_revisions", indices = [Index("objectId")])
data class RevisionRow(
    @PrimaryKey val revisionId: String,
    val objectId: String,
    val revision: Int,
    val snapshotJson: String,
    val createdAtMs: Long,
)

@Entity(tableName = "relationships", indices = [Index("fromId"), Index("toId"), Index("type")])
data class RelationshipRow(
    @PrimaryKey val relationshipId: String,
    val fromId: String,
    val toId: String,
    val type: String, // belongs_to · supports · blocks · references · derived_from · scheduled_for · related_to · completed_by · involves
    val createdAtMs: Long,
)

/** Track extension of the object plane — EWMA estimate lands in P7 calibration. */
@Entity(tableName = "tracks")
data class TrackRow(
    @PrimaryKey val objectId: String, // == objects.objectId
    val estimateMinutes: Int,
    val estimateSamples: Int,
    val archivedAtMs: Long? = null,
)

/**
 * Task extension of the object plane (locked plan §6.1).
 * Local dates are ISO strings; instants are UTC epoch-ms; [timezone] records
 * the wall-clock intent zone at creation.
 */
@Entity(
    tableName = "tasks",
    indices = [Index("taskClass"), Index("status"), Index("scheduledDate"), Index("talliedDate")],
)
data class TaskRow(
    @PrimaryKey val objectId: String, // == objects.objectId
    val taskClass: String, // BIG · BLOCK · SPARK · EXAM
    val status: String, // OPEN · DONE · PARTIAL · REPLACED · ARCHIVED (CARRIED is an event, not a resting state)
    val priority: Int,
    val estimateMinutes: Int,
    val actualMinutes: Int? = null,
    val dueAtMs: Long? = null,
    val scheduledDate: String? = null, // ISO local date
    val startMinute: Int? = null,
    val endMinute: Int? = null,
    val timezone: String,
    val movedCount: Int,
    val energy: String? = null,
    val sourceCaptureId: String? = null,
    val talliedAtMs: Long? = null,
    val talliedDate: String? = null,
    val createdAtMs: Long,
    val updatedAtMs: Long,
)

@Entity(tableName = "day_contracts")
data class DayContractRow(
    @PrimaryKey val dateIso: String,
    val mainFrontTaskId: String? = null,
    val minimumDay: String? = null,
    val ifThenJson: String? = null,
    val ceilingRung: Int? = null,
    val sealedAtMs: Long? = null,
    val wakePlan: String? = null,
    val mood: Int? = null,
    val updatedAtMs: Long,
)

/** Carry history — cooldown rules read this in P2; the ledger never forgets. */
@Entity(tableName = "carry_ledger", indices = [Index("taskId")])
data class CarryRow(
    @PrimaryKey val carryId: String,
    val taskId: String,
    val fromDate: String,
    val toDate: String,
    val movedAtMs: Long,
)

// ─────────────────────────────────────────────────────────────────────────────
// P1 — care, focus, evidence, platform reports.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Reminders: SQLite is canonical, alarms are a projection (locked plan §10).
 * [alarmRevision] orders PendingIntent updates; [alarmExact] is reported back
 * by the platform scheduler — null means "not scheduled yet".
 *
 * P2 — Care engine v3: [careType] is one of CareTypes (anchor · contextual ·
 * relational · value · deadline · habit). Null on legacy P1 rows means
 * CONTEXTUAL. Recurring kinds re-arm on completion inside the kernel.
 */
@Entity(tableName = "reminders", indices = [Index("status"), Index("dueAtMs")])
data class ReminderRow(
    @PrimaryKey val reminderId: String,
    val title: String,
    val dueAtMs: Long,
    val kind: String, // CARE
    val status: String, // PENDING · DUE · DONE · CANCELLED
    val alarmRevision: Int,
    val alarmExact: Boolean? = null,
    val careType: String? = null,
    val createdAtMs: Long,
    val updatedAtMs: Long,
)

/**
 * Focus sessions measured on the monotonic clock (locked plan §6.1).
 * [startedElapsedMs] is SystemClock.elapsedRealtime() at start: a reboot rolls
 * it under zero-detection → INTERRUPTED_OR_UNKNOWN. Never invented.
 */
@Entity(tableName = "focus_sessions", indices = [Index("state")])
data class FocusSessionRow(
    @PrimaryKey val sessionId: String,
    val trackId: String? = null,
    val plannedMinutes: Int, // 0 = open-ended
    val state: String, // RUNNING · COMPLETED · ENDED_EARLY · INTERRUPTED_OR_UNKNOWN
    val startedAtMs: Long,
    val startedElapsedMs: Long,
    val endedAtMs: Long? = null,
    val actualMinutes: Int? = null,
)

/** Provenance: every automatic statement BU ever shows links to one of these (I6). */
@Entity(tableName = "evidence_events", indices = [Index("subjectId"), Index("grade")])
data class EvidenceRow(
    @PrimaryKey val evidenceId: String,
    val subjectId: String,
    val subjectType: String, // REMINDER · TASK · CAPTURE · OBJECT
    val grade: String, // SCHEDULED · OBSERVED · INFERRED · USER_CONFIRMED · OUTCOME_CONFIRMED
    val statement: String,
    val occurredAtMs: Long,
)

/** Key/value settings — day window, onboarding, quiet preferences. */
@Entity(tableName = "settings")
data class SettingRow(@PrimaryKey val key: String, val value: String)

/** Momentum day cache — 7-day rolling ratio, recomputed on every tally. */
@Entity(tableName = "momentum_cache")
data class MomentumRow(
    @PrimaryKey val dateIso: String,
    val doneCount: Int,
    val tallyCount: Int,
)

/** Platform delivery log — what actually left the device as a notification. */
@Entity(tableName = "notification_log", indices = [Index("reminderId")])
data class NotificationLogRow(
    @PrimaryKey val logId: String,
    val reminderId: String,
    val sentAtMs: Long,
    val kind: String, // CARE_DUE
)
