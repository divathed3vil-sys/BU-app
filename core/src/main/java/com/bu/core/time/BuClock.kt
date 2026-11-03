package com.bu.core.time

import android.os.SystemClock

/**
 * Injectable clock — determinism at the edges (locked plan §6.2).
 *
 * [nowMs] is wall time (user-visible instants). [elapsedRealtimeMs] is the
 * monotonic clock (focus measurement only): it never goes backwards until the
 * device reboots — a reboot rolls it under the recorded start, which BU reads
 * as INTERRUPTED_OR_UNKNOWN. Never invented.
 */
interface BuClock {
    fun nowMs(): Long
    fun elapsedRealtimeMs(): Long
}

class SystemBuClock : BuClock {
    override fun nowMs(): Long = System.currentTimeMillis()
    override fun elapsedRealtimeMs(): Long = SystemClock.elapsedRealtime()
}
