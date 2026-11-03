package com.bu.app.onboarding

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bu.core.command.BuCommand
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.outbox.OutboxProcessor
import com.bu.designsystem.component.BuArcDial
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuMint
import com.bu.designsystem.theme.BuTextDim
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * Onboarding (locked plan §4.5): one decision per screen, giant type, ≤90 s.
 * Screen 2 has no sliders — the Arc-Dial is the only time picker in BU.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
) : ViewModel() {

    fun submit(command: BuCommand, onResult: (CommandReceipt) -> Unit = {}) {
        viewModelScope.launch {
            val receipt = gateway.submit(command)
            outboxProcessor.drain()
            onResult(receipt)
        }
    }
}

@Composable
fun OnboardingFlow(
    onDone: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    var step by remember { mutableIntStateOf(0) }

    fun finish() {
        viewModel.submit(BuCommand.MarkOnboarded()) { onDone() }
    }

    AnimatedContent(
        targetState = step,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInHorizontally { it } + fadeIn()) togetherWith (slideOutHorizontally { -it } + fadeOut())
            } else {
                (slideInHorizontally { -it } + fadeIn()) togetherWith (slideOutHorizontally { it } + fadeOut())
            }
        },
        label = "onboarding",
        modifier = Modifier
            .fillMaxSize()
            .background(BuBackground),
    ) { current ->
        when (current) {
            0 -> WelcomeStep(onNext = { step = 1 })
            1 -> DayWindowStep(
                onSet = { start, end ->
                    viewModel.submit(BuCommand.SetDayWindow(start, end)) { receipt ->
                        if (receipt.status == com.bu.core.command.CommandStatus.COMMITTED) step = 2
                    }
                },
            )
            else -> NotificationsStep(
                onAllowed = { finish() },
                onLater = { finish() },
            )
        }
    }
}

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("BU", style = MaterialTheme.typography.displayLarge, color = BuMint)
        Spacer(Modifier.height(16.dp))
        Text(
            "A life system that holds your plans, your memory and your attention — then hands your day back to you.",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Everything you capture is yours, saved first, understood after. One decision per screen.",
            style = MaterialTheme.typography.bodyLarge,
            color = BuTextDim,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text("Begin")
        }
    }
}

/** The day window: 5:00 → 23:00 by default, adjustable — no sliders, ever. */
@Composable
private fun DayWindowStep(onSet: (Int, Int) -> Unit) {
    var start by remember { mutableIntStateOf(5 * 60) }
    var end by remember { mutableIntStateOf(23 * 60) }
    val tooShort = end - start < 240

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Your day", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(4.dp))
        Text(
            "Where does a day begin and close for you?",
            style = MaterialTheme.typography.bodyLarge,
            color = BuTextDim,
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Opens", style = MaterialTheme.typography.labelLarge, color = BuMint)
                BuArcDial(
                    valueMinutes = start,
                    onValueChange = { start = it },
                    rangeStartMinute = 0,
                    rangeEndMinute = 12 * 60,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                )
                Text(formatTime(start), style = MaterialTheme.typography.titleLarge)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Closes", style = MaterialTheme.typography.labelLarge, color = BuMint)
                BuArcDial(
                    valueMinutes = end,
                    onValueChange = { end = it },
                    rangeStartMinute = 12 * 60,
                    rangeEndMinute = 24 * 60 - 15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                )
                Text(formatTime(end), style = MaterialTheme.typography.titleLarge)
            }
        }
        if (tooShort) {
            Spacer(Modifier.height(8.dp))
            Text(
                "A day shorter than 4 hours is not a day — widen it a little.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onSet(start, end) },
            enabled = !tooShort,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Set my day")
        }
    }
}

/** Permission honesty: ask once, explain why, and never block on a no. */
@Composable
private fun NotificationsStep(
    onAllowed: () -> Unit,
    onLater: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { onAllowed() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Care", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text(
            "Care reminders are the only thing BU will ever interrupt you for. One ring, your words, no nagging.",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "You can skip this — everything still saves and shows in PLAN.",
            style = MaterialTheme.typography.bodyLarge,
            color = BuTextDim,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= 33) {
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    onAllowed()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Allow notifications")
        }
        TextButton(onClick = onLater, modifier = Modifier.fillMaxWidth()) {
            Text("Later")
        }
    }
}

private fun formatTime(minute: Int): String {
    val h = minute / 60
    val m = minute % 60
    return "$h:" + m.toString().padStart(2, '0')
}
