package com.bu.app.journal

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

/** JOURNAL — the living chronicle. Threads, check-ins, evidence arrive in P3. */
@Composable
fun JournalTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BuEmptyState(
            title = "JOURNAL arrives in P3",
            body = "A living chronicle: your writing first, auto-filled evidence behind it. " +
                "Every automatic line will carry its source — observed, scheduled, " +
                "inferred, or confirmed by you. Deep Write keeps the serif.",
        )
    }
}
