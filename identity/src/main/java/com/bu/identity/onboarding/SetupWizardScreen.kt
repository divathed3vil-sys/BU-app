package com.bu.identity.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bu.core.model.BuSettings
import com.bu.core.time.BUTime
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.Bg0
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import com.bu.designsystem.theme.MintDeep
import com.bu.designsystem.theme.TrackColors

/**
 * Setup wizard — nothing pre-made. The user builds their own system:
 * name → day boundaries → first track → ritual times → exam (optional) → done.
 * Six steps, each one question. Springs between steps, haptic on every answer.
 */
@Composable
fun SetupWizardScreen(onFinish: (BuSettings) -> Unit) {
    val view = LocalView.current
    var step by remember { mutableStateOf(0) }
    var username by remember { mutableStateOf("") }
    var dayStart by remember { mutableStateOf(5 * 60) }
    var dayEnd by remember { mutableStateOf(23 * 60) }
    var trackName by remember { mutableStateOf("") }
    var trackColor by remember { mutableStateOf(0) }
    var dawnMin by remember { mutableStateOf(6 * 60) }
    var duskMin by remember { mutableStateOf(21 * 60 + 30) }
    var examName by remember { mutableStateOf("") }
    var examInDays by remember { mutableStateOf(30f) }

    fun next() {
        BUHaptic.tick(view)
        step++
    }

    val canAdvance = when (step) {
        0 -> username.isNotBlank()
        2 -> trackName.isNotBlank()
        else -> true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg0)
            .padding(horizontal = 24.dp, vertical = 40.dp),
    ) {
        // step dots
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            repeat(6) { i ->
                Box(
                    modifier = Modifier
                        .size(if (i == step) 18.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (i == step) Mint else InkFaint),
                )
            }
        }
        Spacer(Modifier.height(28.dp))

        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally(tween(360)) { it / 3 } + fadeIn(tween(300))) togetherWith
                        (slideOutHorizontally(tween(300)) { -it / 4 } + fadeOut(tween(200)))
                } else {
                    (slideInHorizontally(tween(360)) { -it / 3 } + fadeIn(tween(300))) togetherWith
                        (slideOutHorizontally(tween(300)) { it / 4 } + fadeOut(tween(200)))
                }
            },
            label = "wizardStep",
        ) { s ->
            Column {
                when (s) {
                    0 -> {
                        SectionLabel("first, a name")
                        Spacer(Modifier.height(8.dp))
                        Text("who is this system for?", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(22.dp))
                        BUField(value = username, onValueChange = { username = it }, placeholder = "your name")
                    }
                    1 -> {
                        SectionLabel("your day")
                        Spacer(Modifier.height(8.dp))
                        Text("when does a day start\nand end for you?", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(22.dp))
                        TimeSlider(label = "day starts", value = dayStart, range = 240f..600f, onChange = { dayStart = it })
                        Spacer(Modifier.height(14.dp))
                        TimeSlider(label = "day ends", value = dayEnd, range = 1200f..1620f, onChange = { dayEnd = it })
                    }
                    2 -> {
                        SectionLabel("first track — yours, not ours")
                        Spacer(Modifier.height(8.dp))
                        Text("what matters enough\nto track first?", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "a subject, a habit, a person, a project. BU ships zero tracks — ever.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkLow,
                        )
                        Spacer(Modifier.height(18.dp))
                        BUField(value = trackName, onValueChange = { trackName = it }, placeholder = "e.g. Physics · Reading · Gym")
                        Spacer(Modifier.height(18.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            TrackColors.forEachIndexed { i, c ->
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(c)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null,
                                        ) { BUHaptic.tick(view); trackColor = i },
                                )
                            }
                        }
                    }
                    3 -> {
                        SectionLabel("the two rituals")
                        Spacer(Modifier.height(8.dp))
                        Text("when should BU\ncome to you?", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Dawn Push opens the day. Dusk Close ends it — 90 seconds, tops.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkLow,
                        )
                        Spacer(Modifier.height(18.dp))
                        TimeSlider(label = "dawn push", value = dawnMin, range = 240f..720f, onChange = { dawnMin = it })
                        Spacer(Modifier.height(14.dp))
                        TimeSlider(label = "dusk close", value = duskMin, range = 1140f..1560f, onChange = { duskMin = it })
                    }
                    4 -> {
                        SectionLabel("the horizon (optional)")
                        Spacer(Modifier.height(8.dp))
                        Text("an exam, a deadline,\na date that matters?", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(18.dp))
                        BUField(value = examName, onValueChange = { examName = it }, placeholder = "e.g. ACS — fully customizable")
                        Spacer(Modifier.height(22.dp))
                        TimeSlider(
                            label = "in days",
                            value = examInDays.toInt(),
                            range = 7f..365f,
                            onChange = { examInDays = it },
                            format = { "$it days" },
                        )
                        Text(
                            "leave the name empty to skip. the countdown never shames — it only glows.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkFaint,
                        )
                    }
                    5 -> {
                        SectionLabel("done")
                        Spacer(Modifier.height(8.dp))
                        Text("the loop is yours,\n${username.ifBlank { "friend" }}.", style = MaterialTheme.typography.headlineMedium, color = InkHi)
                        Spacer(Modifier.height(16.dp))
                        WizardSummary(username, dayStart, dayEnd, trackName, TrackColors[trackColor], dawnMin, duskMin, examName, examInDays.toInt())
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            if (step > 0) {
                androidx.compose.material3.TextButton(onClick = { BUHaptic.tap(view); step-- }) {
                    Text("back", color = InkLow)
                }
            }
            Box(Modifier.weight(1f))
            MintButton(
                text = if (step == 5) "enter the loop" else "next",
                enabled = canAdvance,
                onClick = {
                    if (step == 5) {
                        BUHaptic.win(view)
                        onFinish(
                            BuSettings(
                                username = username.trim(),
                                dayStartMin = dayStart,
                                dayEndMin = dayEnd,
                                dawnMin = dawnMin,
                                duskMin = duskMin,
                                examName = examName.trim(),
                                examDateEpochDay = if (examName.isNotBlank()) BUTime.epochDayFor(examInDays.toLong()) else null,
                                setupDone = true,
                            ),
                        )
                    } else {
                        next()
                    }
                },
                modifier = Modifier.width(150.dp),
            )
        }
    }
}

@Composable
private fun BUField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = InkFaint) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Mint,
            unfocusedBorderColor = Line,
            focusedTextColor = InkHi,
            unfocusedTextColor = InkHi,
            cursorColor = Mint,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun TimeSlider(
    label: String,
    value: Int,
    range: ClosedFloatingPointRange<Float>,
    onChange: (Int) -> Unit,
    format: (Int) -> String = BUTime::fmtMin,
) {
    Text(
        "$label  ·  ${format(value)}",
        style = MaterialTheme.typography.titleMedium,
        color = InkHi,
        textAlign = TextAlign.Start,
    )
    Slider(
        value = value.toFloat(),
        onValueChange = { onChange(it.toInt()) },
        valueRange = range,
        colors = SliderDefaults.colors(
            thumbColor = Mint,
            activeTrackColor = MintDeep,
            inactiveTrackColor = Line,
        ),
    )
}

@Composable
private fun WizardSummary(
    username: String,
    dayStart: Int,
    dayEnd: Int,
    track: String,
    trackColor: androidx.compose.ui.graphics.Color,
    dawn: Int,
    dusk: Int,
    exam: String,
    examDays: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(com.bu.designsystem.theme.Layer)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SummaryRow("name", username)
        SummaryRow("day", "${BUTime.fmtMin(dayStart)} → ${BUTime.fmtMin(dayEnd)}")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(10.dp).clip(CircleShape).background(trackColor))
            Spacer(Modifier.size(10.dp))
            Text("track", style = MaterialTheme.typography.bodyMedium, color = InkLow)
            Spacer(Modifier.weight(1f))
            Text(track, style = MaterialTheme.typography.titleMedium, color = InkHi, textAlign = TextAlign.End)
        }
        SummaryRow("dawn / dusk", "${BUTime.fmtMin(dawn)} · ${BUTime.fmtMin(dusk)}")
        if (exam.isNotBlank()) SummaryRow("horizon", "$exam · ${examDays}d")
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = InkLow)
        Spacer(Modifier.weight(1f))
        Text(value, style = MaterialTheme.typography.titleMedium, color = InkHi, textAlign = TextAlign.End)
    }
}
