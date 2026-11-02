package com.bu.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bu.designsystem.motion.BUMotion
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintInk

/**
 * The doctrine press: every control answers with a spring, never a dead click.
 */
@Composable
fun MintButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    height: Int = 52,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.965f else 1f,
        animationSpec = BUMotion.Snappy,
        label = "mintPress",
    )
    Box(
        modifier = modifier
            .height(height.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(if (enabled) Mint else Layer)
            .clickable(interactionSource = interaction, indication = null, enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = if (enabled) MintInk else InkMid,
        )
    }
}

@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.965f else 1f,
        animationSpec = BUMotion.Snappy,
        label = "ghostPress",
    )
    Box(
        modifier = modifier
            .clip(CircleShape)
            .scale(scale)
            .background(Layer)
            .border(1.dp, Line, CircleShape)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = MaterialTheme.typography.labelLarge, color = InkMid)
    }
}

/** Tiny uppercase section voice: "BIG THREE", "THE ARC". */
@Composable
fun SectionLabel(text: String, modifier: Modifier = Modifier, color: Color = InkMid) {
    Text(text = text, style = MaterialTheme.typography.labelSmall, color = color, modifier = modifier)
}

/** BU card: layer surface, hairline, 16dp radius. */
@Composable
fun BUCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Layer)
            .border(1.dp, Line, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) { content() }
}
