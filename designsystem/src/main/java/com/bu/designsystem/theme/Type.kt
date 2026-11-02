package com.bu.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * BU typography — tight sans for the system voice, serif for the user's own
 * words (journal, reflections). Mono for times and counts.
 */
val BURedacted = FontFamily.SansSerif // system voice placeholder until brand font lands
val BUSerif = FontFamily.Serif
val BUMono = FontFamily.Monospace

val BUTypography = Typography(
    displayLarge = TextStyle( // the big "BU" moments
        fontFamily = BURedacted,
        fontWeight = FontWeight.Bold,
        fontSize = 44.sp,
        lineHeight = 48.sp,
        letterSpacing = (-1.5).sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.4).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 25.sp,
        letterSpacing = (-0.2).sp,
    ),
    titleMedium = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = BURedacted,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.3.sp,
    ),
    labelSmall = TextStyle( // the tiny uppercase "SECTION" voice
        fontFamily = BURedacted,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.5.sp,
        lineHeight = 14.sp,
        letterSpacing = 1.6.sp,
    ),
)

/** The serif voice — only for the user's own words. Never for system UI. */
val JournalSerif = TextStyle(
    fontFamily = BUSerif,
    fontWeight = FontWeight.Normal,
    fontSize = 16.5.sp,
    lineHeight = 27.sp,
)

/** Times, counts, clocks. */
val MonoNumerals = TextStyle(
    fontFamily = BUMono,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    letterSpacing = 0.5.sp,
)
