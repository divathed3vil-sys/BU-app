package com.bu.app

import android.app.Application
import com.bu.app.platform.BuNotifications
import com.bu.core.command.ReminderStore
import com.bu.core.ledger.ReminderDao
import com.bu.core.outbox.OutboxProcessor
import com.bu.core.time.BuClock
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * BU application: one process, one ledger, one kernel.
 * On start: channels exist, overdue reminders land in the ledger honestly,
 * and any outbox work the last process owed is drained (I1 · I5).
 */
@HiltAndroidApp
class BUApplication : Application() {

    @Inject lateinit var outboxProcessor: OutboxProcessor
    @Inject lateinit var reminderDao: ReminderDao
    @Inject lateinit var clock: BuClock

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        BuNotifications.createChannels(this)
        appScope.launch {
            runCatching {
                val now = clock.nowMs()
                reminderDao.markOverdue(now, now)
            }
            runCatching { outboxProcessor.drain() }
        }
    }
}
