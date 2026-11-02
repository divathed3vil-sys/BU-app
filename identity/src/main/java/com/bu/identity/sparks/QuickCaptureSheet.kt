package com.bu.identity.sparks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bu.designsystem.component.MintButton
import com.bu.designsystem.component.SectionLabel
import com.bu.designsystem.motion.BUHaptic
import com.bu.designsystem.theme.InkFaint
import com.bu.designsystem.theme.InkHi
import com.bu.designsystem.theme.InkLow
import com.bu.designsystem.theme.Layer
import com.bu.designsystem.theme.Line
import com.bu.designsystem.theme.Mint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * The pebble's quick-capture sheet — one line, straight into the inbox.
 * Deliberately tiny: the full shaping happens on the sparks screen.
 */
@HiltViewModel
class QuickCaptureViewModel @Inject constructor(
    private val repo: com.bu.identity.data.PlanRepository,
) : ViewModel() {

    fun capture(raw: String, onDone: () -> Unit) {
        viewModelScope.launch {
            if (raw.isNotBlank()) repo.quickCapture(raw)
            onDone()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickCaptureSheet(onDismiss: () -> Unit, vm: QuickCaptureViewModel = hiltViewModel()) {
    val view = LocalView.current
    var text by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Layer,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            SectionLabel("quick spark")
            Text("one line. shape it later.", style = MaterialTheme.typography.bodyMedium, color = InkLow)
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("what just sparked?", color = InkFaint) },
                minLines = 2,
                maxLines = 4,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "lands in the spark inbox",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkFaint,
                    modifier = Modifier.weight(1f),
                )
                MintButton(
                    text = "capture",
                    enabled = text.isNotBlank(),
                    onClick = {
                        BUHaptic.lock(view)
                        vm.capture(text) { onDismiss() }
                    },
                )
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}
