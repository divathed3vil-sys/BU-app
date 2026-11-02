package com.bu.core.command

/**
 * Pure validation — deterministic Kotlin owns the Authority plane (locked plan §5.1).
 * No model output and no UI ever bypasses this.
 */
sealed interface Verdict {
    data class Commit(val notes: List<String> = emptyList()) : Verdict
    data class Reject(val reason: String) : Verdict
}

object Validators {

    fun validate(command: BuCommand): Verdict = when (command) {
        is BuCommand.SaveCapture -> validateCapture(command)
    }

    private fun validateCapture(command: BuCommand.SaveCapture): Verdict =
        if (command.text.isBlank()) Verdict.Reject("EMPTY_CAPTURE")
        else if (command.text.length > MAX_CAPTURE_LENGTH) Verdict.Reject("CAPTURE_TOO_LONG")
        else Verdict.Commit()

    const val MAX_CAPTURE_LENGTH = 2000
}
