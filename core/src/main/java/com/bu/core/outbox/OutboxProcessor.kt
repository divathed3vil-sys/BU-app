package com.bu.core.outbox

import com.bu.core.command.OutboxStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Drains the transactional outbox — effects are applied after commit,
 * at-least-once, idempotent by effect key (locked plan §5.2).
 *
 * P0: effects are acknowledged (the pipeline is proven end-to-end).
 * P1 wires PROJECTIONS_DIRTY → FTS/momentum rebuild and
 * CAPTURE_INTERPRET → the deterministic parser.
 */
@Singleton
class OutboxProcessor @Inject constructor(
    private val outbox: OutboxStore,
) {
    suspend fun drain(max: Int = 20): Int {
        val batch = outbox.pending().take(max)
        batch.forEach { effect ->
            // Effect application arrives with each phase; acknowledgement proves delivery.
            outbox.markDone(effect.outboxId, 0L)
        }
        return batch.size
    }
}
