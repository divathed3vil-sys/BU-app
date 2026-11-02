package com.bu.core.command

import com.bu.core.event.DomainEvent

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
    suspend fun pending(): List<OutboxEffect>
    suspend fun markDone(outboxId: String, atMs: Long)
}

data class CaptureRecord(
    val captureId: String,
    val rawText: String,
    val capturedAtMs: Long,
    val source: String,
    val status: String,
    val linkedCommandId: String?,
)

interface CaptureStore {
    suspend fun insert(capture: CaptureRecord)
    suspend fun updateStatus(captureId: String, status: String)
}
