package com.bu.identity.sparks

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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.core.model.Capture
import com.bu.core.model.CaptureStatus
import com.bu.core.time.BUTime
import com.bu.designsystem.component.BUCard
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.JournalSerif
import com.bu.designsystem.theme.Layer2
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Line2
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintGlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Sparks — the capture stage of the loop.
 * The user's words are serif and untouchable. Capture is cheap on purpose:
 * one line now, shaped into the plan whenever they're ready.
 */
@HiltViewModel
class SparksViewModel @Inject constructor(
    private val repo: com.bu.identity.data.PlanRepository,
) : ViewModel() {

    val inbox: StateFlow<List<Capture>> = repo.observeInbox()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun capture(raw: String) {
        viewModelScope.launch {
            if (raw.isNotBlank()) repo.quickCapture(raw)
        }
    }

    fun promote(capture: Capture) {
        viewModelScope.launch { repo.promoteCapture(capture, BUTime.todayEpochDay()) }
    }

    fun discard(capture: Capture) {
        viewModelScope.launch { repo.discardCapture(capture.id) }
    }
}

@Composable
fun SparksScreen(onBack: () -> Unit, vm: SparksViewModel = hiltViewModel()) {
    val view = LocalView.current
    val inbox by vm.inbox.collectAsStateWithLifecycle()
    var draft by remember { mutableStateOf("") }
    val now = remember { System.currentTimeMillis() }

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
        SectionLabel("sparks")
        Spacer(Modifier.height(6.dp))
        Text(
            "catch the spark.\nshape it later.",
            style = MaterialTheme.typography.headlineMedium,
            color = InkHi,
        )
        Spacer(Modifier.height(18.dp))

        BUCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SectionLabel("capture")
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it },
                    placeholder = { Text("a thought, a task, a worry — one line is enough", color = InkFaint) },
                    minLines = 2,
                    maxLines = 4,
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "saved offline, shaped whenever you're ready",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkFaint,
                        modifier = Modifier.weight(1f),
                    )
                    MintButton(
                        text = "capture",
                        enabled = draft.isNotBlank(),
                        onClick = {
                            BUHaptic.lock(view)
                            vm.capture(draft)
                            draft = ""
                        },
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        if (inbox.isEmpty()) {
            Text(
                "the inbox is empty. sparks are cheap — write it before it evaporates.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkFaint,
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 150.dp),
        ) {
            items(inbox, key = { it.id }) { capture ->
                CaptureCard(
                    capture = capture,
                    agoText = ago(capture.ts, now),
                    onPromote = { BUHaptic.lock(view); vm.promote(capture) },
                    onDiscard = { BUHaptic.tap(view); vm.discard(capture) },
                )
            }
        }
    }
}

@Composable
private fun CaptureCard(
    capture: Capture,
    agoText: String,
    onPromote: () -> Unit,
    onDiscard: () -> Unit,
) {
    val resolved = capture.status == CaptureStatus.RESOLVED
    BUCard(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(
                capture.raw,
                style = JournalSerif,
                color = if (resolved) InkLow else InkHi,
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(agoText, style = MaterialTheme.typography.labelSmall, color = InkFaint)
                Text(
                    capture.status.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (resolved) Mint else InkFaint,
                )
                Spacer(Modifier.weight(1f))
                if (capture.status == CaptureStatus.NEW) {
                    MiniPill("plan it", emphasized = true, onClick = onPromote)
                    MiniPill("drop", emphasized = false, onClick = onDiscard)
                }
            }
        }
    }
}

@Composable
private fun MiniPill(text: String, emphasized: Boolean, onClick: () -> Unit) {
    val view = LocalView.current
    Box(
        Modifier
            .clip(CircleShape)
            .background(if (emphasized) MintGlow else Layer2)
            .border(1.dp, if (emphasized) Mint else Line2, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { BUHaptic.tick(view); onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall, color = if (emphasized) Mint else InkMid)
    }
}

private fun ago(ts: Long, now: Long): String {
    val minutes = ((now - ts) / 60_000L).coerceAtLeast(0)
    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "${minutes}m ago"
        minutes < 60 * 24 -> "${minutes / 60}h ago"
        else -> "${minutes / (60 * 24)}d ago"
    }
}
