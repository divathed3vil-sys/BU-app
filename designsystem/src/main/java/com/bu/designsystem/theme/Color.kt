package com.bu.designsystem.theme

import androidx.compose.ui.graphics.Color

/**
 * BU color doctrine — near-black base, slight gray layering, slight mint accent.
 * No red, ever. Missed = graphite (cooling), exam = gold (used sparingly).
 * Ported 1:1 from the approved v2 mockup (theme.ts).
 */
val Stage = Color(0xFF030504) // room behind the app (never seen in-app)
val Bg0 = Color(0xFF060807) // app background
val Bg1 = Color(0xFF0B0E0C) // raised surface
val Layer = Color(0xFF101410) // cards
val Layer2 = Color(0xFF151A16) // raised cards
val Line = Color(0xFF1E241F) // hairlines
val Line2 = Color(0xFF2A322B)
val InkHi = Color(0xFFEDF1EE) // primary text
val InkMid = Color(0xFFA8B1AA) // secondary text
val InkLow = Color(0xFF6B756E) // tertiary text
val InkFaint = Color(0xFF414B44) // whisper text / disabled

val Mint = Color(0xFF7CF0C0) // the accent — bright, alive
val MintMid = Color(0xFF43DE9C)
val MintDeep = Color(0xFF1E7A52) // filled controls
val MintGlow = Color(0x297CF0C0) // 16% alpha halo
val MintInk = Color(0xFF0A1F16) // text on mint

val Graphite = Color(0xFF3A423C) // cooling / missed — never red
val GraphiteLine = Color(0xFF4A544D)

val Gold = Color(0xFFE4C87F) // exam countdown only
val GoldGlow = Color(0x24E4C87F)

val White = Color(0xFFF4F7F4)

/** Mint-family palette for user-created tracks — subtle, no rainbow. */
val TrackColors: List<Color> = listOf(
    Color(0xFF7CF0C0),
    Color(0xFF9DD9A8),
    Color(0xFF6FD3C3),
    Color(0xFFC9D98A),
    Color(0xFF8FB5A3),
    Color(0xFFD9C79A),
)
