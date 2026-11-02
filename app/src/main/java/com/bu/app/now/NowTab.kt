package com.bu.app.now

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bu.designsystem.component.BuCard
import com.bu.designsystem.component.BuDayArc
import com.bu.designsystem.theme.BuLayerHigh
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuTextDim
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class NowViewModel @Inject constructor(
    eventDao: com.bu.core.ledger.EventDao,
    outboxDao: com.bu.core.ledger.OutboxDao,
) : ViewModel() {

    private val dayStartMs: Long =
        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    /** Captures the kernel committed today — proof the loop is alive. */
    val capturesToday: StateFlow<Int> = eventDao.captureCountSince(dayStartMs)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    /** Pending outbox effects — the async work the kernel still owes. */
    val pendingEffects: StateFlow<Int> = outboxDao.pendingCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
}

@Composable
fun NowTab(viewModel: NowViewModel = hiltViewModel()) {
    val captures by viewModel.capturesToday.collectAsStateWithLifecycle()
    val pending by viewModel.pendingEffects.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // The Day Arc — P0 renders the field; P1 feeds it the real day.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            contentAlignment = Alignment.Center,
        ) {
            BuDayArc(
                progress = 0.42f,
                nowProgress = 0.42f,
                modifier = Modifier.size(264.dp),
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Day Arc", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "5:00 → 23:00 · demo state",
                    style = MaterialTheme.typography.labelSmall,
                    color = BuTextDim,
                )
            }
        }

        KernelPulseCard(captures = captures, pending = pending)

        BuCard(modifier = Modifier.fillMaxWidth()) {
            Text("Big Three", style = MaterialTheme.typography.titleMedium)
            Text(
                "Tomorrow's cockpit arrives in P1: up to three priorities, one Main Front, " +
                    "a Minimum Day line, and the Tally Trio.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
        }

        BuCard(modifier = Modifier.fillMaxWidth()) {
            Text("Sparks", style = MaterialTheme.typography.titleMedium)
            Text(
                "Quick thoughts land here in P1. The Pebble already captures safely — " +
                    "everything you save is in the ledger right now.",
                style = MaterialTheme.typography.bodyMedium,
                color = BuTextDim,
            )
        }
        Box(Modifier.size(1.dp))
    }
}

@Composable
private fun KernelPulseCard(captures: Int, pending: Int) {
    BuCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(BuMint, RoundedCornerShape(5.dp)),
            )
            Column {
                Text("Kernel live", style = MaterialTheme.typography.titleMedium)
                Text(
                    "captures today: $captures · async work queued: $pending",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BuTextDim,
                )
            }
        }
    }
}
