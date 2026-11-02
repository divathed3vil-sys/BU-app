package com.bu.designsystem.motion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * BU haptic grammar (design dogma) — few, deliberate, always meaningful.
 */
class BuHaptics(private val feedback: HapticFeedback) {

    /** Snap points: Arc-Dial 15-minute steps, tally locks, picker stops. */
    fun tick() = feedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)

    /** Grab: pebble picked up, drag started. */
    fun grab() = feedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)

    /** Win: tally done, seal complete, contract signed. */
    fun win() = feedback.performHapticFeedback(HapticFeedbackType.LongPress)

    /** Guard: an action is refused or a limit is reached. */
    fun guard() = feedback.performHapticFeedback(HapticFeedbackType.LongPress)
}

@Composable
fun rememberBuHaptics(): BuHaptics {
    val feedback = LocalHapticFeedback.current
    return remember { BuHaptics(feedback) }
}
