package com.bu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.bu.app.navigation.BuRoot
import com.bu.app.onboarding.OnboardingFlow
import com.bu.core.command.BtkGateway
import com.bu.core.ledger.SettingsDao
import com.bu.designsystem.theme.BuBackground
import com.bu.designsystem.theme.BuTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/** Holds the single gate: onboarded or not, read from the ledger (I2). */
@HiltViewModel
class BuGateViewModel @Inject constructor(
    settingsDao: SettingsDao,
) : ViewModel() {

    private val reload = MutableStateFlow(Unit)

    val onboarded: StateFlow<Boolean?> = reload
        .map { settingsDao.get(BtkGateway.SETTING_ONBOARDED)?.value == "1" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun recheck() {
        reload.value = Unit
    }
}

/**
 * The single Activity. Everything else is Compose state over the ledger.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val gateViewModel: BuGateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuTheme {
                val onboarded by gateViewModel.onboarded.collectAsStateWithLifecycle()
                when (onboarded) {
                    null -> Box(
                        Modifier
                            .fillMaxSize()
                            .background(BuBackground),
                    )

                    true -> BuRoot()
                    else -> OnboardingFlow(onDone = { gateViewModel.recheck() })
                }
            }
        }
    }
}
