package com.bu.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BuColors = darkColorScheme(
    primary = BuMint,
    onPrimary = BuBackground,
    primaryContainer = BuMintDim,
    onPrimaryContainer = BuMint,
    secondary = BuTextDim,
    onSecondary = BuBackground,
    background = BuBackground,
    onBackground = BuText,
    surface = BuLayer,
    onSurface = BuText,
    surfaceVariant = BuLayerHigh,
    onSurfaceVariant = BuTextDim,
    outline = BuOutline,
    outlineVariant = BuOutline,
    error = BuDanger,
    onError = Color.White,
    scrim = Color(0xCC000000),
)

/** BU is a dark-native system; light OS settings get the same calm surface. */
@Composable
fun BuTheme(content: @Composable () -> Unit) {
    isSystemInDarkTheme() // respected but BU stays dark — dusk-first by design
    MaterialTheme(
        colorScheme = BuColors,
        typography = BuTypography,
        content = content,
    )
}
