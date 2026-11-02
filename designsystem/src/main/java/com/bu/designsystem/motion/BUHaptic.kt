package com.bu.designsystem.motion

import android.view.HapticFeedbackConstants
import android.view.View

/**
 * BU haptic grammar — a language, not decoration.
 *  tap   = 8ms  — every press confirms it was heard
 *  tick  = 12ms — selection, step, dock
 *  lock  = double — commitment (Big Three lock, plan saved)
 *  win   = pattern — a day closed, a milestone (used rarely, earned)
 *  warn  = heavy — guardian interception (rare, meaningful)
 */
object BUHaptic {
    fun tap(view: View) = view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    fun tick(view: View) = view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
    fun lock(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        view.postDelayed({ view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }, 40)
    }
    fun win(view: View) {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        view.postDelayed({ view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP) }, 30)
        view.postDelayed({ view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY) }, 60)
    }
    fun warn(view: View) = view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
}
