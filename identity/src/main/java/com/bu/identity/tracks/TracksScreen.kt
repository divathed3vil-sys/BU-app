package com.bu.identity.tracks

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.core.model.Track
import com.bu.core.model.TrackKind
import com.bu.designsystem.component.BUCard
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintInk
import com.bu.designsystem.theme.TrackColors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Tracks — the user's own constellations-in-waiting.
 * BU ships zero tracks. This screen is where the system becomes theirs.
 */
@HiltViewModel
class TracksViewModel @Inject constructor(
    private val repo: com.bu.identity.data.PlanRepository,
) : ViewModel() {

    val tracks: StateFlow<List<Track>> = repo.observeActiveTracks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun create(name: String, colorToken: Int, kind: TrackKind) {
        viewModelScope.launch { repo.createTrack(name, colorToken, kind) }
    }

    fun archive(track: Track) {
        viewModelScope.launch { repo.archiveTrack(track.id) }
    }
}

@Composable
fun TracksScreen(onBack: () -> Unit, vm: TracksViewModel = hiltViewModel()) {
    val view = LocalView.current
    val tracks by vm.tracks.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var colorToken by remember { mutableStateOf(0) }
    var kind by remember { mutableStateOf(TrackKind.AREA) }

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
            TextButton(onClick = { BUHaptic.tap(view); onBack() }) {
                Text("← loop", color = InkLow)
            }
        }
        Spacer(Modifier.height(2.dp))
        SectionLabel("your tracks")
        Spacer(Modifier.height(6.dp))
        Text(
            "nothing pre-made.\neverything earned.",
            style = MaterialTheme.typography.headlineMedium,
            color = InkHi,
        )
        Spacer(Modifier.height(18.dp))

        BUCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel("new track")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("e.g. Physics · Reading · Gym", color = InkFaint) },
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
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TrackColors.forEachIndexed { index, color ->
                        ColorDot(
                            selected = colorToken == index,
                            color = color,
                            onClick = { BUHaptic.tick(view); colorToken = index },
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TrackKind.entries.forEach { candidate ->
                        KindChip(candidate.label, kind == candidate) {
                            BUHaptic.tick(view); kind = candidate
                        }
                    }
                }
                MintButton(
                    text = "create track",
                    enabled = name.isNotBlank(),
                    onClick = {
                        BUHaptic.lock(view)
                        vm.create(name, colorToken, kind)
                        name = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Spacer(Modifier.height(14.dp))
        if (tracks.isEmpty()) {
            Text(
                "your tracks live here. BU ships zero — the first one is yours.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkFaint,
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 150.dp),
        ) {
            items(tracks, key = { it.id }) { track ->
                BUCard(modifier = Modifier.fillMaxWidth().animateItem()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(TrackColors[track.colorToken.coerceIn(0, TrackColors.lastIndex)]),
                        )
                        Spacer(Modifier.size(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text(track.name, style = MaterialTheme.typography.titleMedium, color = InkHi)
                            Text(track.kind.label, style = MaterialTheme.typography.labelSmall, color = InkLow)
                        }
                        Text(
                            "archive",
                            style = MaterialTheme.typography.labelSmall,
                            color = InkFaint,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                ) { BUHaptic.tap(view); vm.archive(track) }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorDot(selected: Boolean, color: Color, onClick: () -> Unit) {
    Box(
        Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) InkHi else Line,
                shape = CircleShape,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    )
}

@Composable
private fun KindChip(text: String, selected: Boolean, onClick: () -> Unit) {
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
            .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = if (selected) MintInk else InkMid)
    }
}
