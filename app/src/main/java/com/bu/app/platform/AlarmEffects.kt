package com.bu.app.platform

import com.bu.core.command.AlarmTicketPayload
import com.bu.core.command.OutboxEffect
import com.bu.core.command.ReminderStore
import com.bu.core.outbox.OutboxEffectHandler
import com.bu.core.platform.ReminderScheduler
import com.bu.core.time.BuClock
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Alarm projection handlers (locked plan §10): SQLite decides, AlarmManager
 * delivers. Both handlers are idempotent — re-arming the same reminder is a
 * no-op on the platform (stable PendingIntent keyed by reminder id), and the
 * ledger row already moved inside the committing transaction.
 */
@Singleton
class ScheduleAlarmHandler @Inject constructor(
    private val scheduler: ReminderScheduler,
    private val reminders: ReminderStore,
    private val clock: BuClock,
) : OutboxEffectHandler {

    override val effectType: String = "SCHEDULE_REMINDER_ALARM"

    override suspend fun apply(effect: OutboxEffect) {
        val ticket = parse(effect) ?: throw IllegalArgumentException("ALARM_TICKET_MALFORMED")
        val exact = scheduler.schedule(ticket.reminderId, ticket.dueAtMs)
        // The honesty flag is written back where every UI that mentions the
        // reminder can read it (I9 — an inexact alarm never poses as exact).
        reminders.setAlarmExact(ticket.reminderId, exact, clock.nowMs())
    }

    private fun parse(effect: OutboxEffect): AlarmTicketPayload? = try {
        Json.decodeFromString(AlarmTicketPayload.serializer(), effect.payloadJson)
    } catch (t: Throwable) {
        null
    }
}

@Singleton
class CancelAlarmHandler @Inject constructor(
    private val scheduler: ReminderScheduler,
) : OutboxEffectHandler {

    override val effectType: String = "CANCEL_REMINDER_ALARM"

    override suspend fun apply(effect: OutboxEffect) {
        val ticket = try {
            Json.decodeFromString(AlarmTicketPayload.serializer(), effect.payloadJson)
        } catch (t: Throwable) {
            null
        } ?: return // a cancel with an unreadable ticket has nothing to disarm
        scheduler.cancel(ticket.reminderId)
    }
}
