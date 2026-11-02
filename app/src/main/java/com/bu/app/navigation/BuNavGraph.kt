package com.bu.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bu.core.settings.BuSettingsStore
import com.bu.core.time.BUTime
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.Gold
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.Mint
import com.bu.identity.onboarding.ManifestScreen
import com.bu.identity.onboarding.SetupWizardScreen
import com.bu.identity.onboarding.WelcomeScreen
import com.bu.identity.settings.SettingsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** Root state: has the loop been set up yet? */
@HiltViewModel
class BuShellViewModel @Inject constructor(
    store: BuSettingsStore,
) : ViewModel() {
    val settings = store.settings
}

object BuRoutes {
    const val WELCOME = "welcome"
    const val MANIFEST = "manifest"
    const val SETUP = "setup"
    const val DAY = "day"
    const val SETTINGS = "settings"
}

@Composable
fun BuNavGraph() {
    val nav = rememberNavController()
    val shell: BuShellViewModel = hiltViewModel()
    val settings by shell.settings.collectAsStateWithLifecycle()

    // start destination respects setup state; when setup completes mid-session the
    // nav action from the wizard routes forward anyway (explicit, not reactive) —
    // so the start value is only read once, like a first-run flag.
    val start = if (settings.setupDone) BuRoutes.DAY else BuRoutes.WELCOME

    NavHost(
        navController = nav,
        startDestination = start,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(BU_NAV_MS)) { it / 5 }
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(BU_NAV_MS)) { -it / 8 }
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(BU_NAV_MS)) { -it / 5 }
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(BU_NAV_MS)) { it / 8 }
        },
    ) {
        composable(BuRoutes.WELCOME) { WelcomeScreen(onBegin = { nav.navigate(BuRoutes.MANIFEST) }) }
        composable(BuRoutes.MANIFEST) { ManifestScreen(onDone = { nav.navigate(BuRoutes.SETUP) }) }
        composable(BuRoutes.SETUP) {
            SetupWizardScreen(
                onFinish = {
                    nav.navigate(BuRoutes.DAY) {
                        popUpTo(BuRoutes.WELCOME) { inclusive = true }
                    }
                },
            )
        }
        composable(BuRoutes.DAY) { DayPlaceholder(onOpenSettings = { nav.navigate(BuRoutes.SETTINGS) }) }
        composable(BuRoutes.SETTINGS) { SettingsScreen(onBack = { nav.popBackStack() }) }
    }
}

private const val BU_NAV_MS = 340

/**
 * P0's Day screen: the stage is set, the actors arrive in P1.
 * It already speaks the doctrine — greeting, horizon glow, loop status.
 */
@Composable
private fun DayPlaceholder(onOpenSettings: () -> Unit) {
    val shell: BuShellViewModel = hiltViewModel()
    val s by shell.settings.collectAsStateWithLifecycle()
    val view = LocalView.current

    Box(
        Modifier
            .fillMaxSize()
            .background(Bg0)
            .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SectionLabel("the loop")
            Spacer(Modifier.height(10.dp))
            Text(
                "good ${greeting()}, ${s.username.ifBlank { "friend" }}",
                style = MaterialTheme.typography.headlineMedium,
                color = InkHi,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "your day runs ${BUTime.fmtMin(s.dayStartMin)} → ${BUTime.fmtMin(s.dayEndMin)}.\n" +
                    "dawn at ${BUTime.fmtMin(s.dawnMin)} · dusk at ${BUTime.fmtMin(s.duskMin)}.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkLow,
                textAlign = TextAlign.Center,
            )
            if (s.hasExam) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "${s.examName} · ${BUTime.fmtDaysUntil(s.examDateEpochDay!!)} days",
                    style = MaterialTheme.typography.titleMedium,
                    color = Gold,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(Modifier.height(26.dp))
            Text(
                "✦ tracks, the Day Arc, Big Three, blocks, sparks\nand the pebble arrive with P1.",
                style = MaterialTheme.typography.bodyMedium,
                color = InkFaint,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(30.dp))
            MintButton(
                text = "open settings",
                onClick = { BUHaptic.tick(view); onOpenSettings() },
            )
        }
    }
}

private fun greeting(): String {
    val m = BUTime.minutesOfDayNow()
    return when {
        m < 5 * 60 -> "night owl"
        m < 12 * 60 -> "morning"
        m < 17 * 60 -> "afternoon"
        else -> "evening"
    }
}
