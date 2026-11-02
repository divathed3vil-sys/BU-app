package com.bu.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bu.core.model.PlanKind
import com.bu.core.model.TrackKind
import com.bu.core.settings.BuSettingsStore
import com.bu.core.time.BUTime
import com.bu.designsystem.component.BuPebble
import com.bu.designsystem.theme.Bg0
import com.bu.identity.data.PlanRepository
import com.bu.identity.onboarding.ManifestScreen
import com.bu.identity.onboarding.SetupWizardScreen
import com.bu.identity.onboarding.WelcomeScreen
import com.bu.identity.plan.DayArcScreen
import com.bu.identity.quick.BuQuickActions
import com.bu.identity.settings.SettingsScreen
import com.bu.identity.sparks.QuickCaptureSheet
import com.bu.identity.sparks.SparksScreen
import com.bu.identity.tracks.TracksScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/** Root state: has the loop been set up yet? Plus the pebble's remote hands. */
@HiltViewModel
class BuShellViewModel @Inject constructor(
    store: BuSettingsStore,
    private val quickActions: BuQuickActions,
    private val repo: PlanRepository,
) : ViewModel() {
    val settings = store.settings

    fun requestEditor(kind: PlanKind, day: Long) = quickActions.requestEditor(kind, day)

    fun createFirstTrack(name: String, colorToken: Int) {
        viewModelScope.launch { repo.createTrack(name, colorToken, TrackKind.AREA) }
    }
}

object BuRoutes {
    const val WELCOME = "welcome"
    const val MANIFEST = "manifest"
    const val SETUP = "setup"
    const val DAY = "day"
    const val TRACKS = "tracks"
    const val SPARKS = "sparks"
    const val SETTINGS = "settings"
}

@Composable
fun BuNavGraph() {
    val nav = rememberNavController()
    val shell: BuShellViewModel = hiltViewModel()
    val settings by shell.settings.collectAsStateWithLifecycle()
    var quickSheet by remember { mutableStateOf(false) }

    val backstack by nav.currentBackStackEntryAsState()
    val route = backstack?.destination?.route
    val pebbleRoutes = remember { setOf(BuRoutes.DAY, BuRoutes.TRACKS, BuRoutes.SPARKS) }

    // start destination respects setup state; when setup completes mid-session the
    // nav action from the wizard routes forward anyway (explicit, not reactive) —
    // so the start value is only read once, like a first-run flag.
    val start = if (settings.setupDone) BuRoutes.DAY else BuRoutes.WELCOME

    Box(Modifier.fillMaxSize().background(Bg0)) {
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
                    onFinish = { _, firstTrack ->
                        if (firstTrack != null) {
                            shell.createFirstTrack(firstTrack.name, firstTrack.colorToken)
                        }
                        nav.navigate(BuRoutes.DAY) {
                            popUpTo(BuRoutes.WELCOME) { inclusive = true }
                        }
                    },
                )
            }
            composable(BuRoutes.DAY) {
                DayArcScreen(
                    onOpenSparks = { nav.navigate(BuRoutes.SPARKS) },
                    onOpenTracks = { nav.navigate(BuRoutes.TRACKS) },
                    onOpenSettings = { nav.navigate(BuRoutes.SETTINGS) },
                )
            }
            composable(BuRoutes.TRACKS) { TracksScreen(onBack = { nav.popBackStack() }) }
            composable(BuRoutes.SPARKS) { SparksScreen(onBack = { nav.popBackStack() }) }
            composable(BuRoutes.SETTINGS) { SettingsScreen(onBack = { nav.popBackStack() }) }
        }

        // the pebble lives above the loop screens — never above onboarding or settings
        if (route in pebbleRoutes) {
            BuPebble(
                onQuickSpark = { quickSheet = true },
                onNewBlock = {
                    shell.requestEditor(PlanKind.BLOCK, BUTime.todayEpochDay())
                    nav.navigate(BuRoutes.DAY) {
                        popUpTo(BuRoutes.DAY) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onNewBig = {
                    shell.requestEditor(PlanKind.BIG, BUTime.todayEpochDay())
                    nav.navigate(BuRoutes.DAY) {
                        popUpTo(BuRoutes.DAY) { inclusive = false }
                        launchSingleTop = true
                    }
                },
            )
        }
    }

    if (quickSheet) {
        QuickCaptureSheet(onDismiss = { quickSheet = false })
    }
}

private const val BU_NAV_MS = 340
