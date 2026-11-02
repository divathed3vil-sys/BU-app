package com.bu.app.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bu.designsystem.component.BuArcDial
import com.bu.designsystem.component.BuCard
import com.bu.designsystem.component.BuEmptyState
import com.bu.designsystem.theme.BuTextDim

/** PLAN — the temporal workspace. Blocks + Care Items land in P1–P2. */
@Composable
fun PlanTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BuEmptyState(
            title = "PLAN arrives in P1",
            body = "Blocks will be placed on a real timeline with a capacity bar that " +
                "refuses to lie about your day. Care Items (anchor · contextual · " +
                "relational · value · deadline · habit) follow in P2.",
        )

        // The Arc-Dial — proof that BU has no sliders. Drags snap in 15-minute
        // steps with a haptic tick; ±5m steppers are the accessible path.
        DialDemo()
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun DialDemo() {
    var value by remember { mutableIntStateOf(16 * 60) }
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Text("Arc-Dial", style = MaterialTheme.typography.titleMedium)
        Text(
            "BU's only time picker — try it. No sliders exist in this app.",
            style = MaterialTheme.typography.bodyMedium,
            color = BuTextDim,
        )
        BuArcDial(
            valueMinutes = value,
            onValueChange = { value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(240.dp),
        )
    }
}
