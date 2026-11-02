package com.bu.app.atlas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bu.designsystem.component.BuEmptyState

/** ATLAS — the knowledge universe. Notes, wiki links, backlinks, graph in P3. */
@Composable
fun AtlasTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BuEmptyState(
            title = "ATLAS arrives in P3",
            body = "Your knowledge universe: Markdown notes with wiki links and " +
                "backlinks, typed relationships, and a bounded graph view. " +
                "Markdown lives in the ledger, so it is exportable forever.",
        )
    }
}
