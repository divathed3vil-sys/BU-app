package com.bu.identity.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bu.core.model.PlanItem
import com.bu.core.model.PlanKind
import com.bu.core.model.PlanState
import com.bu.core.model.Track
import com.bu.core.time.BUTime
import com.bu.designsystem.component.BUCard
import com.bu.designsystem.component.GhostButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.Graphite
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Layer2
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Line2
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintInk
import com.bu.designsystem.theme.MintMid
import com.bu.designsystem.theme.MonoNumerals
import com.bu.designsystem.theme.TrackColors
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * The Day Arc — today's loop at a glance.
 * Big Three first (never more than three), time slots, the loose tail,
 * and the spark inbox teaser. Tap a card to edit; states answer in place.
 */
@Composable
fun DayArcScreen(
    onOpenSparks: () -> Unit,
    onOpenTracks: () -> Unit,
    onOpenSettings: () -> Unit,
    vm: DayViewModel = hiltViewModel(),
) {
    val view = LocalView.current
    val day by vm.day.collectAsStateWithLifecycle()
    val items by vm.items.collectAsStateWithLifecycle()
    val tracks by vm.tracks.collectAsStateWithLifecycle()
    val newSparks by vm.newSparks.collectAsStateWithLifecycle()

    val today = BUTime.todayEpochDay()
    val bigs = items.filter { it.kind == PlanKind.BIG }
    val slots = items.filter { it.kind == PlanKind.BLOCK && it.isTimed }
    val loose = items.filter { it.kind == PlanKind.SPARK || (it.kind == PlanKind.BLOCK && !it.isTimed) }
    val trackById = remember(tracks) { tracks.associateBy { it.id } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg0)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(14.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            SectionLabel("the day arc")
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { BUHaptic.tap(view); onOpenSparks() }) {
                Text(
                    if (newSparks > 0) "sparks · $newSparks" else "sparks",
                    style = MaterialTheme.typography.labelLarge,
                    color = InkLow,
                )
            }
            TextButton(onClick = { BUHaptic.tap(view); onOpenTracks() }) {
                Text("tracks", style = MaterialTheme.typography.labelLarge, color = InkLow)
            }
            TextButton(onClick = { BUHaptic.tap(view); onOpenSettings() }) {
                Text("you", style = MaterialTheme.typography.labelLarge, color = InkLow)
            }
        }
        Spacer(Modifier.height(2.dp))
        Text(dateTitle(day, today), style = MaterialTheme.typography.headlineMedium, color = InkHi)
        if (day > today) {
            Spacer(Modifier.height(4.dp))
            Text(
                "drafting tomorrow is a gift to morning-you",
                style = MaterialTheme.typography.bodyMedium,
                color = InkLow,
            )
        }
        Spacer(Modifier.height(14.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DayChip("today", day == today) {
                if (day != today) {
                    BUHaptic.tick(view)
                    vm.selectDay(today)
                }
            }
            DayChip("tomorrow", day == today + 1) {
                if (day != today + 1) {
                    BUHaptic.tick(view)
                    vm.selectDay(today + 1)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 150.dp),
        ) {
            item(key = "big-header") {
                SectionHeader(
                    title = "big three",
                    count = "${bigs.count { it.state != PlanState.DROPPED }} / 3",
                    action = "add",
                    onAction = { BUHaptic.tick(view); vm.openNew(PlanKind.BIG) },
                )
            }
            if (bigs.isEmpty()) {
                item(key = "big-empty") {
                    EmptyInvite(
                        text = "nothing owns this day yet. choose up to three.",
                        action = "plan the big three",
                        onAction = { BUHaptic.tick(view); vm.openNew(PlanKind.BIG) },
                    )
                }
            } else {
                items(bigs, key = { it.id }) { item ->
                    PlanCard(
                        item = item,
                        track = item.trackId?.let { trackById[it] },
                        isToday = day == today,
                        onOpen = { BUHaptic.tap(view); vm.openEdit(item) },
                        onState = { state -> BUHaptic.tick(view); vm.setState(item, state) },
                        onCarry = { BUHaptic.tick(view); vm.carry(item) },
                    )
                }
            }

            item(key = "slots-header") {
                SectionHeader(
                    title = "slots",
                    count = "${slots.size}",
                    action = "add",
                    onAction = { BUHaptic.tick(view); vm.openNew(PlanKind.BLOCK) },
                )
            }
            if (slots.isEmpty()) {
                item(key = "slots-empty") {
                    EmptyInvite(
                        text = "no time slots. a block makes time real — give one a start and a length.",
                        action = "new block",
                        onAction = { BUHaptic.tick(view); vm.openNew(PlanKind.BLOCK) },
                    )
                }
            } else {
                items(slots, key = { it.id }) { item ->
                    PlanCard(
                        item = item,
                        track = item.trackId?.let { trackById[it] },
                        isToday = day == today,
                        onOpen = { BUHaptic.tap(view); vm.openEdit(item) },
                        onState = { state -> BUHaptic.tick(view); vm.setState(item, state) },
                        onCarry = { BUHaptic.tick(view); vm.carry(item) },
                    )
                }
            }

            if (loose.isNotEmpty()) {
                item(key = "loose-header") {
                    SectionHeader(title = "loose", count = "${loose.size}", action = null, onAction = {})
                }
                items(loose, key = { it.id }) { item ->
                    PlanCard(
                        item = item,
                        track = item.trackId?.let { trackById[it] },
                        isToday = day == today,
                        onOpen = { BUHaptic.tap(view); vm.openEdit(item) },
                        onState = { state -> BUHaptic.tick(view); vm.setState(item, state) },
                        onCarry = { BUHaptic.tick(view); vm.carry(item) },
                    )
                }
            }

            item(key = "sparks-teaser") {
                SparksTeaser(newCount = newSparks, onOpen = onOpenSparks)
            }
            item(key = "footer") {
                Text(
                    "momentum joins at p3 · the arc stays honest until then",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkFaint,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }
    }

    if (vm.editorOpen) {
        PlanEditorSheet(vm)
    }
}

@Composable
private fun PlanCard(
    item: PlanItem,
    track: Track?,
    isToday: Boolean,
    onOpen: () -> Unit,
    onState: (PlanState) -> Unit,
    onCarry: () -> Unit,
) {
    val stateColor = when (item.state) {
        PlanState.DONE -> Mint
        PlanState.PARTIAL -> MintMid
        PlanState.CARRIED, PlanState.MISSED, PlanState.DROPPED -> Graphite
        PlanState.PENDING -> InkMid
    }
    val titleColor = if (item.state == PlanState.PENDING || item.state == PlanState.DONE || item.state == PlanState.PARTIAL) {
        InkHi
    } else {
        InkLow
    }

    BUCard(modifier = Modifier.fillMaxWidth().animateItem(), onClick = onOpen) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            track?.let { TrackColors[it.colorToken.coerceIn(0, TrackColors.lastIndex)] } ?: Line,
                        ),
                )
                Spacer(Modifier.size(10.dp))
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = titleColor,
                    textDecoration = if (item.state == PlanState.DONE) TextDecoration.LineThrough else null,
                    modifier = Modifier.weight(1f),
                )
                if (item.state != PlanState.PENDING) {
                    Text(item.state.label, style = MaterialTheme.typography.labelSmall, color = stateColor)
                }
            }
            val start = item.startMin
            val dur = item.durMin
            if (start != null && dur != null) {
                Spacer(Modifier.height(6.dp))
                Text("${BUTime.fmtMin(start)} · ${dur}m", style = MonoNumerals, color = InkLow)
            }
            if (item.carriedFromId != null) {
                Spacer(Modifier.height(4.dp))
                Text("carried in", style = MaterialTheme.typography.labelSmall, color = InkFaint)
            }
            if (isToday) {
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    when (item.state) {
                        PlanState.PENDING -> {
                            MiniAction("done") { onState(PlanState.DONE) }
                            MiniAction("part") { onState(PlanState.PARTIAL) }
                            MiniAction("carry") { onCarry() }
                            MiniAction("drop") { onState(PlanState.DROPPED) }
                        }
                        PlanState.DONE, PlanState.PARTIAL -> MiniAction("undo") { onState(PlanState.PENDING) }
                        PlanState.DROPPED -> MiniAction("restore") { onState(PlanState.PENDING) }
                        PlanState.CARRIED, PlanState.MISSED -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun MiniAction(text: String, onClick: () -> Unit) {
    val view = LocalView.current
    Box(
        Modifier
            .clip(CircleShape)
            .background(Layer2)
            .border(1.dp, Line2, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { BUHaptic.tick(view); onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall, color = InkMid)
    }
}

@Composable
private fun DayChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .clip(CircleShape)
            .background(if (selected) Mint else Layer)
            .border(1.dp, if (selected) Mint else Line, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = if (selected) MintInk else InkLow)
    }
}

@Composable
private fun SectionHeader(title: String, count: String, action: String?, onAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        SectionLabel(title)
        Spacer(Modifier.size(8.dp))
        Text(count, style = MaterialTheme.typography.labelSmall, color = InkFaint)
        Spacer(Modifier.weight(1f))
        if (action != null) {
            Text(
                "+ $action",
                style = MaterialTheme.typography.labelLarge,
                color = Mint,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onAction,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }
    }
}

@Composable
private fun EmptyInvite(text: String, action: String, onAction: () -> Unit) {
    BUCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text, style = MaterialTheme.typography.bodyMedium, color = InkLow)
            Spacer(Modifier.height(10.dp))
            GhostButton(text = action, onClick = onAction)
        }
    }
}

@Composable
private fun SparksTeaser(newCount: Int, onOpen: () -> Unit) {
    BUCard(modifier = Modifier.fillMaxWidth(), onClick = onOpen) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                SectionLabel("spark inbox")
                Spacer(Modifier.height(4.dp))
                Text(
                    if (newCount > 0) "$newCount waiting to be shaped" else "all sparks shaped — capture more anytime",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkLow,
                )
            }
            Text("→", style = MaterialTheme.typography.titleLarge, color = Mint)
        }
    }
}

private val DayFormat = DateTimeFormatter.ofPattern("EEE · MMM d", Locale.ENGLISH)

private fun dateTitle(day: Long, today: Long): String {
    val formatted = BUTime.localDateOf(day).format(DayFormat).lowercase(Locale.ENGLISH)
    return when (day) {
        today -> "today · $formatted"
        today + 1 -> "tomorrow · $formatted"
        else -> formatted
    }
}
