package com.bu.app.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bu.core.command.BtkGateway
import com.bu.core.command.ReminderStore
import com.bu.core.ledger.NotificationLogDao
import com.bu.core.ledger.NotificationLogRow
import com.bu.core.ledger.SettingsDao
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import com.bu.identity.ritual.RitualHealth
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Fires when a care alarm rings (the platform reporting, never deciding).
 *
 * Delivery path (locked plan §9 — the interrupt budget):
 *  · under budget → the interruptive care notification, with its Why-now line;
 *  · over budget → the item still lands DUE in the ledger and shows in PLAN,
 *    and one quiet digest per day mentions the waiting items. Nothing is lost;
 *    the budget bounds interruptions, never knowledge.
 */
@AndroidEntryPoint
class CareReceiver : BroadcastReceiver() {

    @Inject lateinit var reminderStore: ReminderStore
    @Inject lateinit var notificationLogDao: NotificationLogDao
    @Inject lateinit var settingsDao: SettingsDao
    @Inject lateinit var clock: BuClock
    @Inject lateinit var outboxProcessor: OutboxProcessor

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getStringExtra(EXTRA_REMINDER_ID) ?: return
        val result = goAsync()
        val appContext = context.applicationContext

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val now = clock.nowMs()
                val zone = ZoneId.systemDefault()
                val midnight = LocalDate.now(zone).atStartOfDay(zone).toInstant().toEpochMilli()
                val reminder = reminderStore.byId(reminderId)

                if (reminder != null && reminder.status == "PENDING") {
                    reminderStore.setStatus(reminderId, "DUE", now)

                    val budget = settingsDao.get(BtkGateway.SETTING_NOTIF_BUDGET)?.value?.toIntOrNull()
                        ?: RitualHealth.NotificationBudget.DEFAULT_BUDGET
                    val deliveredToday = notificationLogDao.countSince(KIND_CARE_DUE, midnight)
                    val decision = RitualHealth.NotificationBudget.decide(deliveredToday, budget)

                    if (BuNotifications.canPostNotifications(appContext)) {
                        val manager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
                        if (decision == RitualHealth.NotificationBudget.Decision.INTERRUPT) {
                            notificationLogDao.insert(
                                NotificationLogRow(
                                    logId = UUID.randomUUID().toString(),
                                    reminderId = reminderId,
                                    sentAtMs = now,
                                    kind = KIND_CARE_DUE,
                                ),
                            )
                            val why = RitualHealth.whyNow(
                                reminder.careType,
                                reminder.dueAtMs,
                                zone,
                                LocalDate.now(zone),
                            )
                            manager.notify(
                                BuNotifications.CARE_NOTIFICATION_ID_BASE + reminderId.hashCode(),
                                BuNotifications.careNotification(appContext, reminderId, reminder.title, why),
                            )
                        } else {
                            // The quiet digest fires at most once a day; the
                            // ledger keeps every item regardless (I1 · I9).
                            val digestsToday = notificationLogDao.countSince(KIND_DIGEST_QUIET, midnight)
                            if (digestsToday == 0) {
                                val waiting = reminderStore.pending().count { it.status == "DUE" }
                                notificationLogDao.insert(
                                    NotificationLogRow(
                                        logId = UUID.randomUUID().toString(),
                                        reminderId = reminderId,
                                        sentAtMs = now,
                                        kind = KIND_DIGEST_QUIET,
                                    ),
                                )
                                manager.notify(
                                    BuNotifications.DIGEST_NOTIFICATION_ID,
                                    BuNotifications.digestNotification(appContext, waiting.coerceAtLeast(1)),
                                )
                            }
                        }
                    }
                    outboxProcessor.drain()
                } else if (reminder != null && BuNotifications.canPostNotifications(appContext)) {
                    // Legacy path: the ledger already moved; still deliver the ring.
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
        private const val KIND_CARE_DUE = "CARE_DUE"
        private const val KIND_DIGEST_QUIET = "DIGEST_QUIET"
    }
}
