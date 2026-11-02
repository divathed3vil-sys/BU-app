package com.bu.identity.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bu.designsystem.component.GhostButton
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.InkMid
import com.bu.designsystem.theme.Mint

/**
 * Welcome — the first breath of BU. One sparkle allowed by doctrine.
 * The tone: quiet confidence. No marketing. No screenshots of other people's lives.
 */
@Composable
fun WelcomeScreen(onBegin: () -> Unit) {
    val view = LocalView.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg0)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("✦", style = MaterialTheme.typography.displayLarge, color = Mint)
        Spacer(Modifier.height(18.dp))
        Text("BU", style = MaterialTheme.typography.displayLarge, color = InkHi, textAlign = TextAlign.Center)
        Spacer(Modifier.height(10.dp))
        Text(
            "one loop. your day, closed every night.",
            style = MaterialTheme.typography.bodyLarge,
            color = InkMid,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(44.dp))
        MintButton(text = "Begin", onClick = { BUHaptic.tick(view); onBegin() }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(14.dp))
        Text(
            "everything on this device. nothing in the cloud.\nnothing pre-made — you build your own tracks.",
            style = MaterialTheme.typography.bodyMedium,
            color = InkLow,
            textAlign = TextAlign.Center,
        )
    }
}

/**
 * Capability Manifest — every permission = what it unlocks. Skip-able except essentials.
 * P0 presents the doctrine; live grant flows land with Guardian (P4/P5).
 */
private data class Capability(val title: String, val unlocks: String, val live: Boolean)

@Composable
fun ManifestScreen(onDone: () -> Unit) {
    val view = LocalView.current
    val caps = listOf(
        Capability("Notifications", "ritual nudges, reminders, your escalation chain", live = true),
        Capability("Exact alarms", "Dawn Push and Dusk Close fire on the minute", live = true),
        Capability("Focus & attention", "the pebble, focus sessions, the Day Arc", live = true),
        Capability("Screen guard", "sees which app is open → mirrors your own plan back", live = false),
        Capability("Notification batching", "holds the noise, releases it on your schedule", live = false),
        Capability("Uninstall shield", "protects BU from midnight-you", live = false),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg0)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 48.dp),
    ) {
        SectionLabel("capability manifest")
        Spacer(Modifier.height(8.dp))
        Text("what BU asks for,\nand what it buys you", style = MaterialTheme.typography.headlineMedium, color = InkHi)
        Spacer(Modifier.height(24.dp))
        caps.forEach { cap ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(8.dp)
                        .background(if (cap.live) Mint else InkFaint, CircleShape),
                )
                Spacer(Modifier.size(14.dp))
                Column {
                    Row {
                        Text(cap.title, style = MaterialTheme.typography.titleMedium, color = InkHi)
                        if (!cap.live) {
                            Text("  · later phase", style = MaterialTheme.typography.labelSmall, color = InkFaint)
                        }
                    }
                    Text(cap.unlocks, style = MaterialTheme.typography.bodyMedium, color = InkLow)
                }
            }
        }
        Spacer(Modifier.height(28.dp))
        Text("the guard stays optional. the loop does not.", style = MaterialTheme.typography.bodyMedium, color = InkFaint)
        Spacer(Modifier.height(22.dp))
        MintButton(text = "I understand", onClick = { BUHaptic.tick(view); onDone() }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        GhostButton(text = "Tell me later", onClick = { BUHaptic.tap(view); onDone() }, modifier = Modifier.fillMaxWidth())
    }
}
