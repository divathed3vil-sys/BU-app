package com.bu.core.ledger

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
)
