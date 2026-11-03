package com.bu.core.outbox

import android.util.Log
import com.bu.core.command.OutboxEffect
import com.bu.core.command.OutboxStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * One handler per effect type. Handlers apply the world-changes that must
 * happen AFTER the ledger committed (at-least-once, idempotent by effect key):
 * capture interpretation, alarm projection, widget repaint, projection rebuilds.
 */
interface OutboxEffectHandler {
    val effectType: String

    /** Must be idempotent — redelivery of the same effect is normal. */
    suspend fun apply(effect: OutboxEffect)
}

/**
 * Drains the transactional outbox — effects are applied after commit,
 * at-least-once, idempotent by effect key (locked plan §5.2).
 * Effects that keep failing are dead-lettered after [MAX_ATTEMPTS] so one
 * bad row can never wedge the pipeline.
 */
@Singleton
class OutboxProcessor @Inject constructor(
    private val outbox: OutboxStore,
    private val handlers: Map<String, @JvmSuppressWildcards OutboxEffectHandler>,
) {

    suspend fun drain(maxEffects: Int = 100): Int {
        var processed = 0
        var rounds = 0
        while (rounds++ < MAX_ROUNDS) {
            val batch = outbox.pending(BATCH) ?: return processed
            if (batch.isEmpty()) break
            var appliedThisRound = 0
            for (effect in batch.take(maxEffects - processed)) {
                val handler = handlers[effect.effectType]
                if (handler == null) {
                    // Unknown effect types are acknowledged, not crashed on —
                    // forward compatibility between app versions.
                    outbox.markDone(effect.outboxId, 0L)
                    appliedThisRound++
                    continue
                }
                try {
                    handler.apply(effect)
                    outbox.markDone(effect.outboxId, 0L)
                    appliedThisRound++
                } catch (t: Throwable) {
                    Log.w(TAG, "effect ${effect.effectType}/${effect.effectKey} failed: ${t.message}")
                    val attempts = effect.attempts + 1
                    if (attempts >= MAX_ATTEMPTS) {
                        outbox.markFailed(effect.outboxId, t.message?.take(120) ?: "EFFECT_FAILED")
                    }
                    // Leave PENDING otherwise; a later drain retries it.
                }
                processed++
            }
            if (appliedThisRound == 0) break // everything left is failing; stop this drain
        }
        return processed
    }

    private companion object {
        const val TAG = "BuOutbox"
        const val BATCH = 20
        const val MAX_ROUNDS = 10
        const val MAX_ATTEMPTS = 8
    }
}
