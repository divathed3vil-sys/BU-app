package com.bu.app.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bu.core.command.ReminderStore
import com.bu.core.ledger.NotificationLogDao
import com.bu.core.ledger.NotificationLogRow
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Fires when a care alarm rings (the platform reporting, never deciding):
 * marks the reminder DUE in the ledger, logs the delivery, posts the
 * notification. Tap → the app opens; the tally completes the round-trip.
 */
@AndroidEntryPoint
class CareReceiver : BroadcastReceiver() {

    @Inject lateinit var reminderStore: ReminderStore
    @Inject lateinit var notificationLogDao: NotificationLogDao
    @Inject lateinit var clock: BuClock
    @Inject lateinit var outboxProcessor: OutboxProcessor

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getStringExtra(EXTRA_REMINDER_ID) ?: return
        val result = goAsync()
        val appContext = context.applicationContext

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val now = clock.nowMs()
                val reminder = reminderStore.byId(reminderId)
                if (reminder != null && reminder.status == "PENDING") {
                    reminderStore.setStatus(reminderId, "DUE", now)
                    notificationLogDao.insert(
                        NotificationLogRow(
                            logId = UUID.randomUUID().toString(),
                            reminderId = reminderId,
                            sentAtMs = now,
                            kind = "CARE_DUE",
                        ),
                    )
                    outboxProcessor.drain()
                }
                if (reminder != null && BuNotifications.canPostNotifications(appContext)) {
                    val manager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                    manager.notify(
                        BuNotifications.CARE_NOTIFICATION_ID_BASE + reminderId.hashCode(),
                        BuNotifications.careNotification(appContext, reminderId, reminder.title),
                    )
                }
            } catch (t: Throwable) {
                android.util.Log.w(TAG, "care delivery failed: ${t.message}")
            } finally {
                result.finish()
            }
        }
    }

    companion object {
        private const val TAG = "BuCare"
        const val ACTION_CARE_DUE = "com.bu.app.action.CARE_DUE"
        const val EXTRA_REMINDER_ID = "reminder_id"
    }
}
