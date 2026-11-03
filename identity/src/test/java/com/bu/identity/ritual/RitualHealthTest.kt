package com.bu.identity.ritual

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId

/**
 * Pure ritual law — the moved-3 rule, recommitment escalation, tomorrow's
 * draft, why-now lines, the waiting chain, and the interrupt budget.
 */
class RitualHealthTest {

    private val zone: ZoneId = ZoneId.of("Asia/Colombo")

    // ── The moved-3 law ───────────────────────────────────────────────────────

    @Test
    fun `carry stays open below the limit`() {
        assertFalse(RitualHealth.carryBlocked(0))
        assertFalse(RitualHealth.carryBlocked(1))
        assertFalse(RitualHealth.carryBlocked(2))
    }

    @Test
    fun `third carry closes the lane`() {
        assertTrue(RitualHealth.carryBlocked(3))
        assertTrue(RitualHealth.carryBlocked(4))
    }

    // ── Recommitment escalation ───────────────────────────────────────────────

    @Test
    fun `empty ledger is day one - no nudge`() {
        assertEquals(
            RitualHealth.Nudge.NONE,
            RitualHealth.duskNudge("2026-11-05", emptySet()),
        )
    }

    @Test
    fun `tonight sealed means no nudge`() {
        assertEquals(
            RitualHealth.Nudge.NONE,
            RitualHealth.duskNudge("2026-11-05", setOf("2026-11-05", "2026-11-03")),
        )
    }

    @Test
    fun `one unsealed night is gentle`() {
        assertEquals(
            RitualHealth.Nudge.GENTLE,
            RitualHealth.duskNudge("2026-11-05", setOf("2026-11-04")),
        )
    }

    @Test
    fun `two unsealed nights is catch up`() {
        assertEquals(
            RitualHealth.Nudge.CATCH_UP,
            RitualHealth.duskNudge("2026-11-05", setOf("2026-11-03")),
        )
    }

    @Test
    fun `three plus unsealed nights is recommit`() {
        assertEquals(
            RitualHealth.Nudge.RECOMMIT,
            RitualHealth.duskNudge("2026-11-05", setOf("2026-11-01")),
        )
    }

    @Test
    fun `never-sealed history caps instead of looping forever`() {
        // One sealed date far in the past; the walk back must terminate.
        assertEquals(
            RitualHealth.Nudge.RECOMMIT,
            RitualHealth.duskNudge("2026-11-05", setOf("2026-01-01")),
        )
    }

    @Test
    fun `nudge copy never uses the word missed`() {
        RitualHealth.Nudge.entries.forEach { nudge ->
            val copy = RitualHealth.nudgeCopy(nudge)
            assertFalse(copy.contains("miss", ignoreCase = true))
        }
    }

    // ── The receipt ───────────────────────────────────────────────────────────

    @Test
    fun `receipt headline counts honestly`() {
        val receipt = RitualHealth.Receipt(planned = 3, landed = 2, carried = 1, letGo = 0)
        assertEquals("3 planned · 2 landed · 1 carried", receipt.headline())
    }

    @Test
    fun `receipt with let-go mentions it kindly`() {
        val receipt = RitualHealth.Receipt(planned = 2, landed = 0, carried = 1, letGo = 1)
        assertEquals("2 planned · 0 landed · 1 carried · 1 let go", receipt.headline())
    }

    @Test
    fun `rest day receipt is kind not scolding`() {
        val receipt = RitualHealth.Receipt(planned = 0, landed = 0, carried = 0, letGo = 0)
        assertEquals("Nothing was planned — a rest day is still a day.", receipt.headline())
    }

    // ── Tomorrow's auto-draft ─────────────────────────────────────────────────

    @Test
    fun `draft leads with carried then shrunk and caps at three`() {
        val draft = RitualHealth.draftTomorrow(
            carried = listOf("call amma", "revise ACS ch4"),
            shrunk = listOf("write intro", "extra line"),
        )
        assertEquals(listOf("call amma", "revise ACS ch4", "write intro"), draft)
    }

    @Test
    fun `draft drops blanks and duplicates`() {
        val draft = RitualHealth.draftTomorrow(
            carried = listOf("  call amma  ", "", "call amma"),
            shrunk = emptyList(),
        )
        assertEquals(listOf("call amma"), draft)
    }

    // ── Why-now lines ─────────────────────────────────────────────────────────

    @Test
    fun `deadline due today says so`() {
        val today = LocalDate.of(2026, 11, 5)
        val dueAt = today.atTime(18, 0).atZone(zone).toInstant().toEpochMilli()
        assertEquals("Due today", RitualHealth.whyNow("DEADLINE", dueAt, zone, today))
    }

    @Test
    fun `deadline due tomorrow says tomorrow`() {
        val today = LocalDate.of(2026, 11, 5)
        val dueAt = today.plusDays(1).atTime(9, 0).atZone(zone).toInstant().toEpochMilli()
        assertEquals("Due tomorrow", RitualHealth.whyNow("DEADLINE", dueAt, zone, today))
    }

    @Test
    fun `recurring kinds speak of rhythm not streaks`() {
        assertTrue(RitualHealth.whyNow("ANCHOR", 0L, zone, LocalDate.now()).contains("rhythm"))
        assertTrue(RitualHealth.whyNow("HABIT", 0L, zone, LocalDate.now()).contains("not streaks"))
        assertTrue(RitualHealth.whyNow("RELATIONAL", 0L, zone, LocalDate.now()).contains("waiting"))
        assertTrue(RitualHealth.whyNow("VALUE", 0L, zone, LocalDate.now()).contains("value"))
        assertEquals("Relevant right now", RitualHealth.whyNow(null, 0L, zone, LocalDate.now()))
    }

    // ── The waiting chain ─────────────────────────────────────────────────────

    @Test
    fun `item due later today is not waiting`() {
        val now = LocalDate.of(2026, 11, 5).atTime(10, 0).atZone(zone).toInstant().toEpochMilli()
        assertNull(RitualHealth.waitingLine(now + 60_000, now, zone))
    }

    @Test
    fun `item due yesterday waits since yesterday`() {
        val now = LocalDate.of(2026, 11, 5).atTime(10, 0).atZone(zone).toInstant().toEpochMilli()
        val due = LocalDate.of(2026, 11, 4).atTime(18, 0).atZone(zone).toInstant().toEpochMilli()
        assertEquals("waiting since yesterday", RitualHealth.waitingLine(due, now, zone))
    }

    @Test
    fun `item due three days ago counts the days`() {
        val now = LocalDate.of(2026, 11, 5).atTime(10, 0).atZone(zone).toInstant().toEpochMilli()
        val due = LocalDate.of(2026, 11, 2).atTime(18, 0).atZone(zone).toInstant().toEpochMilli()
        assertEquals("waiting 3 days", RitualHealth.waitingLine(due, now, zone))
    }

    // ── The interrupt budget ──────────────────────────────────────────────────

    @Test
    fun `under budget interrupts`() {
        assertEquals(
            RitualHealth.NotificationBudget.Decision.INTERRUPT,
            RitualHealth.NotificationBudget.decide(deliveredToday = 2, budget = 5),
        )
        assertEquals(
            RitualHealth.NotificationBudget.Decision.INTERRUPT,
            RitualHealth.NotificationBudget.decide(deliveredToday = 4, budget = 5),
        )
    }

    @Test
    fun `at or over budget goes to the digest`() {
        assertEquals(
            RitualHealth.NotificationBudget.Decision.DIGEST,
            RitualHealth.NotificationBudget.decide(deliveredToday = 5, budget = 5),
        )
        assertEquals(
            RitualHealth.NotificationBudget.Decision.DIGEST,
            RitualHealth.NotificationBudget.decide(deliveredToday = 9, budget = 5),
        )
    }

    @Test
    fun `budget is floored at one - silence is never a config accident`() {
        assertEquals(
            RitualHealth.NotificationBudget.Decision.INTERRUPT,
            RitualHealth.NotificationBudget.decide(deliveredToday = 0, budget = 0),
        )
        assertEquals(
            RitualHealth.NotificationBudget.Decision.DIGEST,
            RitualHealth.NotificationBudget.decide(deliveredToday = 1, budget = 0),
        )
    }

    // ── Mood words ────────────────────────────────────────────────────────────

    @Test
    fun `mood words are quiet and shameless`() {
        assertEquals("heavy", RitualHealth.moodLabel(1))
        assertEquals("steady", RitualHealth.moodLabel(3))
        assertEquals("bright", RitualHealth.moodLabel(5))
    }
}
