package com.bu.core.command

/** Where a command entered the kernel (locked plan §5.3). */
enum class CommandSource { USER_UI, AI_PROPOSAL, ANDROID_EVENT, IMPORT, SYSTEM_RECONCILIATION }

/** Durable command state machine (locked plan §5.2). */
enum class CommandStatus {
    RECEIVED,
    INTERPRETING,
    PROPOSED,
    VALIDATING,
    AWAITING_APPROVAL,
    READY,
    COMMITTING,
    COMMITTED,
    FAILED,
    REJECTED,
    UNDONE,
}

/** The typed envelope every mutation carries through the kernel. */
data class CommandEnvelope(
    val commandId: String,
    val idempotencyKey: String,
    val commandType: String,
    val source: CommandSource,
    val createdAtMs: Long,
    val actor: String,
    val correlationId: String? = null,
    val payloadJson: String,
    val schemaVersion: Int = 1,
)

/** What the kernel hands back after a command passes through it. */
data class CommandReceipt(
    val commandId: String,
    val idempotencyKey: String,
    val status: CommandStatus,
    val eventIds: List<String> = emptyList(),
    val reason: String? = null,
)

/** Inbox row as the gateway sees it (persistence-agnostic). */
data class CommandRecord(
    val commandId: String,
    val idempotencyKey: String,
    val commandType: String,
    val status: CommandStatus,
    val source: String,
    val payloadJson: String,
    val createdAtMs: Long,
    val updatedAtMs: Long,
    val errorCode: String? = null,
)
