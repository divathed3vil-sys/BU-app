package com.bu.core.command

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Typed commands (locked plan §5.2). Every state change in BU enters
 * through [CommandGateway] as one of these. P0 ships the capture slice;
 * P1 adds plan/tally/reminder commands.
 */
sealed interface BuCommand {
    /** Client-supplied key; retries with the same key never duplicate effects (I5). */
    val idempotencyKey: String?
    val source: CommandSource
    val commandType: String

    data class SaveCapture(
        val text: String,
        val captureSource: String = "PEBBLE",
        override val source: CommandSource = CommandSource.USER_UI,
        override val idempotencyKey: String? = null,
    ) : BuCommand {
        override val commandType: String = "SAVE_CAPTURE"
    }
}

@Serializable
data class SaveCapturePayload(val text: String, val captureSource: String)

private val json = Json { encodeDefaults = true }

/** Serializes a command into its durable envelope payload. */
fun BuCommand.payloadJson(): String = when (this) {
    is BuCommand.SaveCapture ->
        json.encodeToString(
            SaveCapturePayload(text = text, captureSource = captureSource),
        )
}
