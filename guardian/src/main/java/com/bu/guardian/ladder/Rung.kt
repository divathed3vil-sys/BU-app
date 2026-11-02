package com.bu.guardian.ladder

/**
 * The Shield ladder — L0..L5 (locked plan §9).
 * Escalation is only ever legal inside a ceiling the owner sealed.
 */
enum class Rung(val level: Int, val label: String) {
    OBSERVE(0, "Observe"),
    NUDGE(1, "Nudge"),
    PAUSE(2, "Pause"),
    SOFT_LIMIT(3, "Soft limit"),
    HARD_LIMIT(4, "Hard limit"),
    CONTRACT_LOCK(5, "Contract lock"),
    ;

    companion object {
        fun fromLevel(level: Int): Rung = entries.first { it.level == level.coerceIn(0, 5) }
    }
}

/** Ceiling sealed at Dusk Close — the max firmness the owner consented to. */
data class Ceiling(val maxLevel: Int) {
    init {
        require(maxLevel in 0..5) { "ceiling must be 0..5" }
    }

    fun allows(rung: Rung): Boolean = rung.level <= maxLevel
}

object LadderPolicy {

    /**
     * One-rung escalation, never past the sealed ceiling (I11 — no hidden escalation).
     */
    fun escalate(current: Rung, ceiling: Ceiling): Rung =
        Rung.fromLevel((current.level + 1).coerceAtMost(ceiling.maxLevel))

    /**
     * De-escalate after compliant days (locked plan §9).
     */
    fun deEscalate(current: Rung, compliantDays: Int): Rung =
        if (compliantDays >= 3) Rung.fromLevel((current.level - 1).coerceAtLeast(0)) else current
}
