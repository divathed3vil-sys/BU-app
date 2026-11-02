package com.bu.core.time

/** Injectable clock — determinism at the edges (locked plan §6.2). */
interface BuClock {
    fun nowMs(): Long
}

class SystemBuClock : BuClock {
    override fun nowMs(): Long = System.currentTimeMillis()
}
