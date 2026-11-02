package com.bu.core.time

import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * BU time math — everything is epochDay / minutes / epochMillis. Never strings in the core.
 * Local-time based (the user's day is the only day that matters).
 */
object BUTime {

    fun todayEpochDay(): Long = Calendar.getInstance().let {
        java.time.LocalDate.of(it.get(Calendar.YEAR), it.get(Calendar.MONTH) + 1, it.get(Calendar.DAY_OF_MONTH)).toEpochDay()
    }

    fun epochDayFor(daysFromToday: Long): Long = todayEpochDay() + daysFromToday

    fun minutesOfDayNow(): Int = Calendar.getInstance().let { it.get(Calendar.HOUR_OF_DAY) * 60 + it.get(Calendar.MINUTE) }

    fun fmtMin(min: Int): String {
        val h = (min / 60).mod(24)
        val m = min % 60
        val amPm = if (min >= 12 * 60) "PM" else "AM"
        val h12 = if (h % 12 == 0) 12 else h % 12
        return String.format(Locale.US, "%d:%02d %s", h12, m, amPm)
    }

    fun fmtDaysUntil(epochDay: Long): Long = epochDay - todayEpochDay()

    fun localDateOf(epochDay: Long): java.time.LocalDate = java.time.LocalDate.ofEpochDay(epochDay)

    fun zone(): TimeZone = TimeZone.getDefault()
}
