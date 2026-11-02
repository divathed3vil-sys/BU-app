package com.bu.identity.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bu.core.model.PlanKind
import com.bu.core.plan.CarryLogic
import com.bu.core.time.BUTime
import com.bu.designsystem.component.GhostButton
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg1
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintDeep
import com.bu.designsystem.theme.MintInk
import com.bu.designsystem.theme.MonoNumerals
import com.bu.designsystem.theme.TrackColors

private val DURATIONS = listOf(25, 45, 60, 90)

/**
 * The plan editor — one sheet, one decision.
 * Big Three are gated by the loop's own rule: three is the whole point.
 * Blocks must fit inside the waking day. Everything answers with haptics.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PlanEditorSheet(vm: DayViewModel) {
    val view = LocalView.current
    val s by vm.settings.collectAsStateWithLifecycle()
    val tracks by vm.tracks.collectAsStateWithLifecycle()
    val original = vm.editorItem ?: return
    val editing = original.id != 0L

    var title by remember(original.id) { mutableStateOf(original.title) }
    var kind by remember(original.id) { mutableStateOf(original.kind) }
    var trackId by remember(original.id) { mutableStateOf(original.trackId) }
    var startMin by remember(original.id) { mutableStateOf(original.startMin) }
    var durMin by remember(original.id) { mutableStateOf(original.durMin ?: 45) }
    var pinned by remember(original.id) { mutableStateOf(original.startMin != null && original.kind == PlanKind.BIG) }

    val timeOn = kind == PlanKind.BLOCK || pinned
    val bigBlocked = kind == PlanKind.BIG && !(editing && original.kind == PlanKind.BIG) && !vm.canAddBig()

    val rangeStart = s.dayStartMin
    val rangeEnd = (s.dayEndMin - durMin).coerceAtLeast(s.dayStartMin)
    val chosenStart = (startMin ?: rangeStart).coerceIn(rangeStart, rangeEnd)
    val fits = !timeOn || CarryLogic.fitsInDay(chosenStart, durMin, s.dayStartMin, s.dayEndMin)

    ModalBottomSheet(
        onDismissRequest = { vm.closeEditor() },
        containerColor = Layer,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .navigationBarsPadding(),
        ) {
            SectionLabel(if (editing) "edit plan" else "new plan")
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                KindChip("big three", kind == PlanKind.BIG) { BUHaptic.tick(view); kind = PlanKind.BIG }
                KindChip("block", kind == PlanKind.BLOCK) { BUHaptic.tick(view); kind = PlanKind.BLOCK }
                if (original.kind == PlanKind.SPARK) {
                    KindChip("spark", kind == PlanKind.SPARK) { BUHaptic.tick(view); kind = PlanKind.SPARK }
                }
            }
            if (kind == PlanKind.BIG) {
                Spacer(Modifier.height(8.dp))
                Text(
                    if (bigBlocked) {
                        "three already own this day. tomorrow can carry the rest."
                    } else {
                        "at most three own a day — three is the whole point."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (bigBlocked) InkHi else InkLow,
                )
            }

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("what is it?", color = InkFaint) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Mint,
                    unfocusedBorderColor = Line,
                    focusedTextColor = InkHi,
                    unfocusedTextColor = InkHi,
                    cursorColor = Mint,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(16.dp))
            SectionLabel("track")
            Spacer(Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TrackDotChip(label = "none", color = null, selected = trackId == null) {
                    BUHaptic.tick(view); trackId = null
                }
                tracks.forEach { track ->
                    TrackDotChip(
                        label = track.name,
                        color = TrackColors[track.colorToken.coerceIn(0, TrackColors.lastIndex)],
                        selected = trackId == track.id,
                    ) { BUHaptic.tick(view); trackId = track.id }
                }
            }

            if (kind == PlanKind.BIG) {
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SectionLabel("pin a time")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = pinned,
                        onCheckedChange = { BUHaptic.tick(view); pinned = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MintDeep,
                            checkedThumbColor = Mint,
                            uncheckedTrackColor = Line,
                        ),
                    )
                }
            }
            if (timeOn) {
                Spacer(Modifier.height(10.dp))
                SectionLabel("when")
                Spacer(Modifier.height(4.dp))
                Text(
                    "starts · ${BUTime.fmtMin(chosenStart)} · ${durMin}m",
                    style = MonoNumerals,
                    color = InkHi,
                )
                Slider(
                    value = chosenStart.toFloat(),
                    onValueChange = { startMin = it.toInt() },
                    valueRange = rangeStart.toFloat()..rangeEnd.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = Mint,
                        activeTrackColor = MintDeep,
                        inactiveTrackColor = Line,
                    ),
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DURATIONS.forEach { d ->
                        KindChip("${d}m", durMin == d) { BUHaptic.tick(view); durMin = d }
                    }
                }
                if (!fits) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "that runs past your waking day — pull it in.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkHi,
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (editing) {
                    GhostButton(text = "delete", onClick = { BUHaptic.tap(view); vm.delete(original) })
                }
                Spacer(Modifier.weight(1f))
                MintButton(
                    text = if (editing) "save" else "add it",
                    enabled = title.isNotBlank() && !bigBlocked && fits,
                    onClick = {
                        BUHaptic.lock(view)
                        val timedStart = if (timeOn) chosenStart else null
                        vm.save(
                            original.copy(
                                kind = kind,
                                title = title.trim(),
                                trackId = trackId,
                                startMin = timedStart,
                                durMin = timedStart?.let { durMin },
                            ),
                        )
                    },
                    modifier = Modifier.padding(start = 12.dp),
                )
            }
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun KindChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .clip(CircleShape)
            .background(if (selected) Mint else Bg1)
            .border(1.dp, if (selected) Mint else Line, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = if (selected) MintInk else InkMid)
    }
}

@Composable
private fun TrackDotChip(label: String, color: Color?, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .clip(CircleShape)
            .background(if (selected) Mint else Bg1)
            .border(1.dp, if (selected) Mint else Line, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color ?: InkFaint),
        )
        Spacer(Modifier.size(6.dp))
        Text(label, style = MaterialTheme.typography.labelLarge, color = if (selected) MintInk else InkMid)
    }
}
