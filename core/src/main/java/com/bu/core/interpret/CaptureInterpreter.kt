package com.bu.core.interpret

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

/**
 * The deterministic capture interpreter (locked plan §5.5, P1 slice).
 *
 * Two-clock capture: the raw text is already safely in the ledger when this
 * runs (outbox effect CAPTURE_INTERPRET). Here, pure Kotlin decides whether a
 * capture is a task, and whether a time is confident enough to schedule.
 *
 * Laws honored here:
 *  - The model never touches this path until P6, and even then only proposes.
 *  - Ambiguity is honest: an unresolvable time ("at 5") simply leaves the task
 *    unscheduled — the raw text is never rewritten, never lost.
 *  - A date without a confident time lands the task on that day, timeless.
 *  - Arithmetic is always deterministic Kotlin. Never a model.
 */
class CaptureInterpreter {

    enum class Kind { SPARK, TASK }

    data class Interpretation(
        val kind: Kind,
        val title: String,
        val scheduledDateIso: String? = null,
        val startMinute: Int? = null,
        val scheduledAtMs: Long? = null,
    )

    fun interpret(raw: String, now: LocalDateTime, zone: ZoneId): Interpretation {
        val text = raw.trim()
        if (!looksLikeTask(text)) {
            return Interpretation(kind = Kind.SPARK, title = text)
        }

        var dateAnchor: DayAnchor = DayAnchor.NONE
        var time: LocalTime? = null
        var relative: LocalDateTime? = null
        var matchedTimeSpan: IntRange? = null

        // "in 20 min / 2 hours" — relative, always confident.
        RELATIVE.find(text)?.let { m ->
            val amount = m.groupValues[1].toIntOrNull() ?: return@let
            val unit = m.groupValues[2].lowercase()
            val deltaMs = if (unit.startsWith("h")) amount * 3_600_000L else amount * 60_000L
            relative = now.plusNanos(deltaMs * 1_000_000)
            matchedTimeSpan = m.range
        }

        if (relative == null) {
            // Explicit clock time: "at 5pm", "at 17:30", "at 9".
            CLOCK_TIME.find(text)?.let { m ->
                val hour = m.groupValues[1].toIntOrNull() ?: return@let
                val minute = m.groupValues[2].toIntOrNull() ?: 0
                val meridiem = m.groupValues[3].lowercase()
                val resolvedHour = when {
                    meridiem == "am" && hour == 12 -> 0
                    meridiem == "am" -> hour
                    meridiem == "pm" && hour == 12 -> 12
                    meridiem == "pm" -> hour + 12
                    hour in 12..23 -> hour
                    hour in 8..11 -> hour           // plausibly morning
                    else -> return@let              // "at 5" — honest ambiguity, no guess
                }
                if (minute in 0..59) {
                    time = LocalTime.of(resolvedHour, minute)
                    matchedTimeSpan = m.range
                }
            }

            if (time == null) {
                // "17:30" bare 24-hour.
                BARE_24H.find(text)?.let { m ->
                    val hour = m.groupValues[1].toIntOrNull() ?: return@let
                    val minute = m.groupValues[2].toIntOrNull() ?: return@let
                    if (hour in 0..23 && minute in 0..59) {
                        time = LocalTime.of(hour, minute)
                        matchedTimeSpan = m.range
                    }
                }
            }
        }

        if (relative == null) {
            when {
                TONIGHT.containsMatchIn(text) -> {
                    dateAnchor = DayAnchor.TODAY
                    if (time == null) time = LocalTime.of(20, 0)
                    if (matchedTimeSpan == null) matchedTimeSpan = TONIGHT.find(text)?.range
                }
                TOMORROW.containsMatchIn(text) -> {
                    dateAnchor = DayAnchor.TOMORROW
                    if (matchedTimeSpan == null) matchedTimeSpan = TOMORROW.find(text)?.range
                }
                TODAY.containsMatchIn(text) -> {
                    dateAnchor = DayAnchor.TODAY
                    if (matchedTimeSpan == null) matchedTimeSpan = TODAY.find(text)?.range
                }
                else -> WEEKDAY.find(text)?.let { m ->
                    val dow = weekdayOf(m.value) ?: return@let
                    dateAnchor = DayAnchor.WEEKDAY(dow)
                    if (matchedTimeSpan == null) matchedTimeSpan = m.range
                }
            }
        }

        // Resolve anchor + time into a concrete local date and time.
        val resolvedDate: LocalDate?
        val resolvedTime: LocalTime?
        when {
            relative != null -> {
                resolvedDate = relative!!.toLocalDate()
                resolvedTime = relative!!.toLocalTime()
            }
            dateAnchor is DayAnchor.WEEKDAY -> {
                val target = (dateAnchor as DayAnchor.WEEKDAY).day
                var candidate = now.toLocalDate().with(TemporalAdjusters.nextOrSame(target))
                if (candidate == now.toLocalDate()) {
                    val sameDay = time?.let { now.toLocalDate().atTime(it) }
                    if (sameDay == null || !sameDay.isAfter(now)) candidate = candidate.plusWeeks(1)
                }
                resolvedDate = candidate
                resolvedTime = time
            }
            dateAnchor == DayAnchor.TOMORROW -> {
                resolvedDate = now.toLocalDate().plusDays(1)
                resolvedTime = time
            }
            dateAnchor == DayAnchor.TODAY -> {
                resolvedDate = now.toLocalDate()
                resolvedTime = time
            }
            else -> {
                if (time != null) {
                    val candidate = now.toLocalDate().atTime(time)
                    resolvedDate = if (candidate.isAfter(now)) candidate.toLocalDate() else candidate.toLocalDate().plusDays(1)
                    resolvedTime = time
                } else {
                    resolvedDate = null
                    resolvedTime = null
                }
            }
        }

        // Title: strip the matched time phrase when it resolved, and strip
        // intent prefixes ("remember to …"). The raw text is never altered.
        var title = text
        matchedTimeSpan?.let { range ->
            if (range.first >= 0 && range.last < title.length) {
                title = title.take(range.first) + title.drop(range.last + 1)
            }
        }
        title = INTENT_PREFIX.replace(title, " ").trim(' ', ',', '-', ':', '·')
        while ("  " in title) title = title.replace("  ", " ")

        return Interpretation(
            kind = Kind.TASK,
            title = title.ifBlank { text },
            scheduledDateIso = resolvedDate?.toString(),
            startMinute = resolvedTime?.let { it.hour * 60 + it.minute },
            scheduledAtMs = if (resolvedDate != null && resolvedTime != null) {
                resolvedDate.atTime(resolvedTime).atZone(zone).toInstant().toEpochMilli()
            } else {
                null
            },
        )
    }

    private fun looksLikeTask(text: String): Boolean {
        val lower = text.lowercase()
        if (TASK_PATTERNS.any { it.containsMatchIn(lower) }) return true
        return VERBS.any { verb -> Regex("\\b$verb\\b").containsMatchIn(lower) }
    }

    private fun weekdayOf(word: String): DayOfWeek? = when (word.lowercase()) {
        "mon", "monday" -> DayOfWeek.MONDAY
        "tue", "tues", "tuesday" -> DayOfWeek.TUESDAY
        "wed", "weds", "wednesday" -> DayOfWeek.WEDNESDAY
        "thu", "thur", "thurs", "thursday" -> DayOfWeek.THURSDAY
        "fri", "friday" -> DayOfWeek.FRIDAY
        "sat", "saturday" -> DayOfWeek.SATURDAY
        "sun", "sunday" -> DayOfWeek.SUNDAY
        else -> null
    }

    private sealed interface DayAnchor {
        data object NONE : DayAnchor
        data object TODAY : DayAnchor
        data object TOMORROW : DayAnchor
        data class WEEKDAY(val day: DayOfWeek) : DayAnchor
    }

    private companion object {
        val VERBS = listOf(
            "call", "text", "message", "msg", "email", "send", "reply", "buy", "get", "grab",
            "pick", "order", "book", "pay", "renew", "cancel", "submit", "finish", "complete",
            "study", "revise", "read", "write", "draft", "fix", "clean", "wash", "tidy",
            "organise", "organize", "prepare", "prep", "apply", "register", "sign", "collect",
            "drop", "return", "install", "update", "backup", "schedule", "research", "check", "ask",
        )
        val TASK_PATTERNS = listOf(
            Regex("\\bremember to\\b"),
            Regex("\\bneed to\\b"),
            Regex("\\bhave to\\b"),
            Regex("\\bmust\\b"),
            Regex("\\bdon'?t forget (to|about)\\b"),
            Regex("\\bgotta\\b"),
            Regex("\\btodo:?\\b"),
            Regex("\\bto-do:?\\b"),
            Regex("\\btask:?\\b"),
            Regex("\\bremind me to\\b"),
        )
        val INTENT_PREFIX = Regex(
            "^(hey\\s+)?(remember to|need to|have to|must|don'?t forget to|don'?t forget about|gotta|remind me to|todo:|to-do:|task:)\\s*",
            RegexOption.IGNORE_CASE,
        )
        val RELATIVE = Regex("\\bin\\s+(\\d{1,3})\\s*(min|mins|minute|minutes|h|hr|hrs|hour|hours)\\b", RegexOption.IGNORE_CASE)
        val CLOCK_TIME = Regex("\\bat\\s+(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)?\\b", RegexOption.IGNORE_CASE)
        val BARE_24H = Regex("\\b(\\d{1,2}):(\\d{2})\\b")
        val TODAY = Regex("\\btoday\\b", RegexOption.IGNORE_CASE)
        val TONIGHT = Regex("\\btonight\\b", RegexOption.IGNORE_CASE)
        val TOMORROW = Regex("\\b(tomorrow|tmrw|tmr)\\b", RegexOption.IGNORE_CASE)
        val WEEKDAY = Regex(
            "\\b(mon|monday|tue|tues|tuesday|wed|weds|wednesday|thu|thur|thurs|thursday|fri|friday|sat|saturday|sun|sunday)\\b",
            RegexOption.IGNORE_CASE,
        )
    }
}
