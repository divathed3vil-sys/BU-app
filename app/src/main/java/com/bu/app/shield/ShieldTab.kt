package com.bu.app.shield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bu.designsystem.component.BuCard
import com.bu.designsystem.component.BuEmptyState
import com.bu.designsystem.theme.BuTextDim
import com.bu.guardian.ladder.Rung

/** SHIELD — the Guardian plane. Usage capture + rungs land in P4–P5. */
@Composable
fun ShieldTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BuEmptyState(
            title = "SHIELD arrives in P4",
            body = "Gentle first, firm only where you sealed it: app budgets, focus " +
                "sessions that inherit your tracks, one-breath pauses, and a " +
                "notification budget that respects your sleep.",
        )

        // The ladder exists in the domain already — shown honestly as P0 state.
        BuCard(modifier = Modifier.fillMaxWidth()) {
            Text("The ladder (L0–L5)", style = MaterialTheme.typography.titleMedium)
            Text(
                "Observe · Nudge · Pause · Soft · Hard · Contract lock",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
            Text(
                "Escalation can never pass a ceiling you sealed the night before. " +
                    "The engine is pure Kotlin and tested; its UI arrives in P4–P5.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
            Text(
                Rung.entries.joinToString(" · ") { "L${it.level} ${it.label}" },
                style = MaterialTheme.typography.labelSmall,
                color = BuTextDim,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}
