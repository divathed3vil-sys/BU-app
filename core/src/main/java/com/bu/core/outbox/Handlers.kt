package com.bu.core.outbox

/**
 * Dagger map key for outbox effect handlers — handlers register by effect type,
 * the processor looks them up by the same string the gateway enqueued.
 *
 * NOTE: deliberately NOT a @Qualifier. A Dagger map key and a qualifier are
 * different instruments; combining them makes the key invisible to @IntoMap.
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
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
