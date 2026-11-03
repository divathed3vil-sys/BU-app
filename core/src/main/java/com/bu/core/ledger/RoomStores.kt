package com.bu.core.ledger

import androidx.room.withTransaction
import com.bu.core.command.CaptureRecord
import com.bu.core.command.CaptureStore
import com.bu.core.command.CommandInboxStore
import com.bu.core.command.CommandRecord
import com.bu.core.command.CommandStatus
import com.bu.core.command.ContractStore
import com.bu.core.command.DomainStores
import com.bu.core.command.EventStore
import com.bu.core.command.EvidenceStore
import com.bu.core.command.FocusStore
import com.bu.core.command.MomentumStore
import com.bu.core.command.ObjectStore
import com.bu.core.command.OutboxEffect
import com.bu.core.command.OutboxStore
import com.bu.core.command.ReminderStore
import com.bu.core.command.SettingsStore
import com.bu.core.command.TaskStore
import com.bu.core.command.TrackStore
import com.bu.core.command.TransactionRunner
import com.bu.core.event.DomainEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomTransactionRunner @Inject constructor(private val db: BuDatabase) : TransactionRunner {
    override suspend fun <T> inTransaction(block: suspend () -> T): T =
        db.withTransaction { block() }
}

@Singleton
class RoomCommandInboxStore @Inject constructor(private val dao: CommandDao) : CommandInboxStore {
    override suspend fun insert(record: CommandRecord) = dao.insert(record.toRow())

    override suspend fun updateStatus(commandId: String, status: CommandStatus, errorCode: String?, atMs: Long) =
        dao.updateStatus(commandId, status.name, errorCode, atMs)

    override suspend fun findByIdempotencyKey(key: String): CommandRecord? =
        dao.findByIdempotencyKey(key)?.toRecord()

    override suspend fun findById(commandId: String): CommandRecord? =
        dao.findById(commandId)?.toRecord()
}

@Singleton
class RoomEventStore @Inject constructor(private val dao: EventDao) : EventStore {
    override suspend fun append(event: DomainEvent) = dao.insert(event.toRow())

    override suspend fun findByCorrelation(commandId: String, type: String): DomainEvent? =
        dao.findByCorrelation(commandId, type)?.toEvent()

    override suspend fun findByTypeAndAggregate(type: String, aggregateId: String): DomainEvent? =
        dao.findByTypeAndAggregate(type, aggregateId)?.toEvent()
}

@Singleton
class RoomOutboxStore @Inject constructor(private val dao: OutboxDao) : OutboxStore {
    override suspend fun enqueue(effect: OutboxEffect) = dao.insert(effect.toRow())

    override suspend fun pending(limit: Int): List<OutboxEffect> =
        dao.pending(limit).map { it.toEffect() }

    override suspend fun markDone(outboxId: String, atMs: Long) = dao.markDone(outboxId)

    override suspend fun markFailed(outboxId: String, code: String) = dao.markFailed(outboxId, code)
}

@Singleton
class RoomCaptureStore @Inject constructor(private val dao: CaptureDao) : CaptureStore {
    override suspend fun insert(capture: CaptureRecord) = dao.insert(capture.toRow())

    override suspend fun updateStatus(captureId: String, status: String) =
        dao.updateStatus(captureId, status)

    override suspend fun findById(captureId: String): CaptureRecord? =
        dao.findById(captureId)?.toRecord()

    override suspend fun linkTask(captureId: String, status: String, taskObjectId: String?, atMs: Long) =
        dao.interpret(captureId, status, taskObjectId, atMs)
}

@Singleton
class RoomObjectStore @Inject constructor(private val dao: ObjectDao) : ObjectStore {
    override suspend fun insert(row: ObjectRow) = dao.insert(row)
    override suspend fun byId(objectId: String): ObjectRow? = dao.findById(objectId)
    override suspend fun rename(objectId: String, title: String, atMs: Long) = dao.rename(objectId, title, atMs)
    override suspend fun archive(objectId: String, atMs: Long) = dao.archive(objectId, atMs)
    override suspend fun insertRelationship(row: RelationshipRow) = dao.insertRelationship(row)
    override suspend fun insertRevision(row: RevisionRow) = dao.insertRevision(row)
    override suspend fun revisionCount(objectId: String): Int = dao.revisionCount(objectId)
}

@Singleton
class RoomTrackStore @Inject constructor(private val dao: TrackDao) : TrackStore {
    override suspend fun save(row: TrackRow) = dao.upsertTrack(row)
    override suspend fun byId(objectId: String): TrackRow? = dao.findById(objectId)
    override suspend fun archive(objectId: String, atMs: Long) = dao.archive(objectId, atMs)
}

@Singleton
class RoomTaskStore @Inject constructor(private val dao: TaskDao, private val carryDao: CarryDao) : TaskStore {
    override suspend fun insert(row: TaskRow) = dao.insert(row)
    override suspend fun byId(objectId: String): TaskRow? = dao.findById(objectId)
    override suspend fun replaceOpenBig(dateIso: String, atMs: Long) = dao.replaceOpenBig(dateIso, atMs)

    override suspend fun setTally(
        objectId: String,
        status: String,
        atMs: Long,
        dateIso: String,
        actualMinutes: Int?,
    ) = dao.setTally(objectId, status, atMs, dateIso, actualMinutes)

    override suspend fun setSchedule(
        objectId: String,
        toDate: String,
        startMinute: Int?,
        movedCount: Int,
        atMs: Long,
    ) = dao.setSchedule(objectId, toDate, startMinute, movedCount, atMs)

    override suspend fun overlappingBlock(dateIso: String, startMinute: Int, endMinute: Int): TaskRow? =
        dao.overlappingBlock(dateIso, startMinute, endMinute)

    override suspend fun insertCarry(row: CarryRow) = carryDao.insert(row)

    override suspend fun setEstimate(objectId: String, minutes: Int, atMs: Long) =
        dao.setEstimate(objectId, minutes, atMs)

    override suspend fun kill(objectId: String, atMs: Long) = dao.kill(objectId, atMs)
}

@Singleton
class RoomContractStore @Inject constructor(private val dao: ContractDao) : ContractStore {
    override suspend fun upsert(row: DayContractRow) = dao.upsert(row)
    override suspend fun byDate(dateIso: String): DayContractRow? = dao.byDate(dateIso)
}

@Singleton
class RoomReminderStore @Inject constructor(private val dao: ReminderDao) : ReminderStore {
    override suspend fun insert(row: ReminderRow) = dao.insert(row)
    override suspend fun byId(reminderId: String): ReminderRow? = dao.findById(reminderId)
    override suspend fun pending(): List<ReminderRow> = dao.listPending()
    override suspend fun setStatus(reminderId: String, status: String, atMs: Long) =
        dao.setStatus(reminderId, status, atMs)

    override suspend fun reschedule(reminderId: String, dueAtMs: Long, atMs: Long) =
        dao.reschedule(reminderId, dueAtMs, atMs)

    override suspend fun setAlarmExact(reminderId: String, exact: Boolean, atMs: Long) =
        dao.setAlarmExact(reminderId, exact, atMs)
}

@Singleton
class RoomFocusStore @Inject constructor(private val dao: FocusDao) : FocusStore {
    override suspend fun insert(row: FocusSessionRow) = dao.insert(row)
    override suspend fun byId(sessionId: String): FocusSessionRow? = dao.findById(sessionId)
    override suspend fun running(): FocusSessionRow? = dao.running()

    override suspend fun end(sessionId: String, state: String, endedAtMs: Long, actualMinutes: Int?) =
        dao.end(sessionId, state, endedAtMs, actualMinutes)
}

@Singleton
class RoomSettingsStore @Inject constructor(private val dao: SettingsDao) : SettingsStore {
    override suspend fun set(key: String, value: String) = dao.upsert(SettingRow(key, value))
}

@Singleton
class RoomEvidenceStore @Inject constructor(private val dao: EvidenceDao) : EvidenceStore {
    override suspend fun insert(
        subjectId: String,
        subjectType: String,
        grade: String,
        statement: String,
        atMs: Long,
    ) = dao.insert(
        EvidenceRow(
            evidenceId = java.util.UUID.randomUUID().toString(),
            subjectId = subjectId,
            subjectType = subjectType,
            grade = grade,
            statement = statement,
            occurredAtMs = atMs,
        ),
    )
}

/**
 * Momentum day recount — the 7-day rolling ratio's raw material.
 * done = tallies that landed; tally = every tally action that day
 * (done · partial · carried — carrying is a tally too, never a failure).
 */
@Singleton
class RoomMomentumStore @Inject constructor(private val dao: MomentumDao, private val taskDao: TaskDao) : MomentumStore {
    override suspend fun recountDay(dateIso: String) {
        val done = taskDao.countDoneOnDate(dateIso)
        val tally = taskDao.countTalliedOnDate(dateIso)
        dao.upsert(MomentumRow(dateIso, done, tally))
    }
}

@Singleton
class RoomDomainStores @Inject constructor(
    objects: RoomObjectStore,
    tracks: RoomTrackStore,
    tasks: RoomTaskStore,
    contracts: RoomContractStore,
    reminders: RoomReminderStore,
    focus: RoomFocusStore,
    settings: RoomSettingsStore,
    evidence: RoomEvidenceStore,
    momentum: RoomMomentumStore,
) : DomainStores(
    objects = objects,
    tracks = tracks,
    tasks = tasks,
    contracts = contracts,
    reminders = reminders,
    focus = focus,
    settings = settings,
    evidence = evidence,
    momentum = momentum,
)

// ── mappers ──────────────────────────────────────────────────────────────────

private fun CommandRow.toRecord() = CommandRecord(
    commandId = commandId,
    idempotencyKey = idempotencyKey,
    commandType = commandType,
    status = CommandStatus.valueOf(status),
    source = source,
    payloadJson = payloadJson,
    createdAtMs = createdAtMs,
    updatedAtMs = updatedAtMs,
    errorCode = errorCode,
)

private fun CommandRecord.toRow() = CommandRow(
    commandId = commandId,
    idempotencyKey = idempotencyKey,
    commandType = commandType,
    status = status.name,
    source = source,
    payloadJson = payloadJson,
    createdAtMs = createdAtMs,
    updatedAtMs = updatedAtMs,
    errorCode = errorCode,
)

private fun EventRow.toEvent() = DomainEvent(
    eventId = eventId,
    type = type,
    aggregateId = aggregateId,
    aggregateType = aggregateType,
    occurredAtMs = occurredAtMs,
    recordedAtMs = recordedAtMs,
    actor = actor,
    payloadJson = payloadJson,
    schemaVersion = schemaVersion,
    correlationId = correlationId,
    idempotencyKey = idempotencyKey,
)

private fun DomainEvent.toRow() = EventRow(
    eventId = eventId,
    type = type,
    aggregateId = aggregateId,
    aggregateType = aggregateType,
    occurredAtMs = occurredAtMs,
    recordedAtMs = recordedAtMs,
    actor = actor,
    payloadJson = payloadJson,
    schemaVersion = schemaVersion,
    correlationId = correlationId,
    idempotencyKey = idempotencyKey,
)

private fun OutboxRow.toEffect() = OutboxEffect(
    outboxId = outboxId,
    effectType = effectType,
    effectKey = effectKey,
    payloadJson = payloadJson,
    status = status,
    attempts = attempts,
    createdAtMs = createdAtMs,
)

private fun OutboxEffect.toRow() = OutboxRow(
    outboxId = outboxId,
    effectType = effectType,
    effectKey = effectKey,
    payloadJson = payloadJson,
    status = status,
    attempts = attempts,
    createdAtMs = createdAtMs,
)

private fun CaptureRecord.toRow() = CaptureRow(
    captureId = captureId,
    rawText = rawText,
    capturedAtMs = capturedAtMs,
    source = source,
    status = status,
    linkedCommandId = linkedCommandId,
    taskObjectId = taskObjectId,
)

private fun CaptureRow.toRecord() = CaptureRecord(
    captureId = captureId,
    rawText = rawText,
    capturedAtMs = capturedAtMs,
    source = source,
    status = status,
    linkedCommandId = linkedCommandId,
    taskObjectId = taskObjectId,
)
