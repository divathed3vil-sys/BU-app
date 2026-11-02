package com.bu.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bu.designsystem.motion.rememberBuHaptics
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuOutline
import com.bu.designsystem.theme.BuText
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * The Arc-Dial — BU's ONLY time picker (locked plan §4.1).
 * Native sliders are deleted from the product; the dial drags along an arc,
 * snaps in 15-minute steps with a haptic tick, and rides a value bubble.
 * ±5-minute steppers double as the accessible path.
 */
@Composable
fun BuArcDial(
    valueMinutes: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    rangeStartMinute: Int = 5 * 60,
    rangeEndMinute: Int = 23 * 60,
    snapStep: Int = 15,
) {
    val haptics = rememberBuHaptics()
    var lastSnapBucket by remember { mutableIntStateOf(valueMinutes / snapStep) }
    var dialSize by remember { mutableStateOf(IntSize.Zero) }

    val span = (rangeEndMinute - rangeStartMinute).coerceAtLeast(1)
    val startAngle = 135f
    val sweep = 270f

    fun angleFor(value: Int): Float {
        val fraction = (value - rangeStartMinute).toFloat() / span
        return startAngle + fraction * sweep
    }

    fun valueFor(angle: Float): Int {
        val normalized = if (angle < startAngle) angle + 360f else angle
        val clamped = normalized.coerceIn(startAngle, startAngle + sweep)
        val fraction = (clamped - startAngle) / sweep
        return rangeStartMinute + (fraction * span).roundToInt()
    }

    Box(
        modifier = modifier.onSizeChanged { dialSize = it },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(rangeStartMinute, rangeEndMinute, snapStep) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val dx = change.position.x - center.x
                        val dy = change.position.y - center.y
                        var angle = Math.toDegrees(atan2(dy, dx).toDouble()).toFloat()
                        if (angle < 0f) angle += 360f
                        val raw = valueFor(angle)
                        val snapped = (raw.toFloat() / snapStep).roundToInt() * snapStep
                        val clamped = snapped.coerceIn(rangeStartMinute, rangeEndMinute)
                        if (clamped / snapStep != lastSnapBucket) {
                            lastSnapBucket = clamped / snapStep
                            haptics.tick()
                        }
                        onValueChange(clamped)
                    }
                },
        ) {
            val strokePx = 12.dp.toPx()
            val diameter = minOf(size.width, size.height) - strokePx * 2f
            val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
            val arcSize = Size(diameter, diameter)

            drawArc(
                color = BuOutline,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round),
            )
            drawArc(
                color = BuMint,
                startAngle = startAngle,
                sweepAngle = (valueMinutes - rangeStartMinute).toFloat() / span * sweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round),
            )

            // Thumb — rides the arc at the current value.
            val thumbAngleRad = Math.toRadians(angleFor(valueMinutes).toDouble())
            val radius = diameter / 2f
            drawCircle(
                color = BuText,
                radius = 9.dp.toPx() / 2f,
                center = Offset(
                    center.x + radius * cos(thumbAngleRad).toFloat(),
                    center.y + radius * sin(thumbAngleRad).toFloat(),
                ),
            )
        }

        // Value bubble rides near the thumb angle.
        val bubbleAngle = Math.toRadians(angleFor(valueMinutes).toDouble())
        val bubbleRadius = (minOf(dialSize.width, dialSize.height).coerceAtLeast(1)) / 2f * 0.58f
        Text(
            text = formatMinutes(valueMinutes),
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            color = BuText,
            modifier = Modifier.offset {
                IntOffset(
                    (bubbleRadius * cos(bubbleAngle)).roundToInt(),
                    (bubbleRadius * sin(bubbleAngle)).roundToInt(),
                )
            },
        )
    }

    // Accessible + fine-trim path.
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
    ) {
        TextButton(onClick = {
            val next = (valueMinutes - 5).coerceIn(rangeStartMinute, rangeEndMinute)
            if (next != valueMinutes) {
                haptics.tick()
                onValueChange(next)
            }
        }) { Text("−5m") }
        TextButton(onClick = {
            val next = (valueMinutes + 5).coerceIn(rangeStartMinute, rangeEndMinute)
            if (next != valueMinutes) {
                haptics.tick()
                onValueChange(next)
            }
        }) { Text("+5m") }
    }
}

fun formatMinutes(totalMinutes: Int): String {
    val h = totalMinutes / 60
    val m = totalMinutes % 60
    return "%02d:%02d".format(h, m)
}
