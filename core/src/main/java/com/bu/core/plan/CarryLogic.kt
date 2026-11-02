package com.bu.core.plan

import com.bu.core.model.PlanItem
import com.bu.core.model.PlanKind
import com.bu.core.model.PlanState

/**
 * The loop's soft rules — pure functions, zero Android, unit-tested.
 *
 *  Big Three — at most three things own a day. Dropped ones don't count:
 *  dropping is a decision, not a debt.
 *
 *  Carry — the cooling carry-over. The original cools to CARRIED (graphite,
 *  never red) and a fresh copy opens on the target day, linked back to its
 *  origin. Nothing is deleted, nothing is shamed.
 *
 *  Fit — a time block must live inside the user's waking day.
 */
object CarryLogic {

    const val BIG_LIMIT = 3

    fun canAddBig(existing: List<PlanItem>, day: Long): Boolean =
        existing.count {
            it.date == day && it.kind == PlanKind.BIG && it.state != PlanState.DROPPED
        } < BIG_LIMIT

    fun carryForward(item: PlanItem, targetDay: Long, now: Long): Pair<PlanItem, PlanItem> {
        val cooled = item.copy(state = PlanState.CARRIED, updatedAt = now)
        val fresh = item.copy(
            id = 0,
            date = targetDay,
            state = PlanState.PENDING,
            carriedFromId = item.id,
            locked = false,
            createdAt = now,
            updatedAt = now,
        )
        return cooled to fresh
    }

    fun fitsInDay(startMin: Int, durMin: Int, dayStartMin: Int, dayEndMin: Int): Boolean =
        startMin >= dayStartMin && startMin + durMin <= dayEndMin
}
