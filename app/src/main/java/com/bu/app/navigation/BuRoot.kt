package com.bu.app.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bu.app.atlas.AtlasTab
import com.bu.app.capture.CaptureSheet
import com.bu.app.journal.JournalTab
import com.bu.app.now.NowTab
import com.bu.app.plan.PlanTab
import com.bu.app.shield.ShieldTab
import com.bu.designsystem.component.BuPebble
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuLayer
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuOutline
import com.bu.designsystem.theme.BuTextDim
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * BU root — the LOOM shell (locked plan §3):
 * 5 swipeable tabs with a leading indicator, the Pebble overlay,
 * the universal capture sheet, and honest per-phase empty states.
 *
 * The nav is a FLOATING pill (owner decision, P1): detached from the screen
 * edges, lifted by a soft shadow, content glides beneath it. Every tab's
 * scroll column carries its own bottom clearance; the overlays (Pebble,
 * snackbar) sit above the pill, never behind it.
 */
@Composable
fun BuRoot() {
    var captureOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { LoomTab.entries.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BuBackground),
    ) {
        Column(Modifier.fillMaxSize()) {
            LoomTopBar()
            LoomPager(
                pagerState = pagerState,
                modifier = Modifier.weight(1f),
            )
        }

        // The floating LOOM nav — detached, lifted, alive.
        LoomBottomBar(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(start = 24.dp, end = 24.dp, bottom = 14.dp),
        )

        // The Pebble — global capture, magnetic, position remembered.
        BuPebble(
            onTap = { captureOpen = true },
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(bottom = 108.dp),
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 104.dp),
        )
    }

    if (captureOpen) {
        CaptureSheet(
            onDismiss = { captureOpen = false },
            onSaved = { receiptText ->
                captureOpen = false
                scope.launch { snackbarHostState.showSnackbar(receiptText) }
            },
        )
    }
}

@Composable
private fun LoomTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                "BU",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                "Personal Life OS · LOOM",
                style = MaterialTheme.typography.labelSmall,
                color = BuTextDim,
            )
        }
        // Identity chip → YOU (lands P7). Present, honest, inert for now.
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = BuLayer,
            border = BorderStroke(1.dp, BuOutline),
        ) {
            Text(
                "YOU",
                style = MaterialTheme.typography.labelLarge,
                color = BuTextDim,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun LoomPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        beyondViewportPageCount = 1,
    ) { page ->
        when (LoomTab.entries[page]) {
            LoomTab.NOW -> NowTab()
            LoomTab.PLAN -> PlanTab()
            LoomTab.JOURNAL -> JournalTab()
            LoomTab.ATLAS -> AtlasTab()
            LoomTab.SHIELD -> ShieldTab()
        }
    }
}

@Composable
private fun LoomBottomBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val currentPage = pagerState.currentPage
    val pageOffset = pagerState.currentPageOffsetFraction
    val indicatorIndex = currentPage + pageOffset

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = BuLayer,
        shape = CircleShape,
        border = BorderStroke(1.dp, BuOutline),
        shadowElevation = 18.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
        ) {
            // Leading indicator — slides under the active tab (motion v3: no flicker).
            val tabWidthPx = with(density) { 72.dp.toPx() }
            val indicatorX by animateFloatAsState(
                targetValue = indicatorIndex * tabWidthPx + tabWidthPx / 2f - 28f,
                animationSpec = spring(dampingRatio = 0.85f, stiffness = 380f),
                label = "tabIndicator",
            )
            Box(
                modifier = Modifier
                    .offset { IntOffset(indicatorX.roundToInt(), 0) }
                    .align(Alignment.TopStart)
                    .padding(top = 4.dp)
                    .width(56.dp)
                    .height(4.dp)
                    .background(BuMint, RoundedCornerShape(2.dp)),
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LoomTab.entries.forEach { tab ->
                    val selected = tab.ordinal == currentPage
                    val tint by animateColorAsState(
                        targetValue = if (selected) BuMint else BuTextDim,
                        animationSpec = spring(dampingRatio = 0.85f, stiffness = 380f),
                        label = "tabTint",
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                scope.launch {
                                    pagerState.animateScrollToPage(tab.ordinal)
                                }
                            },
                    ) {
                        Icon(tab.icon, contentDescription = tab.label, tint = tint)
                        Text(
                            tab.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = tint,
                            modifier = Modifier.alpha(if (selected) 1f else 0.7f),
                        )
                    }
                }
            }
        }
    }
}
