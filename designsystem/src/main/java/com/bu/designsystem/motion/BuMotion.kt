package com.bu.designsystem.motion

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

/**
 * Motion v3 — four named springs, nothing else (locked plan §4.2).
 * Every entry animation is transform/opacity only; layout never animates in.
 */
object BuMotion {

    // Crisp responses: tab switches, chips, tally swipes.
    val Snappy = spring<Float>(dampingRatio = 0.85f, stiffness = 380f)

    // The BU signature: pebble, seal ring, capture success.
    val Bouncy = spring<Float>(dampingRatio = 0.55f, stiffness = 300f)

    // Screen-scale moves: pager settle, sheet entrance.
    val Screen = spring<Float>(dampingRatio = 0.9f, stiffness = 220f)

    // Barely-there: dimming, colour shifts, ghost states.
    val Soft = spring<Float>(dampingRatio = 1f, stiffness = 180f)

    // Deterministic easings for one-shot transitions (springs are for interactive).
    val EmphasizedEnter: Easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
    val EmphasizedExit: Easing = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f)
    val Linear: Easing = LinearEasing

    // Motion v3 rules (locked plan §4.2) — enforced by review, stated here as law.
    const val RULE_TRANSFORM_ONLY = "Entry animations are transform/opacity only — never layout"
    const val RULE_NO_TAB_FLICKER = "Indicator leads, content settles — never two competing animations"
    const val RULE_PREDICTIVE_BACK = "Edge-back peeks before it commits"
    const val RULE_REDUCED_MOTION = "Reduced-motion replaces springs with instant state change"
}
