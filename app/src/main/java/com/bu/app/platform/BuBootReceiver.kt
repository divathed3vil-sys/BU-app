package com.bu.app.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bu.core.command.ReminderStore
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.platform.ReminderScheduler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Boot re-arm (locked plan §10): AlarmManager never survives a reboot, but
 * SQLite does. On boot, BU rebuilds the alarm projection from the ledger —
 * the platform catches up to the truth, never the other way round.
 */
@AndroidEntryPoint
class BuBootReceiver : BroadcastReceiver() {

    @Inject lateinit var reminders: ReminderStore
    @Inject lateinit var scheduler: ReminderScheduler
    @Inject lateinit var outboxProcessor: OutboxProcessor

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val result = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Re-arm every reminder still pending in the ledger. Overdue
                // ones are not re-armed — they are already DUE in the ledger
                // and show calmly in PLAN; no notification spam after a boot.
                reminders.pending().forEach { reminder ->
                    runCatching { scheduler.schedule(reminder.reminderId, reminder.dueAtMs) }
                }
                outboxProcessor.drain()
            } catch (t: Throwable) {
                android.util.Log.w(TAG, "boot re-arm failed: ${t.message}")
            } finally {
                result.finish()
            }
        }
    }

    private companion object {
        const val TAG = "BuBoot"
    }
}
