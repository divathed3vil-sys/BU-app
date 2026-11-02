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
    const val CAPTURE_SAVED = "CAPTURE_SAVED"
    const val CAPTURE_DISCARDED = "CAPTURE_DISCARDED"
    const val PROJECTIONS_DIRTY = "PROJECTIONS_DIRTY"
}

object AggregateTypes {
    const val COMMAND = "COMMAND"
    const val CAPTURE = "CAPTURE"
}
