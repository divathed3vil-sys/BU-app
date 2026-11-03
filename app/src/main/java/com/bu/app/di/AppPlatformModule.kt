package com.bu.app.di

import com.bu.app.platform.AndroidReminderScheduler
import com.bu.app.platform.AndroidWidgetRefresher
import com.bu.app.platform.CancelAlarmHandler
import com.bu.app.platform.ScheduleAlarmHandler
import com.bu.app.platform.WidgetDirtyHandler
import com.bu.core.outbox.EffectKey
import com.bu.core.outbox.OutboxEffectHandler
import com.bu.core.platform.ReminderScheduler
import com.bu.core.platform.WidgetRefresher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * App-module platform bindings: alarm scheduling, widget repainting, and the
 * outbox handlers that drive them. The kernel enqueued the tickets; these
 * adapters apply them after commit (locked plan §5.1 plane 5).
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppPlatformModule {

    @Binds
    abstract fun reminderScheduler(impl: AndroidReminderScheduler): ReminderScheduler

    @Binds
    abstract fun widgetRefresher(impl: AndroidWidgetRefresher): WidgetRefresher

    @Binds
    @IntoMap
    @EffectKey("SCHEDULE_REMINDER_ALARM")
    abstract fun scheduleAlarmHandler(impl: ScheduleAlarmHandler): OutboxEffectHandler

    @Binds
    @IntoMap
    @EffectKey("CANCEL_REMINDER_ALARM")
    abstract fun cancelAlarmHandler(impl: CancelAlarmHandler): OutboxEffectHandler

    @Binds
    @IntoMap
    @EffectKey("WIDGET_DIRTY")
    abstract fun widgetDirtyHandler(impl: WidgetDirtyHandler): OutboxEffectHandler
}
