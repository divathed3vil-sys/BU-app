package com.bu.identity.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BigThreeRulesTest {

    @Test
    fun `at most three slots`() {
        val four = (1..4).map { BigThreeSlot("task $it", isMainFront = it == 1) }
        assertFalse(BigThreeRules.isValid(four))
        assertTrue(BigThreeRules.validate(four).contains("MAX_THREE"))
    }

    @Test
    fun `exactly one main front`() {
        val none = listOf(BigThreeSlot("a"), BigThreeSlot("b"))
        val two = listOf(BigThreeSlot("a", isMainFront = true), BigThreeSlot("b", isMainFront = true))
        assertFalse(BigThreeRules.isValid(none))
        assertFalse(BigThreeRules.isValid(two))
        assertTrue(BigThreeRules.validate(two).contains("EXACTLY_ONE_MAIN_FRONT"))
    }

    @Test
    fun `valid day passes`() {
        val good = listOf(
            BigThreeSlot("Physics revision", isMainFront = true, timeSlot = "16:00"),
            BigThreeSlot("ACS essay draft"),
        )
        assertTrue(BigThreeRules.isValid(good))
    }

    @Test
    fun `empty day is valid - empty states instruct, never scold`() {
        assertTrue(BigThreeRules.isValid(emptyList()))
        assertEquals(0, BigThreeRules.validate(emptyList()).size)
    }
}
