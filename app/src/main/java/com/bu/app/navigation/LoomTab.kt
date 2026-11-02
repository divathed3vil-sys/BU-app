package com.bu.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The LOOM five tabs (locked plan §3): NOW · PLAN · JOURNAL · ATLAS · SHIELD.
 * YOU opens from the identity chip; rituals are full-screen flows (P2+).
 */
enum class LoomTab(
    val label: String,
    val arrivesInPhase: String,
    val icon: ImageVector,
) {
    NOW("NOW", "P1", Icons.Outlined.Today),
    PLAN("PLAN", "P1", Icons.Outlined.Schedule),
    JOURNAL("JOURNAL", "P3", Icons.Outlined.AutoStories),
    ATLAS("ATLAS", "P3", Icons.Outlined.Hub),
    SHIELD("SHIELD", "P4", Icons.Outlined.Shield),
}
