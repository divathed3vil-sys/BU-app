package com.bu.core.outbox

import com.bu.core.command.BuCommand
import com.bu.core.command.CaptureStore
import com.bu.core.command.CommandGateway
import com.bu.core.command.InterpretTicketPayload
import com.bu.core.interpret.CaptureInterpreter
import com.bu.core.time.BuClock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json

/**
 * Applies the CAPTURE_INTERPRET effect: the second clock of universal capture
 * (locked plan §5.5). The text is already durable; this decides what it was.
 *
 * The verdict re-enters the kernel as a command — the outbox never writes
 * state directly (I2). Idempotent: re-interpreting an already-interpreted
 * capture commits as a no-op inside the gateway.
 */
@Singleton
class CaptureInterpretHandler @Inject constructor(
    private val gateway: CommandGateway,
    private val captures: CaptureStore,
    private val clock: BuClock,
) : OutboxEffectHandler {

    override val effectType: String = "CAPTURE_INTERPRET"

    private val interpreter = CaptureInterpreter()

    override suspend fun apply(effect: OutboxEffect) {
        val captureId = parseCaptureId(effect)
            ?: throw IllegalArgumentException("INTERPRET_TICKET_MALFORMED")

        val capture = captures.findById(captureId)
            ?: throw IllegalArgumentException("CAPTURE_NOT_FOUND")

        val now = LocalDateTime.ofInstant(Instant.ofEpochMilli(clock.nowMs()), ZONE)
        val verdict = interpreter.interpret(capture.rawText, now, ZONE)

        gateway.submit(
            BuCommand.InterpretCapture(
                captureId = captureId,
                isTask = verdict.kind == CaptureInterpreter.Kind.TASK,
                title = verdict.title,
                scheduledDateIso = verdict.scheduledDateIso,
                startMinute = verdict.startMinute,
                scheduledAtMs = verdict.scheduledAtMs,
            ),
        )
    }

    private fun parseCaptureId(effect: OutboxEffect): String? = try {
        Json.decodeFromString(InterpretTicketPayload.serializer(), effect.payloadJson).captureId
    } catch (t: Throwable) {
        // P0 rows carried only the raw command payload; the capture id lives
        // in the effect key: "<captureId>:interpret".
        effect.effectKey.substringBefore(":interpret").takeIf { it.isNotBlank() && it != effect.effectKey }
    }

    private companion object {
        // The device's home zone. Locked to the owner's zone until settings
        // expose a travel zone; wall-clock intent is recorded on tasks.
        val ZONE: ZoneId = ZoneId.of("Asia/Colombo")
    }
}
