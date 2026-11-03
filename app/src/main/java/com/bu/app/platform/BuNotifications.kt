package com.bu.app.platform

import android.Manifest
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bu.app.MainActivity
import com.bu.app.R
import com.bu.core.platform.ReminderScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Notification + alarm plumbing for the care round-trip (locked plan §10):
 * SQLite is canonical, alarms are a projection; exact only where the OS
 * allows it, and an inexact alarm is always described as inexact.
 */
object BuNotifications {

    const val CHANNEL_CARE = "bu_care"
    const val CHANNEL_SYSTEM = "bu_system"
    const val CARE_NOTIFICATION_ID_BASE = 20_000
    const val DIGEST_NOTIFICATION_ID = 20_000 - 1

    fun createChannels(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val care = NotificationChannel(
            CHANNEL_CARE,
            "Care reminders",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "The one reminder BU may interrupt you for."
        }
        val system = NotificationChannel(
            CHANNEL_SYSTEM,
            "Quiet status",
            NotificationManager.IMPORTANCE_MIN,
        ).apply {
            description = "Background confirmations and the quiet care digest. Never interrupts."
        }
        manager.createNotificationChannels(listOf(care, system))
    }

    fun canPostNotifications(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= 33) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    /**
     * The interruptive care notification. [whyNow] — the line that can never
     * be absent (locked plan §2): every ring states why it is surfacing now.
     */
    fun careNotification(context: Context, reminderId: String, title: String, whyNow: String? = null): Notification {
        val open = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            reminderId.hashCode(),
            open,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(context, CHANNEL_CARE)
            .setSmallIcon(R.drawable.ic_bu_notify)
            .setContentTitle("Care · now")
            .setContentText(if (whyNow != null) "$title · $whyNow" else title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(if (whyNow != null) "$title · $whyNow" else title),
            )
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    /**
     * The quiet digest — budget overflow lands here (locked plan §9: "rest →
     * digests"). Same channel as silent status; nothing is lost, the ledger
     * keeps every item and PLAN shows them all.
     */
    fun digestNotification(context: Context, waiting: Int): Notification {
        val open = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentIntent = PendingIntent.getActivity(
            context,
            DIGEST_NOTIFICATION_ID,
            open,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat.Builder(context, CHANNEL_SYSTEM)
            .setSmallIcon(R.drawable.ic_bu_notify)
            .setContentTitle("Care · waiting")
            .setContentText("$waiting care item${if (waiting == 1) "" else "s"} waiting in PLAN — no rush.")
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setSilent(true)
            .build()
    }
}

@Singleton
class AndroidReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) : ReminderScheduler {

    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /** True when armed exact; false when the OS forced an inexact window. */
    override suspend fun schedule(reminderId: String, dueAtMs: Long): Boolean {
        val pi = pendingIntent(reminderId)
        val canExact = if (Build.VERSION.SDK_INT >= 31) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
        if (canExact) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dueAtMs, pi)
        } else {
            // Honest fallback: a 10-minute delivery window, never presented as exact.
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, dueAtMs, 10 * 60_000L, pi)
        }
        return canExact
    }

    override suspend fun cancel(reminderId: String) {
        alarmManager.cancel(pendingIntent(reminderId))
    }

    private fun pendingIntent(reminderId: String): PendingIntent {
        val intent = Intent(context, CareReceiver::class.java).apply {
            action = CareReceiver.ACTION_CARE_DUE
            putExtra(CareReceiver.EXTRA_REMINDER_ID, reminderId)
        }
        return PendingIntent.getBroadcast(
            context,
            reminderId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}
