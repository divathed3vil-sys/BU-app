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
        is BuCommand.InterpretCapture -> validateInterpret(command)
        is BuCommand.PromoteCapture ->
            if (command.captureId.isBlank()) Verdict.Reject("UNKNOWN_CAPTURE") else Verdict.Commit()
        is BuCommand.CreateTrack -> validateCreateTrack(command)
        is BuCommand.UpdateTrack -> validateUpdateTrack(command)
        is BuCommand.ArchiveTrack ->
            if (command.trackId.isBlank()) Verdict.Reject("UNKNOWN_TRACK") else Verdict.Commit()
        is BuCommand.CreateTask -> validateCreateTask(command)
        is BuCommand.TallyTask -> validateTally(command)
        is BuCommand.SetBigThree -> validateBigThree(command)
        is BuCommand.CreateReminder -> validateReminder(command)
        is BuCommand.CompleteReminder, is BuCommand.CancelReminder ->
            refIsBlank(command) ?: Verdict.Commit()
        is BuCommand.SnoozeReminder -> validateSnooze(command)
        is BuCommand.StartFocus -> validateStartFocus(command)
        is BuCommand.EndFocus ->
            if (command.sessionId.isBlank()) Verdict.Reject("UNKNOWN_FOCUS_SESSION") else Verdict.Commit()
        is BuCommand.SetDayWindow -> validateDayWindow(command)
        is BuCommand.MarkOnboarded -> Verdict.Commit()
    }

    private fun refIsBlank(command: BuCommand): Verdict? {
        val id = when (command) {
            is BuCommand.CompleteReminder -> command.reminderId
            is BuCommand.CancelReminder -> command.reminderId
            else -> return null
        }
        return if (id.isBlank()) Verdict.Reject("UNKNOWN_REMINDER") else null
    }

    private fun validateCapture(command: BuCommand.SaveCapture): Verdict =
        if (command.text.isBlank()) Verdict.Reject("EMPTY_CAPTURE")
        else if (command.text.length > MAX_CAPTURE_LENGTH) Verdict.Reject("CAPTURE_TOO_LONG")
        else Verdict.Commit()

    private fun validateInterpret(command: BuCommand.InterpretCapture): Verdict =
        if (command.captureId.isBlank()) Verdict.Reject("UNKNOWN_CAPTURE")
        else if (command.title.isBlank()) Verdict.Reject("EMPTY_TITLE")
        else Verdict.Commit()

    private fun validateCreateTrack(command: BuCommand.CreateTrack): Verdict =
        if (command.name.isBlank()) Verdict.Reject("EMPTY_TITLE")
        else if (command.name.length > MAX_NAME_LENGTH) Verdict.Reject("TITLE_TOO_LONG")
        else if (command.estimateMinutes !in MIN_ESTIMATE_MINUTES..MAX_TRACK_ESTIMATE_MINUTES) {
            Verdict.Reject("ESTIMATE_OUT_OF_RANGE")
        } else Verdict.Commit()

    private fun validateUpdateTrack(command: BuCommand.UpdateTrack): Verdict =
        if (command.trackId.isBlank()) Verdict.Reject("UNKNOWN_TRACK")
        else if (command.name != null && (command.name.isBlank() || command.name.length > MAX_NAME_LENGTH)) {
            Verdict.Reject("EMPTY_TITLE")
        } else if (command.estimateMinutes != null &&
            command.estimateMinutes !in MIN_ESTIMATE_MINUTES..MAX_TRACK_ESTIMATE_MINUTES
        ) {
            Verdict.Reject("ESTIMATE_OUT_OF_RANGE")
        } else Verdict.Commit()

    private fun validateCreateTask(command: BuCommand.CreateTask): Verdict {
        if (command.title.isBlank()) return Verdict.Reject("EMPTY_TITLE")
        if (command.title.length > MAX_TITLE_LENGTH) return Verdict.Reject("TITLE_TOO_LONG")
        if (command.taskClass !in TASK_CLASSES) return Verdict.Reject("UNKNOWN_TASK_CLASS")

        when (command.taskClass) {
            // A block is a time-box: it needs a day, a start and a snapped duration.
            "BLOCK" -> {
                if (command.scheduledDateIso.isNullOrBlank()) return Verdict.Reject("BLOCK_NEEDS_A_DAY")
                val start = command.startMinute
                    ?: return Verdict.Reject("BLOCK_NEEDS_A_START")
                val duration = command.durationMinutes
                    ?: return Verdict.Reject("BLOCK_NEEDS_A_DURATION")
                if (start !in 0..(24 * 60 - 1)) return Verdict.Reject("BLOCK_START_OUT_OF_DAY")
                if (duration < MIN_BLOCK_DURATION || duration > MAX_BLOCK_DURATION) {
                    return Verdict.Reject("BLOCK_DURATION_OUT_OF_RANGE")
                }
                if (duration % 15 != 0 || start % 15 != 0) return Verdict.Reject("TIME_NOT_SNAPPED")
                if (start + duration > 24 * 60) return Verdict.Reject("BLOCK_RUNS_PAST_MIDNIGHT")
            }
            // A Big Three slot belongs to one day.
            "BIG" -> if (command.scheduledDateIso.isNullOrBlank()) return Verdict.Reject("BIG_NEEDS_A_DAY")
        }
        return Verdict.Commit()
    }

    private fun validateTally(command: BuCommand.TallyTask): Verdict {
        if (command.taskId.isBlank()) return Verdict.Reject("UNKNOWN_TASK")
        if (command.tally !in TALLIES) return Verdict.Reject("UNKNOWN_TALLY")
        if (command.tally == "CARRIED" && command.toDateIso.isNullOrBlank()) {
            return Verdict.Reject("CARRY_NEEDS_A_DATE")
        }
        return Verdict.Commit()
    }

    /**
     * The Big Three law (locked plan §2): ≤3 slots, exactly one Main Front,
     * no empty titles. Enforced here so no screen can violate it.
     */
    private fun validateBigThree(command: BuCommand.SetBigThree): Verdict {
        if (command.dateIso.isBlank()) return Verdict.Reject("BIG_NEEDS_A_DAY")
        if (command.slots.isEmpty() || command.slots.size > 3) return Verdict.Reject("MAX_THREE")
        val fronts = command.slots.count { it.isMainFront }
        if (fronts != 1) return Verdict.Reject("EXACTLY_ONE_MAIN_FRONT")
        if (command.slots.any { it.title.isBlank() || it.title.length > MAX_TITLE_LENGTH }) {
            return Verdict.Reject("EMPTY_TITLE")
        }
        command.slots.forEach { slot ->
            val start = slot.startMinute ?: return@forEach
            if (start !in 0..(24 * 60 - 1) || start % 15 != 0) return Verdict.Reject("TIME_NOT_SNAPPED")
        }
        return Verdict.Commit()
    }

    private fun validateReminder(command: BuCommand.CreateReminder): Verdict =
        if (command.title.isBlank()) Verdict.Reject("EMPTY_TITLE")
        else if (command.title.length > MAX_TITLE_LENGTH) Verdict.Reject("TITLE_TOO_LONG")
        else if (command.dueAtMs <= 0) Verdict.Reject("REMINDER_NEEDS_A_TIME")
        else Verdict.Commit()

    private fun validateSnooze(command: BuCommand.SnoozeReminder): Verdict =
        if (command.reminderId.isBlank()) Verdict.Reject("UNKNOWN_REMINDER")
        else if (command.minutes !in MIN_SNOOZE_MINUTES..MAX_SNOOZE_MINUTES) {
            Verdict.Reject("SNOOZE_OUT_OF_RANGE")
        } else Verdict.Commit()

    private fun validateStartFocus(command: BuCommand.StartFocus): Verdict =
        if (command.plannedMinutes !in 0..MAX_FOCUS_MINUTES) Verdict.Reject("FOCUS_OUT_OF_RANGE")
        else Verdict.Commit()

    private fun validateDayWindow(command: BuCommand.SetDayWindow): Verdict =
        if (command.startMinute < 0 || command.startMinute >= 24 * 60) Verdict.Reject("WINDOW_OUT_OF_DAY")
        else if (command.endMinute <= 0 || command.endMinute > 24 * 60) Verdict.Reject("WINDOW_OUT_OF_DAY")
        else if (command.endMinute - command.startMinute < MIN_WINDOW_MINUTES) {
            Verdict.Reject("WINDOW_TOO_SHORT")
        } else Verdict.Commit()

    const val MAX_CAPTURE_LENGTH = 2000
    const val MAX_TITLE_LENGTH = 200
    const val MAX_NAME_LENGTH = 80
    const val MIN_ESTIMATE_MINUTES = 5
    const val MAX_TRACK_ESTIMATE_MINUTES = 600
    const val MIN_BLOCK_DURATION = 15
    const val MAX_BLOCK_DURATION = 480
    const val MIN_SNOOZE_MINUTES = 1
    const val MAX_SNOOZE_MINUTES = 240
    const val MAX_FOCUS_MINUTES = 240
    const val MIN_WINDOW_MINUTES = 240 // a day shorter than 4 h is not a day

    val TASK_CLASSES = setOf("BIG", "BLOCK", "SPARK", "EXAM")
    val TALLIES = setOf("DONE", "PARTIAL", "CARRIED")
}
