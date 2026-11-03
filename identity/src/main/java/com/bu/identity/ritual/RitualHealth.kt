package com.bu.identity.ritual

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/**
 * Pure ritual intelligence (locked plan §2, §13 P2) — zero Android imports,
 * JVM-testable. Three laws live here:
 *
 *  1. **The moved-3 law.** A task carried three times has used its cooldown;
 *     only Do, Shrink, or Kill remain. Never a punishment — a decision point.
 *  2. **Recommitment escalation.** Days without a Dusk Close raise the nudge
 *     level gently. The word "missed" never appears anywhere (zero-guilt copy
 *     law); the copy speaks of nights that "went by without a close".
 *  3. **The interrupt budget.** Care may interrupt a bounded number of times
 *     per day; the rest waits in the quiet digest. Nothing is lost — the
 *     ledger keeps every item regardless of how it was delivered.
 */
object RitualHealth {

    enum class Nudge { NONE, GENTLE, CATCH_UP, RECOMMIT }

    const val CARRY_LIMIT = 3

    /** True when the carry lane is closed for this task (movedCount counts carries so far). */
    fun carryBlocked(movedCount: Int): Boolean = movedCount >= CARRY_LIMIT

    /**
     * Recommitment escalation, counted from [todayIso] backwards over the set
     * of sealed contract dates. A seal made yesterday evening targets today —
     * so "today sealed" means last night closed. An empty ledger is day one,
     * never a failure: no history, no nudge.
     */
    fun duskNudge(todayIso: String, sealedDates: Set<String>): Nudge {
        if (sealedDates.isEmpty()) return Nudge.NONE
        var misses = 0
        var cursor = LocalDate.parse(todayIso)
        while (!sealedDates.contains(cursor.toString())) {
            misses++
            cursor = cursor.minusDays(1)
            if (misses >= 30) break // never loop forever over an unsealed past
        }
        return when {
            misses == 0 -> Nudge.NONE
            misses == 1 -> Nudge.GENTLE
            misses == 2 -> Nudge.CATCH_UP
            else -> Nudge.RECOMMIT
        }
    }

    /** Zero-guilt nudge copy — "missed" is banned; the loop is patient. */
    fun nudgeCopy(nudge: Nudge): String = when (nudge) {
        Nudge.NONE -> ""
        Nudge.GENTLE -> "Last night went by without a close. No worry — tonight's is ready."
        Nudge.CATCH_UP -> "Two nights without a close. The loop is patient; one close catches everything up."
        Nudge.RECOMMIT -> "A few nights passed. One decision right now re-anchors the whole system: set today's Front."
    }

    // ── Dusk Close receipt (auto-filled, zero typing) ─────────────────────────

    data class Receipt(val planned: Int, val landed: Int, val carried: Int, val letGo: Int) {
        fun headline(): String = when {
            planned == 0 -> "Nothing was planned — a rest day is still a day."
            else -> "$planned planned · $landed landed" +
                (if (carried > 0) " · $carried carried" else "") +
                (if (letGo > 0) " · $letGo let go" else "")
        }
    }

    // ── Tomorrow's draft (auto-drafted from triage, editable to zero) ─────────

    /**
     * The seal's draft: carried and shrunk items become tomorrow's first
     * lines, capped at three (the Big Three law). The first line carries the
     * Front mark; the owner edits everything before sealing.
     */
    fun draftTomorrow(carried: List<String>, shrunk: List<String>): List<String> =
        (carried + shrunk).map { it.trim() }.filter { it.isNotEmpty() }.distinct().take(3)

    // ── Care engine v3 (locked plan §13 P2) ───────────────────────────────────

    /**
     * Why-now — the line that can never be absent (locked plan §2). Every care
     * notification and every care row states why it is surfacing now.
     */
    fun whyNow(careType: String?, dueAtMs: Long, zone: ZoneId, today: LocalDate): String =
        when (careType) {
            "ANCHOR" -> "Daily rhythm · keeps the day's bones"
            "HABIT" -> "Daily repeat · rhythm, not streaks"
            "DEADLINE" -> {
                val due = Instant.ofEpochMilli(dueAtMs).atZone(zone).toLocalDate()
                when (due) {
                    today -> "Due today"
                    today.plusDays(1) -> "Due tomorrow"
                    else -> "Due $due"
                }
            }
            "RELATIONAL" -> "Someone is waiting"
            "VALUE" -> "This feeds what you value"
            else -> "Relevant right now"
        }

    /**
     * The escalation chain's visible edge: a DUE item that has already waited
     * past its day says so calmly. Null while it is still today's business.
     */
    fun waitingLine(dueAtMs: Long, nowMs: Long, zone: ZoneId): String? {
        val dueDate = Instant.ofEpochMilli(dueAtMs).atZone(zone).toLocalDate()
        val today = Instant.ofEpochMilli(nowMs).atZone(zone).toLocalDate()
        val days = ChronoUnit.DAYS.between(dueDate, today)
        return when {
            days <= 0 -> null
            days == 1 -> "waiting since yesterday"
            else -> "waiting $days days"
        }
    }

    /** The interrupt budget decision — bounded interruptions, quiet overflow. */
    object NotificationBudget {
        enum class Decision { INTERRUPT, DIGEST }

        const val DEFAULT_BUDGET = 5

        fun decide(deliveredToday: Int, budget: Int = DEFAULT_BUDGET): Decision =
            if (deliveredToday < budget.coerceAtLeast(1)) Decision.INTERRUPT else Decision.DIGEST
    }

    /** Mood words for the Receipt step — five quiet words, no faces, no shame. */
    fun moodLabel(mood: Int): String = when (mood) {
        1 -> "heavy"
        2 -> "low"
        3 -> "steady"
        4 -> "good"
        else -> "bright"
    }
}
