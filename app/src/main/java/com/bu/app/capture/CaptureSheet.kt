package com.bu.app.capture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bu.core.command.BuCommand
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandReceipt
import com.bu.core.command.CommandStatus
import com.bu.core.outbox.OutboxProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val gateway: CommandGateway,
    private val outboxProcessor: OutboxProcessor,
) : ViewModel() {

    /**
     * Universal capture, two clocks (locked plan §5.5): the text is saved
     * through the kernel instantly; interpretation is an outbox effect
     * applied after commit (deterministic parser in P1, Needle in P6).
     */
    fun save(text: String, captureSource: String, onResult: (CommandReceipt) -> Unit) {
        viewModelScope.launch {
            val receipt = gateway.submit(
                BuCommand.SaveCapture(
                    text = text.trim(),
                    captureSource = captureSource,
                    idempotencyKey = UUID.randomUUID().toString(),
                ),
            )
            outboxProcessor.drain()
            onResult(receipt)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptureSheet(
    onDismiss: () -> Unit,
    onSaved: (String) -> Unit,
    viewModel: CaptureViewModel = hiltViewModel(),
    captureSource: String = "PEBBLE",
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Capture", style = MaterialTheme.typography.headlineSmall)
            Text(
                "A thought, task, worry, or spark — BU keeps it safe first, understands it after.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    error = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Type anything…") },
                minLines = 2,
                maxLines = 5,
                singleLine = false,
                isError = error != null,
                supportingText = { error?.let { Text(it) } },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onDismiss) { Text("Not now") }
                TextButton(
                    enabled = !saving && text.isNotBlank(),
                    onClick = {
                        saving = true
                        viewModel.save(text, captureSource) { receipt ->
                            saving = false
                            when (receipt.status) {
                                CommandStatus.COMMITTED ->
                                    onSaved("Captured · receipt ${receipt.commandId.take(8)}")
                                CommandStatus.REJECTED ->
                                    error = when (receipt.reason) {
                                        "EMPTY_CAPTURE" -> "Nothing to capture yet."
                                        "CAPTURE_TOO_LONG" -> "Too long for a spark — trim it a little."
                                        else -> "BU could not save that."
                                    }
                                else -> error = "BU could not save that."
                            }
                        }
                    },
                ) { Text(if (saving) "Saving…" else "Capture") }
            }
        }
    }
}
