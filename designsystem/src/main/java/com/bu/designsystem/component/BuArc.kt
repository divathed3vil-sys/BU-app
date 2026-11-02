package com.bu.designsystem.component

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.bu.designsystem.theme.BuLayerHigh
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuMintDeep
import com.bu.designsystem.theme.BuOutline
import kotlin.math.cos
import kotlin.math.sin

/**
 * The Day Arc — BU's time field, 5:00 → 23:00 by default (design dogma).
 * Drawn once per frame on Canvas with cached geometry; the 60-tick ring is
 * never recomposed, only the dash offset moves (locked plan §10).
 *
 * P0 renders it live with demo state; P1 feeds it the real day.
 */
@Composable
fun BuDayArc(
    progress: Float,
    modifier: Modifier = Modifier,
    dayStartHour: Int = 5,
    dayEndHour: Int = 23,
    nowProgress: Float = progress,
    accent: Color = BuMint,
    deepAccent: Color = BuMintDeep,
) {
    val stroke = 14.dp
    Canvas(modifier = modifier) {
        val diameter = minOf(size.width, size.height) - stroke.toPx()
        val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)
        val arcSize = Size(diameter, diameter)

        // Track ring — the day's full span.
        drawArc(
            color = BuOutline,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
        )

        // Elapsed day — deep mint under-glow.
        val elapsedSweep = 360f * nowProgress.coerceIn(0f, 1f)
        if (elapsedSweep > 1f) {
            drawArc(
                color = deepAccent.copy(alpha = 0.35f),
                startAngle = -90f,
                sweepAngle = elapsedSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
            )
        }

        // Committed progress — mint on top.
        val committedSweep = 360f * progress.coerceIn(0f, 1f)
        if (committedSweep > 1f) {
            drawArc(
                color = accent,
                startAngle = -90f,
                sweepAngle = committedSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = stroke.toPx() * 0.55f, cap = StrokeCap.Round),
            )
        }

        // Hour ticks — subtle, drawn from cached math.
        val hours = dayEndHour - dayStartHour
        val radius = diameter / 2f
        val tickOuter = radius + stroke.toPx() * 1.4f
        val tickInner = radius + stroke.toPx() * 0.9f
        for (h in 0..hours) {
            val fraction = h.toFloat() / hours
            val angleDeg = -90f + fraction * 360f
            rotate(angleDeg, pivot = center) {
                drawLine(
                    color = BuLayerHigh,
                    start = Offset(center.x, center.y - tickOuter),
                    end = Offset(center.x, center.y - tickInner),
                    strokeWidth = 2f,
                )
            }
        }

        // Now marker — where the owner actually is.
        val nowAngle = (-90f + 360f * nowProgress.coerceIn(0f, 1f)) * (Math.PI.toFloat() / 180f)
        drawCircle(
            color = accent,
            radius = 7.dp.toPx() / 2f,
            center = Offset(
                center.x + tickInner * cos(nowAngle) * 0.96f,
                center.y + tickInner * sin(nowAngle) * 0.96f,
            ),
        )
    }
}
