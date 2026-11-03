package com.bu.core.outbox

import javax.inject.Qualifier

/**
 * Dagger map key for outbox effect handlers — handlers register by effect type,
 * the processor looks them up by the same string the gateway enqueued.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class EffectKey(val value: String)

/**
 * PROJECTIONS_DIRTY — the projection-rebuild signal. FTS/graph/momentum
 * rebuilds read their inputs transactionally already; this handler exists so
 * the effect type is first-class and gains real work as projections grow (P3+).
 */
@Singleton
class ProjectionsDirtyHandler @Inject constructor() : OutboxEffectHandler {
    override val effectType: String = "PROJECTIONS_DIRTY"
    override suspend fun apply(effect: OutboxEffect) {
        // Acknowledged by the processor; rebuild work lands with P3 projections.
    }
}
