package com.bu.identity.loop

/**
 * Momentum — a 7-day rolling ratio of tallies that landed, recomputed from the
 * ledger's momentum_cache (locked plan §2). There are no streaks and there is
 * no break: a missed day simply fades out of the window. Carrying counts as a
 * tally — the owner engaged — it just didn't land yet.
 */
object Momentum {

    /** One day of tallies, as cached in the ledger. */
    data class Day(val dateIso: String, val done: Int, val tallies: Int)

    data class Snapshot(
        val done: Int,
        val tallies: Int,
        val ratio: Double?, // null = no tallies yet this week
        val daysWithTallies: Int,
    )

    /**
     * Rolling 7-day window ending on [todayIso] inclusive. Rows outside the
     * window are ignored; days absent from the cache count as zero — they can
     * never punish, they only make the picture smaller.
     */
    fun snapshot(todayIso: String, rows: List<Day>): Snapshot {
        val byDate = rows.associateBy { it.dateIso }
        var done = 0
        var tallies = 0
        var days = 0
        for (offset in 0..6) {
            val date = todayIso.minusDays(offset.toLong())
            val row = byDate[date]
            if (row != null && row.tallies > 0) {
                days++
                done += row.done
                tallies += row.tallies
            }
        }
        val ratio = if (tallies > 0) done.toDouble() / tallies else null
        return Snapshot(done = done, tallies = tallies, ratio = ratio, daysWithTallies = days)
    }

    /** Zero-guilt headline (zero-guilt copy law: no "missed", no red, no streaks). */
    fun headline(snapshot: Snapshot): String {
        val snap = snapshot
        if (snap.tallies == 0) {
            return "No tallies yet — the first one is free."
        }
        val pct = (snap.ratio!! * 100).toInt()
        val core = "$pct% · ${snap.done} of ${snap.tallies} landed this week"
        return when {
            pct >= 80 -> "Momentum $core"
            pct >= 45 -> "Momentum $core — steady"
            else -> "Momentum $core — carried plans still count"
        }
    }

    private fun String.minusDays(days: Long): String =
        java.time.LocalDate.parse(this).minusDays(days).toString()
}
