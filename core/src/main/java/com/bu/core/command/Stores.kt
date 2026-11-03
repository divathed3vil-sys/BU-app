package com.bu.core.command

import com.bu.core.event.DomainEvent
import com.bu.core.ledger.CarryRow
import com.bu.core.ledger.DayContractRow
import com.bu.core.ledger.FocusSessionRow
import com.bu.core.ledger.ObjectRow
import com.bu.core.ledger.RelationshipRow
import com.bu.core.ledger.ReminderRow
import com.bu.core.ledger.RevisionRow
import com.bu.core.ledger.TaskRow
import com.bu.core.ledger.TrackRow

/** Persistence-agnostic stores the kernel is built on (I2 single authority). */
interface TransactionRunner {
    suspend fun <T> inTransaction(block: suspend () -> T): T
}

interface CommandInboxStore {
    suspend fun insert(record: CommandRecord)
    suspend fun updateStatus(commandId: String, status: CommandStatus, errorCode: String? = null, atMs: Long)
    suspend fun findByIdempotencyKey(key: String): CommandRecord?
    suspend fun findById(commandId: String): CommandRecord?
}

interface EventStore {
    suspend fun append(event: DomainEvent)
    suspend fun findByCorrelation(commandId: String, type: String): DomainEvent?

    /** Idempotency probe for once-per-day rituals (Dawn Push). */
    suspend fun findByTypeAndAggregate(type: String, aggregateId: String): DomainEvent?
}

data class OutboxEffect(
    val outboxId: String,
    val effectType: String,
    val effectKey: String,
    val payloadJson: String,
    val status: String = "PENDING",
    val attempts: Int = 0,
    val createdAtMs: Long,
)

interface OutboxStore {
    suspend fun enqueue(effect: OutboxEffect)
    suspend fun pending(limit: Int): List<OutboxEffect>
    suspend fun markDone(outboxId: String, atMs: Long)
    suspend fun markFailed(outboxId: String, code: String)
}

data class CaptureRecord(
    val captureId: String,
    val rawText: String,
    val capturedAtMs: Long,
    val source: String,
    val status: String,
    val linkedCommandId: String?,
    val taskObjectId: String? = null,
)

interface CaptureStore {
    suspend fun insert(capture: CaptureRecord)
    suspend fun updateStatus(captureId: String, status: String)
    suspend fun findById(captureId: String): CaptureRecord?
    suspend fun linkTask(captureId: String, status: String, taskObjectId: String?, atMs: Long)
}

// ── Object plane ─────────────────────────────────────────────────────────────

interface ObjectStore {
    suspend fun insert(row: ObjectRow)
    suspend fun byId(objectId: String): ObjectRow?
    suspend fun rename(objectId: String, title: String, atMs: Long)
    suspend fun archive(objectId: String, atMs: Long)
    suspend fun insertRelationship(row: RelationshipRow)
    suspend fun insertRevision(row: RevisionRow)
    suspend fun revisionCount(objectId: String): Int
}

interface TrackStore {
    /** Upsert semantics — tracks are edited in place, revisions record history. */
    suspend fun save(row: TrackRow)
    suspend fun byId(objectId: String): TrackRow?
    suspend fun archive(objectId: String, atMs: Long)
}

interface TaskStore {
    suspend fun insert(row: TaskRow)
    suspend fun byId(objectId: String): TaskRow?
    suspend fun replaceOpenBig(dateIso: String, atMs: Long)
    suspend fun setTally(objectId: String, status: String, atMs: Long, dateIso: String, actualMinutes: Int?)
    suspend fun setSchedule(objectId: String, toDate: String, startMinute: Int?, movedCount: Int, atMs: Long)
    suspend fun overlappingBlock(dateIso: String, startMinute: Int, endMinute: Int): TaskRow?
    suspend fun insertCarry(row: CarryRow)

    /** Triage: the task returns smaller — the estimate lands honestly. */
    suspend fun setEstimate(objectId: String, minutes: Int, atMs: Long)

    /** Triage: let go — soft kill, tombstone stays in the ledger (I10). */
    suspend fun kill(objectId: String, atMs: Long)
}

interface ContractStore {
    suspend fun upsert(row: DayContractRow)
    suspend fun byDate(dateIso: String): DayContractRow?
}

interface ReminderStore {
    suspend fun insert(row: ReminderRow)
    suspend fun byId(reminderId: String): ReminderRow?
    suspend fun pending(): List<ReminderRow>
    suspend fun setStatus(reminderId: String, status: String, atMs: Long)
    suspend fun reschedule(reminderId: String, dueAtMs: Long, atMs: Long)
    suspend fun setAlarmExact(reminderId: String, exact: Boolean, atMs: Long)
}

interface FocusStore {
    suspend fun insert(row: FocusSessionRow)
    suspend fun byId(sessionId: String): FocusSessionRow?
    suspend fun running(): FocusSessionRow?

    /** Returns 1 when a RUNNING row moved, 0 when nothing was running. */
    suspend fun end(sessionId: String, state: String, endedAtMs: Long, actualMinutes: Int?): Int
}

interface SettingsStore {
    suspend fun set(key: String, value: String)
}

interface EvidenceStore {
    suspend fun insert(subjectId: String, subjectType: String, grade: String, statement: String, atMs: Long)
}

/** Recomputes one day of the momentum cache from the tally ledger. */
interface MomentumStore {
    suspend fun recountDay(dateIso: String)
}

/**
 * The domain stores, bundled — one constructor seam for the gateway, one fake
 * for JVM tests. Each store stays a pure interface (I2: single authority per
 * field; the gateway is the only writer). Open only so the Room bundle can
 * subclass it for DI; no behaviour is overridden.
 */
open class DomainStores(
    val objects: ObjectStore,
    val tracks: TrackStore,
    val tasks: TaskStore,
    val contracts: ContractStore,
    val reminders: ReminderStore,
    val focus: FocusStore,
    val settings: SettingsStore,
    val evidence: EvidenceStore,
    val momentum: MomentumStore,
)
