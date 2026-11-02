package com.bu.core.plan

import com.bu.core.model.PlanItem
import com.bu.core.model.PlanKind
import com.bu.core.model.PlanState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CarryLogicTest {

    private fun big(day: Long = 1, state: PlanState = PlanState.PENDING) =
        PlanItem(date = day, kind = PlanKind.BIG, title = "big", state = state)

    @Test
    fun `big three accepts up to three`() {
        val day = 1L
        assertTrue(CarryLogic.canAddBig(listOf(big(day), big(day)), day))
        assertFalse(CarryLogic.canAddBig(listOf(big(day), big(day), big(day)), day))
    }

    @Test
    fun `dropped bigs do not count against the limit`() {
        val day = 1L
        val items = listOf(big(day), big(day), big(day, PlanState.DROPPED))
        assertTrue(CarryLogic.canAddBig(items, day))
    }

    @Test
    fun `other days do not count against the limit`() {
        val day = 1L
        val items = listOf(big(day), big(day), big(2))
        assertTrue(CarryLogic.canAddBig(items, day))
    }

    @Test
    fun `carry cools the original and opens a fresh linked copy`() {
        val now = 1_000L
        val item = PlanItem(
            id = 7,
            date = 1,
            kind = PlanKind.BLOCK,
            title = "physics ch 4",
            trackId = 3,
            startMin = 360,
            durMin = 60,
            locked = true,
        )

        val (cooled, fresh) = CarryLogic.carryForward(item, targetDay = 2, now = now)

        // the original cools — never deleted, never red
        assertEquals(7L, cooled.id)
        assertEquals(PlanState.CARRIED, cooled.state)

        // the copy opens fresh on the target day, linked back
        assertEquals(0L, fresh.id)
        assertEquals(2L, fresh.date)
        assertEquals(PlanState.PENDING, fresh.state)
        assertEquals(7L, fresh.carriedFromId)
        assertEquals("physics ch 4", fresh.title)
        assertEquals(3L, fresh.trackId)
        assertEquals(360, fresh.startMin)
        assertEquals(60, fresh.durMin)
        assertEquals(false, fresh.locked)
    }

    @Test
    fun `blocks must fit inside the waking day`() {
        assertTrue(CarryLogic.fitsInDay(startMin = 5 * 60, durMin = 60, dayStartMin = 5 * 60, dayEndMin = 23 * 60))
        assertFalse(CarryLogic.fitsInDay(startMin = 4 * 60, durMin = 60, dayStartMin = 5 * 60, dayEndMin = 23 * 60))
        assertFalse(CarryLogic.fitsInDay(startMin = 22 * 60 + 30, durMin = 60, dayStartMin = 5 * 60, dayEndMin = 23 * 60))
        assertTrue(CarryLogic.fitsInDay(startMin = 22 * 60, durMin = 60, dayStartMin = 5 * 60, dayEndMin = 23 * 60))
    }
}
