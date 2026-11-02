package com.bu.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * BU is a dark-only system by doctrine: near-black is the calm the day sits on.
 * (Light theme may arrive much later, as its own designed thing — never an afterthought.)
 */
private val BUColors = darkColorScheme(
    primary = Mint,
    onPrimary = MintInk,
    primaryContainer = MintDeep,
    onPrimaryContainer = Mint,
    secondary = MintMid,
    onSecondary = MintInk,
    tertiary = Gold,
    onTertiary = Color(0xFF241D0B),
    background = Bg0,
    onBackground = InkHi,
    surface = Bg1,
    onSurface = InkHi,
    surfaceVariant = Layer,
    onSurfaceVariant = InkMid,
    surfaceContainer = Layer,
    surfaceContainerHigh = Layer2,
    surfaceContainerHighest = Layer2,
    outline = Line,
    outlineVariant = Line2,
    error = Graphite, // doctrine: errors cool down, they never scream
    onError = InkHi,
)

/** Extended BU palette (things Material has no slot for). */
data class BUExtendedColors(
    val inkLow: Color = InkLow,
    val inkFaint: Color = InkFaint,
    val mintGlow: Color = MintGlow,
    val graphite: Color = Graphite,
    val graphiteLine: Color = GraphiteLine,
    val gold: Color = Gold,
    val stage: Color = Stage,
)

val LocalBUExtended = staticCompositionLocalOf { BUExtendedColors() }

object BU {
    val extended: BUExtendedColors
        @Composable get() = LocalBUExtended.current
}

@Composable
fun BUTheme(content: @Composable () -> Unit) {
    // isSystemInDarkTheme intentionally unused: dark is the doctrine.
    @Suppress("UNUSED_EXPRESSION") isSystemInDarkTheme()
    MaterialTheme(
        colorScheme = BUColors,
        typography = BUTypography,
        shapes = BUShapes,
        content = content,
    )
}

/** Radius doctrine: cards 16, sheets 28, pebble/controls = full. */
val BUShapes = androidx.compose.material3.Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)
