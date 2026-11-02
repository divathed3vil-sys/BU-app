package com.bu.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bu.designsystem.motion.rememberBuHaptics
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuMint
import kotlin.math.roundToInt

/**
 * The Pebble — BU's always-available capture affordance (locked plan §3).
 * Lively: draggable, magnetic edge-docking, position remembered across
 * sessions, squash on grab. Deliberately distinct from the Sparks capture
 * button: the Pebble is the global capture surface; Sparks captures
 * directly as a spark. A drag only ever docks — it never fires capture.
 */
private val PebbleOffsetSaver = listSaver<MutableState<Offset>, Float>(
    save = { listOf(it.value.x, it.value.y) },
    restore = { mutableStateOf(Offset(it[0], it[1])) },
)

@Composable
fun BuPebble(
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
    edgeMarginDp: Int = 20,
) {
    val haptics = rememberBuHaptics()

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var pebbleSize by remember { mutableStateOf(IntSize.Zero) }
    var grabbed by remember { mutableStateOf(false) }

    val savedOffset = rememberSaveable(stateSaver = PebbleOffsetSaver) { mutableStateOf(Offset.Zero) }
    val animatedOffset by animateOffsetAsState(
        targetValue = savedOffset.value,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = 0.55f,
            stiffness = 300f,
        ),
        label = "pebbleDock",
    )
    val scale by animateFloatAsState(
        targetValue = if (grabbed) 1.14f else 1f,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = 0.55f,
            stiffness = 300f,
        ),
        label = "pebbleSquash",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { containerSize = it },
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        animatedOffset.x.roundToInt(),
                        animatedOffset.y.roundToInt(),
                    )
                }
                .size(56.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    shadowElevation = if (grabbed) 24f else 12f
                }
                .background(BuBackground, CircleShape)
                .border(2.dp, BuMint, CircleShape)
                // Tap detector: cancels itself the moment a drag passes touch slop,
                // so a drag can never end in a capture.
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            grabbed = true
                            haptics.grab()
                        },
                        onTap = { onTap() },
                    )
                }
                // Drag detector: owns movement, magnetic-docks on release.
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            grabbed = true
                            haptics.grab()
                        },
                        onDragEnd = {
                            grabbed = false
                            savedOffset.value = docked(savedOffset.value, containerSize, pebbleSize, edgeMarginDp)
                        },
                        onDragCancel = { grabbed = false },
                    ) { change, dragAmount ->
                        change.consume()
                        val maxX = maxOffsetX(containerSize, pebbleSize, edgeMarginDp)
                        val maxY = maxOffsetY(containerSize, pebbleSize, edgeMarginDp)
                        val next = savedOffset.value + dragAmount
                        savedOffset.value = Offset(
                            next.x.coerceIn(edgeMarginDp.toFloat(), maxX),
                            next.y.coerceIn(edgeMarginDp.toFloat(), maxY),
                        )
                    }
                }
                .onSizeChanged { pebbleSize = it },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Capture",
                tint = BuMint,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}

private fun maxOffsetX(container: IntSize, pebble: IntSize, margin: Int): Float =
    (container.width - pebble.width - margin).coerceAtLeast(margin.toFloat())

private fun maxOffsetY(container: IntSize, pebble: IntSize, margin: Int): Float =
    (container.height - pebble.height - margin).coerceAtLeast(margin.toFloat())

private fun docked(current: Offset, container: IntSize, pebble: IntSize, margin: Int): Offset {
    val maxX = maxOffsetX(container, pebble, margin)
    val maxY = maxOffsetY(container, pebble, margin)
    val clamped = Offset(current.x.coerceIn(margin.toFloat(), maxX), current.y.coerceIn(margin.toFloat(), maxY))
    val dockLeft = clamped.x + pebble.width / 2f < container.width / 2f
    return Offset(if (dockLeft) margin.toFloat() else maxX, clamped.y)
}
