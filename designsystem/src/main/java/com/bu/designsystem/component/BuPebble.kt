package com.bu.designsystem.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.motion.BUMotion
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintGlow
import com.bu.designsystem.theme.MintInk
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

/**
 * THE PEBBLE — the living `+`. Locked doctrine from the mockup review:
 *
 *  · it floats over the loop screens, always within reach
 *  · drags are 1:1 with the finger; release docks to the nearest edge
 *    with the big Dock bounce (BUMotion.Dock)
 *  · it breathes while idle — the only thing on screen allowed to move alone
 *  · a tap (not a drag) expands the quick menu: quick spark / new block /
 *    big three. It opens doors; it never duplicates what's behind them
 *    (the sparks screen owns full capture, the editor owns planning)
 *  · haptic grammar: tick on grab, tap on dock, tap on menu items
 *  · its resting place survives restarts
 */
private val OrbSize = 58.dp
private val MenuWidth = 176.dp
private val MenuBlockHeight = 156.dp
private const val PEBBLE_DESCRIPTION = "the pebble — quick actions"

@Composable
fun BuPebble(
    onQuickSpark: () -> Unit,
    onNewBlock: () -> Unit,
    onNewBig: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val view = LocalView.current
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("bu_pebble", Context.MODE_PRIVATE) }
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val orbPx = with(density) { OrbSize.toPx() }
    val menuPx = with(density) { MenuWidth.toPx() }
    val menuBlockPx = with(density) { MenuBlockHeight.toPx() }
    val gapPx = with(density) { 12.dp.toPx() }

    var container by remember { mutableStateOf(IntSize.Zero) }
    var placed by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var dragging by remember { mutableStateOf(false) }
    val x = remember { Animatable(0f) }
    val y = remember { Animatable(0f) }

    // the breathing pulse — subtle, alive, patient
    val breath by rememberInfiniteTransition(label = "pebbleBreath").animateFloat(
        initialValue = 1f,
        targetValue = 1.045f,
        animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Reverse),
        label = "pebbleBreathScale",
    )

    LaunchedEffect(container) {
        if (container == IntSize.Zero) return@LaunchedEffect
        val maxX = (container.width - orbPx).coerceAtLeast(0f)
        val maxY = (container.height - orbPx).coerceAtLeast(0f)
        if (!placed) {
            x.snapTo(prefs.getFloat("pebble_fx", 1f) * maxX)
            y.snapTo(prefs.getFloat("pebble_fy", 0.72f) * maxY)
            placed = true
        } else {
            x.snapTo(x.value.coerceIn(0f, maxX))
            y.snapTo(y.value.coerceIn(0f, maxY))
        }
    }

    fun dock() {
        val maxX = (container.width - orbPx).coerceAtLeast(0f)
        val maxY = (container.height - orbPx).coerceAtLeast(0f)
        val targetX = if (x.value + orbPx / 2f < container.width / 2f) 0f else maxX
        val targetY = y.value.coerceIn(0f, maxY)
        scope.launch {
            launch { x.animateTo(targetX, BUMotion.Dock) }
            launch { y.animateTo(targetY, BUMotion.Dock) }
            BUHaptic.tick(view)
            prefs.edit()
                .putFloat("pebble_fx", if (maxX > 0f) targetX / maxX else 1f)
                .putFloat("pebble_fy", if (maxY > 0f) targetY / maxY else 0f)
                .apply()
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .onSizeChanged { container = it },
    ) {
        if (expanded) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Bg0.copy(alpha = 0.72f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { expanded = false },
            )
        }

        if (placed && container != IntSize.Zero) {
            // quick menu — pops out of the pebble, staggered, springy
            val above = y.value > menuBlockPx + gapPx
            val menuX = x.value.coerceIn(0f, (container.width - menuPx).coerceAtLeast(0f))
            val menuY = if (above) y.value - menuBlockPx - gapPx else y.value + orbPx + gapPx
            Column(
                Modifier
                    .width(MenuWidth)
                    .offset { IntOffset(menuX.roundToInt(), menuY.roundToInt().coerceAtLeast(0)) },
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                listOf(
                    "quick spark" to onQuickSpark,
                    "new block" to onNewBlock,
                    "big three" to onNewBig,
                ).forEachIndexed { index, (label, action) ->
                    AnimatedVisibility(
                        visible = expanded,
                        enter = scaleIn(
                            animationSpec = tween(280, delayMillis = index * 50, easing = BUMotion.PopOut),
                            initialScale = 0.55f,
                        ) + fadeIn(tween(220, delayMillis = index * 50)),
                        exit = scaleOut(tween(130)) + fadeOut(tween(110)),
                    ) {
                        PebbleMenuItem(label) {
                            BUHaptic.tap(view)
                            expanded = false
                            action()
                        }
                    }
                }
            }

            // the orb itself
            Box(
                Modifier
                    .offset { IntOffset(x.value.roundToInt(), y.value.roundToInt()) }
                    .size(OrbSize)
                    .scale(if (dragging) 1.1f else breath)
                    .semantics { contentDescription = PEBBLE_DESCRIPTION }
                    .clip(CircleShape)
                    .background(Mint)
                    .border(5.dp, MintGlow, CircleShape)
                    .pointerInput(Unit) {
                        var travel = 0f
                        detectDragGestures(
                            onDragStart = {
                                travel = 0f
                                dragging = true
                                expanded = false
                                BUHaptic.tick(view)
                            },
                            onDragEnd = {
                                dragging = false
                                if (travel < 12f) {
                                    expanded = !expanded
                                    BUHaptic.tap(view)
                                } else {
                                    dock()
                                }
                            },
                            onDragCancel = { dragging = false },
                        ) { change, amount ->
                            change.consume()
                            travel += abs(amount.x) + abs(amount.y)
                            val maxX = (container.width - orbPx).coerceAtLeast(0f)
                            val maxY = (container.height - orbPx).coerceAtLeast(0f)
                            scope.launch {
                                x.snapTo((x.value + amount.x).coerceIn(0f, maxX))
                                y.snapTo((y.value + amount.y).coerceIn(0f, maxY))
                            }
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text("+", style = MaterialTheme.typography.headlineMedium, color = MintInk)
            }
        }
    }
}

@Composable
private fun PebbleMenuItem(text: String, onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Layer)
            .border(1.dp, Line, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 18.dp, vertical = 12.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = InkHi)
    }
}
