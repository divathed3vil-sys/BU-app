package com.bu.designsystem.motion

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

/**
 * BU motion doctrine — "everything answers, nothing lags."
 * 12 rules live in the design doc; the springs here are the law:
 *  - every press answers in <100ms (spring, never duration-based shrink)
 *  - entries overshoot slightly (pop), never fade limply
 *  - drags are 1:1 with the finger, releases spring with a bounce
 *  - haptics are a grammar, not decoration (see BUHaptic)
 */
object BUMotion {

    // ---- springs (stiffness/damping ported from the approved mockup) ----
    val Snappy = spring<Float>(dampingRatio = 0.86f, stiffness = 420f, visibilityThreshold = Spring.VisibilityThreshold.Float)
    val Bouncy = spring<Float>(dampingRatio = 0.58f, stiffness = 340f)
    val Bouncier = spring<Float>(dampingRatio = 0.52f, stiffness = 300f)
    val Screen = spring<Float>(dampingRatio = 0.80f, stiffness = 300f)
    val Soft = spring<Float>(dampingRatio = 0.78f, stiffness = 200f)

    /** Dock-the-pebble spring: big bounce off the edge. */
    val Dock = spring<Float>(dampingRatio = 0.55f, stiffness = 380f)

    // ---- easings for CSS-like keyframes (entries/exits) ----
    val PopOut: Easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f) // overshoot
    val EnterSoft: Easing = CubicBezierEasing(0.22f, 1.20f, 0.36f, 1f)
    val ExitQuick: Easing = CubicBezierEasing(0.4f, 0f, 0.6f, 1f)

    // ---- durations (entries only; presses are springs) ----
    const val ENTER_MS = 420
    const val ENTER_POP_MS = 500
    const val EXIT_MS = 220
    const val CARRY_MS = 700 // the cooling carry-over slide
}
