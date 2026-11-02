package com.bu.core.ledger

import androidx.room.withTransaction
import com.bu.core.command.CaptureRecord
import com.bu.core.command.CaptureStore
import com.bu.core.command.CommandInboxStore
import com.bu.core.command.CommandRecord
import com.bu.core.command.CommandStatus
import com.bu.core.command.EventStore
import com.bu.core.command.OutboxEffect
import com.bu.core.command.OutboxStore
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
}

@Singleton
class RoomOutboxStore @Inject constructor(private val dao: OutboxDao) : OutboxStore {
    override suspend fun enqueue(effect: OutboxEffect) = dao.insert(effect.toRow())

    override suspend fun pending(): List<OutboxEffect> = dao.pending().map { it.toEffect() }

    override suspend fun markDone(outboxId: String, atMs: Long) = dao.markDone(outboxId)
}

@Singleton
class RoomCaptureStore @Inject constructor(private val dao: CaptureDao) : CaptureStore {
    override suspend fun insert(capture: CaptureRecord) = dao.insert(capture.toRow())

    override suspend fun updateStatus(captureId: String, status: String) =
        dao.updateStatus(captureId, status)
}

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
)

private fun CaptureRow.toRecord() = CaptureRecord(
    captureId = captureId,
    rawText = rawText,
    capturedAtMs = capturedAtMs,
    source = source,
    status = status,
    linkedCommandId = linkedCommandId,
)
