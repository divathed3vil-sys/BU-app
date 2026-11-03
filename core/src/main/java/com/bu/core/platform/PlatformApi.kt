package com.bu.core.platform

/**
 * Platform adapters report and deliver; they never own truth (locked plan §5.1
 * plane 5). The ledger decides; these interfaces project the decision into
 * Android — alarms, widgets — and report back only their own status fields.
 */
interface ReminderScheduler {

    /**
     * Arms the platform alarm for a reminder. Idempotent per reminderId.
     * Returns the honesty flag: exact only when the OS allows it — an inexact
     * window is described as inexact everywhere BU shows it (locked plan §10).
     */
    suspend fun schedule(reminderId: String, dueAtMs: Long): Boolean

    /** Disarms any pending alarm for the reminder. Idempotent. */
    suspend fun cancel(reminderId: String)
}

/** Repaints the home-screen NOW widget from the ledger. */
interface WidgetRefresher {
    suspend fun refresh()
}
