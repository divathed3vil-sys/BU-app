package com.bu.identity.settings

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bu.core.model.BuSettings
import com.bu.core.settings.BuSettingsStore
import com.bu.core.time.BUTime
import com.bu.designsystem.component.BUCard
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintDeep
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * You · settings — the system's dials. Everything user-owned, nothing hidden.
 * Day boundaries, rituals, horizon, motion level. Guardian settings arrive in P4.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val store: BuSettingsStore,
) : androidx.lifecycle.ViewModel() {
    val settings = store.settings

    fun update(update: (BuSettings) -> BuSettings) = store.save(update)
}
@Composable
fun SettingsScreen(onBack: () -> Unit, vm: SettingsViewModel = hiltViewModel()) {
    val view = LocalView.current
    val s by vm.settings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg0)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.TextButton(onClick = { BUHaptic.tap(view); onBack() }) {
                Text("← back", color = InkLow)
            }
        }
        SectionLabel("you · settings")
        Spacer(Modifier.height(6.dp))
        Text("the dials of your system", style = MaterialTheme.typography.headlineMedium, color = InkHi)
        Spacer(Modifier.height(20.dp))

        BUCard(Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                SettingHeader("the day")
                Text(
                    "runs ${BUTime.fmtMin(s.dayStartMin)} → ${BUTime.fmtMin(s.dayEndMin)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkLow,
                )
                MiniSlider("starts", s.dayStartMin, 240f..600f) { v -> vm.update { it.copy(dayStartMin = v) } }
                MiniSlider("ends", s.dayEndMin, 1200f..1620f) { v -> vm.update { it.copy(dayEndMin = v) } }
                SettingHeader("rituals")
                MiniSlider("dawn push", s.dawnMin, 240f..720f) { v -> vm.update { it.copy(dawnMin = v) } }
                MiniSlider("dusk close", s.duskMin, 1140f..1560f) { v -> vm.update { it.copy(duskMin = v) } }
                MiniSlider("sunday review", s.sundayReviewMin, 480f..1020f) { v -> vm.update { it.copy(sundayReviewMin = v) } }
            }
        }
        Spacer(Modifier.height(14.dp))

        BUCard(Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SettingHeader("horizon")
                if (s.hasExam) {
                    Text(
                        "${s.examName} · ${BUTime.fmtDaysUntil(s.examDateEpochDay!!)} days",
                        style = MaterialTheme.typography.titleMedium,
                        color = com.bu.designsystem.theme.Gold,
                    )
                } else {
                    Text(
                        "no horizon set — add one anytime",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkLow,
                    )
                }
            }
        }
        Spacer(Modifier.height(14.dp))

        BUCard(Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SettingHeader("motion")
                Text(
                    "how much BU moves — calm · standard · full bounce",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkLow,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("calm", style = MaterialTheme.typography.labelSmall, color = InkFaint)
                    Slider(
                        value = s.motionLevel.toFloat(),
                        onValueChange = { v -> vm.update { it.copy(motionLevel = v.toInt()) } },
                        valueRange = 0f..2f,
                        steps = 1,
                        modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
                        colors = SliderDefaults.colors(thumbColor = Mint, activeTrackColor = MintDeep, inactiveTrackColor = Line),
                    )
                    Text("bounce", style = MaterialTheme.typography.labelSmall, color = InkFaint)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("haptic grammar", style = MaterialTheme.typography.bodyMedium, color = InkLow)
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = true,
                        onCheckedChange = { BUHaptic.tick(view) },
                        colors = SwitchDefaults.colors(checkedTrackColor = MintDeep, checkedThumbColor = Mint, uncheckedTrackColor = Line),
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))
        Text(
            "the day arc, tracks and sparks live on their own screens —\nthe guardian wakes in P4.",
            style = MaterialTheme.typography.bodyMedium,
            color = InkFaint,
        )
    }
}

@Composable
private fun SettingHeader(text: String) {
    SectionLabel(text)
}

@Composable
private fun MiniSlider(label: String, value: Int, range: ClosedFloatingPointRange<Float>, onChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = InkLow, modifier = Modifier.fillMaxWidth(0.28f))
        Slider(
            value = value.toFloat(),
            onValueChange = { onChange(it.toInt()) },
            valueRange = range,
            modifier = Modifier.weight(1f),
            colors = SliderDefaults.colors(thumbColor = Mint, activeTrackColor = MintDeep, inactiveTrackColor = Line),
        )
        Text(BUTime.fmtMin(value), style = MaterialTheme.typography.bodyMedium, color = InkHi, modifier = Modifier.padding(start = 8.dp))
    }
}
