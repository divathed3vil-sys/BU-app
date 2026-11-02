package com.bu.guardian

/**
 * The Guardian plane — detector, restrictor, defender, observer.
 *
 * P0 plants the boundary: this module may READ guardian settings and
 * WRITE evidence. It never touches the identity plane's words.
 * Real services land in P4 (accessibility, tracker, overlay, emergency)
 * and P5 (notification batching, shorts budget, host blocking, widgets).
 */
object GuardianCharter {
    const val NOTES: String = "planted in P0. wakes in P4."
}
