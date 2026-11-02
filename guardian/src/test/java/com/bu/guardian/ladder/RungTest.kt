package com.bu.guardian.ladder

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RungTest {

    @Test
    fun `escalation never passes the sealed ceiling (I11)`() {
        val ceiling = Ceiling(maxLevel = 2)
        var rung = Rung.OBSERVE
        repeat(5) {
            rung = LadderPolicy.escalate(rung, ceiling)
        }
        assertEquals(Rung.PAUSE, rung) // stuck at L2 — ceiling holds
        assertFalse(ceiling.allows(Rung.SOFT_LIMIT))
        assertTrue(ceiling.allows(Rung.PAUSE))
    }

    @Test
    fun `de-escalation requires three compliant days`() {
        assertEquals(Rung.NUDGE, LadderPolicy.deEscalate(Rung.PAUSE, compliantDays = 3))
        assertEquals(Rung.PAUSE, LadderPolicy.deEscalate(Rung.PAUSE, compliantDays = 2))
    }

    @Test
    fun `rung levels are contiguous`() {
        Rung.entries.forEachIndexed { index, rung -> assertEquals(index, rung.level) }
    }
}
