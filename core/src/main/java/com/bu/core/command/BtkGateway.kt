package com.bu.core.command

import com.bu.core.event.AggregateTypes
import com.bu.core.event.DomainEvent
import com.bu.core.event.EventTypes
import com.bu.core.time.BuClock
import javax.inject.Inject
import java.util.UUID

/**
 * The BU Temporal Kernel gateway — the ONLY mutation path in BU (locked plan §5.2).
 *
 * Flow: envelope → idempotency check → validate → transactional commit
 * (state + events + outbox in one transaction) → receipt.
 * Effects (alarms, FTS, embeddings, interpretations) are applied after
 * commit by the outbox processor, at-least-once, idempotent by effect key.
 */
class BtkGateway @Inject constructor(
    private val inbox: CommandInboxStore,
    private val events: EventStore,
    private val outbox: OutboxStore,
    private val captures: CaptureStore,
    private val tx: TransactionRunner,
    private val clock: BuClock,
) : CommandGateway {

    override suspend fun submit(command: BuCommand): CommandReceipt {
        val key = command.idempotencyKey ?: newId()

        // I5 — retry safety: a committed/received command with the same key returns its original receipt.
        inbox.findByIdempotencyKey(key)?.let { existing ->
            return CommandReceipt(
                commandId = existing.commandId,
                idempotencyKey = key,
                status = existing.status,
                reason = if (existing.status == CommandStatus.COMMITTED) null else existing.errorCode,
            )
        }

        val commandId = newId()
        val now = clock.nowMs()
        val envelope = CommandEnvelope(
            commandId = commandId,
            idempotencyKey = key,
            commandType = command.commandType,
            source = command.source,
            createdAtMs = now,
            actor = command.source.name,
            payloadJson = command.payloadJson(),
        )
        inbox.insert(
            CommandRecord(
                commandId = commandId,
                idempotencyKey = key,
                commandType = envelope.commandType,
                status = CommandStatus.RECEIVED,
                source = envelope.source.name,
                payloadJson = envelope.payloadJson,
                createdAtMs = now,
                updatedAtMs = now,
            ),
        )
        events.append(
            DomainEvent(
                eventId = newId(),
                type = EventTypes.COMMAND_RECEIVED,
                aggregateId = commandId,
                aggregateType = AggregateTypes.COMMAND,
                occurredAtMs = now,
                recordedAtMs = clock.nowMs(),
                actor = envelope.actor,
                payloadJson = envelope.payloadJson,
                correlationId = commandId,
                idempotencyKey = key,
            ),
        )

        return when (val verdict = Validators.validate(command)) {
            is Verdict.Reject -> {
                inbox.updateStatus(commandId, CommandStatus.REJECTED, verdict.reason, clock.nowMs())
                events.append(
                    DomainEvent(
                        eventId = newId(),
                        type = EventTypes.COMMAND_REJECTED,
                        aggregateId = commandId,
                        aggregateType = AggregateTypes.COMMAND,
                        occurredAtMs = clock.nowMs(),
                        recordedAtMs = clock.nowMs(),
                        actor = envelope.actor,
                        payloadJson = envelope.payloadJson,
                        correlationId = commandId,
                        idempotencyKey = key,
                    ),
                )
                CommandReceipt(commandId, key, CommandStatus.REJECTED, reason = verdict.reason)
            }

            is Verdict.Commit -> tx.inTransaction {
                commit(command, envelope)
            }
        }
    }

    override suspend fun approve(commandId: String, approval: Approval): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        return if (record.status == CommandStatus.AWAITING_APPROVAL) {
            // Approval flows arrive with AI proposals (P6). P0 keeps the contract.
            CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NO_PENDING_APPROVAL")
        } else {
            CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_AWAITING_APPROVAL")
        }
    }

    override suspend fun reject(commandId: String, reason: String?): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        if (record.status != CommandStatus.AWAITING_APPROVAL) {
            return CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_AWAITING_APPROVAL")
        }
        val now = clock.nowMs()
        inbox.updateStatus(commandId, CommandStatus.REJECTED, reason, now)
        events.append(rejectionEvent(record, reason, now))
        return CommandReceipt(commandId, record.idempotencyKey, CommandStatus.REJECTED, reason = reason)
    }

    override suspend fun undo(commandId: String): CommandReceipt {
        val record = inbox.findById(commandId)
            ?: return receiptUnknown(commandId)
        if (record.status != CommandStatus.COMMITTED) {
            return CommandReceipt(commandId, record.idempotencyKey, record.status, reason = "NOT_COMMITTED")
        }
        return tx.inTransaction { undo(record) }
    }

    private suspend fun undo(record: CommandRecord): CommandReceipt {
        val now = clock.nowMs()
        val savedEvent = events.findByCorrelation(record.commandId, EventTypes.CAPTURE_SAVED)
        return if (savedEvent != null) {
            captures.updateStatus(savedEvent.aggregateId, "DISCARDED")
            events.append(
                DomainEvent(
                    eventId = newId(),
                    type = EventTypes.CAPTURE_DISCARDED,
                    aggregateId = savedEvent.aggregateId,
                    aggregateType = AggregateTypes.CAPTURE,
                    occurredAtMs = now,
                    recordedAtMs = now,
                    actor = record.source,
                    payloadJson = record.payloadJson,
                    correlationId = record.commandId,
                ),
            )
            outbox.enqueue(
                OutboxEffect(
                    outboxId = newId(),
                    effectType = "PROJECTIONS_DIRTY",
                    effectKey = "${savedEvent.aggregateId}:undo",
                    payloadJson = record.payloadJson,
                    createdAtMs = now,
                ),
            )
            events.append(
                DomainEvent(
                    eventId = newId(),
                    type = EventTypes.COMMAND_UNDONE,
                    aggregateId = record.commandId,
                    aggregateType = AggregateTypes.COMMAND,
                    occurredAtMs = now,
                    recordedAtMs = now,
                    actor = record.source,
                    payloadJson = record.payloadJson,
                    correlationId = record.commandId,
                ),
            )
            CommandReceipt(record.commandId, record.idempotencyKey, CommandStatus.UNDONE)
        } else {
            CommandReceipt(record.commandId, record.idempotencyKey, record.status, reason = "NO_COMPENSATION_DEFINED")
        }
    }

    private suspend fun commit(command: BuCommand, envelope: CommandEnvelope): CommandReceipt {
        val now = clock.nowMs()
        return when (command) {
            is BuCommand.SaveCapture -> {
                val captureId = newId()
                val event = DomainEvent(
                    eventId = newId(),
                    type = EventTypes.CAPTURE_SAVED,
                    aggregateId = captureId,
                    aggregateType = AggregateTypes.CAPTURE,
                    occurredAtMs = now,
                    recordedAtMs = now,
                    actor = envelope.actor,
                    payloadJson = envelope.payloadJson,
                    correlationId = envelope.commandId,
                    idempotencyKey = envelope.idempotencyKey,
                )
                captures.insert(
                    CaptureRecord(
                        captureId = captureId,
                        rawText = command.text,
                        capturedAtMs = now,
                        source = command.captureSource,
                        status = "RAW_CAPTURED",
                        linkedCommandId = envelope.commandId,
                    ),
                )
                events.append(event)
                outbox.enqueue(
                    OutboxEffect(
                        outboxId = newId(),
                        effectType = "PROJECTIONS_DIRTY",
                        effectKey = captureId,
                        payloadJson = envelope.payloadJson,
                        createdAtMs = now,
                    ),
                )
                outbox.enqueue(
                    OutboxEffect(
                        outboxId = newId(),
                        effectType = "CAPTURE_INTERPRET",
                        effectKey = "$captureId:interpret",
                        payloadJson = envelope.payloadJson,
                        createdAtMs = now,
                    ),
                )
                inbox.updateStatus(envelope.commandId, CommandStatus.COMMITTED, null, clock.nowMs())
                CommandReceipt(
                    commandId = envelope.commandId,
                    idempotencyKey = envelope.idempotencyKey,
                    status = CommandStatus.COMMITTED,
                    eventIds = listOf(event.eventId),
                )
            }
        }
    }

    private fun rejectionEvent(record: CommandRecord, reason: String?, now: Long) = DomainEvent(
        eventId = newId(),
        type = EventTypes.COMMAND_REJECTED,
        aggregateId = record.commandId,
        aggregateType = AggregateTypes.COMMAND,
        occurredAtMs = now,
        recordedAtMs = now,
        actor = record.source,
        payloadJson = record.payloadJson,
        correlationId = record.commandId,
        idempotencyKey = record.idempotencyKey,
    )

    private fun receiptUnknown(commandId: String) = CommandReceipt(
        commandId = commandId,
        idempotencyKey = "",
        status = CommandStatus.FAILED,
        reason = "UNKNOWN_COMMAND",
    )

    private fun newId(): String = UUID.randomUUID().toString()
}

/** The only mutation gateway in BU (locked plan §5.2). */
interface CommandGateway {
    suspend fun submit(command: BuCommand): CommandReceipt
    suspend fun approve(commandId: String, approval: Approval): CommandReceipt
    suspend fun reject(commandId: String, reason: String?): CommandReceipt
    suspend fun undo(commandId: String): CommandReceipt
}

/** Approval marker — typed so approvals can never be smuggled in as flags. */
data class Approval(val byActor: String, val atMs: Long, val note: String? = null)
