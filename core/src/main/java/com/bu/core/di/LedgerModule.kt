package com.bu.core.di

import android.content.Context
import androidx.room.Room
import com.bu.core.command.BtkGateway
import com.bu.core.command.CaptureStore
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandInboxStore
import com.bu.core.command.ContractStore
import com.bu.core.command.DomainStores
import com.bu.core.command.EventStore
import com.bu.core.command.EvidenceStore
import com.bu.core.command.FocusStore
import com.bu.core.command.MomentumStore
import com.bu.core.command.ObjectStore
import com.bu.core.command.OutboxStore
import com.bu.core.command.ReminderStore
import com.bu.core.command.SettingsStore
import com.bu.core.command.TaskStore
import com.bu.core.command.TrackStore
import com.bu.core.command.TransactionRunner
import com.bu.core.ledger.BuDatabase
import com.bu.core.ledger.CarryDao
import com.bu.core.ledger.CaptureDao
import com.bu.core.ledger.CommandDao
import com.bu.core.ledger.ContractDao
import com.bu.core.ledger.EvidenceDao
import com.bu.core.ledger.EventDao
import com.bu.core.ledger.FocusDao
import com.bu.core.ledger.MomentumDao
import com.bu.core.ledger.NotificationLogDao
import com.bu.core.ledger.ObjectDao
import com.bu.core.ledger.OutboxDao
import com.bu.core.ledger.ReminderDao
import com.bu.core.ledger.RoomCaptureStore
import com.bu.core.ledger.RoomCommandInboxStore
import com.bu.core.ledger.RoomContractStore
import com.bu.core.ledger.RoomDomainStores
import com.bu.core.ledger.RoomEventStore
import com.bu.core.ledger.RoomEvidenceStore
import com.bu.core.ledger.RoomFocusStore
import com.bu.core.ledger.RoomMomentumStore
import com.bu.core.ledger.RoomObjectStore
import com.bu.core.ledger.RoomOutboxStore
import com.bu.core.ledger.RoomReminderStore
import com.bu.core.ledger.RoomSettingsStore
import com.bu.core.ledger.RoomTaskStore
import com.bu.core.ledger.RoomTrackStore
import com.bu.core.ledger.RoomTransactionRunner
import com.bu.core.ledger.SettingsDao
import com.bu.core.ledger.TaskDao
import com.bu.core.ledger.TrackDao
import com.bu.core.outbox.CaptureInterpretHandler
import com.bu.core.outbox.EffectKey
import com.bu.core.outbox.OutboxEffectHandler
import com.bu.core.outbox.ProjectionsDirtyHandler
import com.bu.core.time.BuClock
import com.bu.core.time.SystemBuClock
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LedgerModule {

    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): BuDatabase =
        Room.databaseBuilder(context, BuDatabase::class.java, "bu-ledger.db")
            .addMigrations(BuDatabase.MIGRATION_1_2)
            .build()

    @Provides fun commandDao(db: BuDatabase) = db.commandDao()
    @Provides fun eventDao(db: BuDatabase) = db.eventDao()
    @Provides fun outboxDao(db: BuDatabase) = db.outboxDao()
    @Provides fun captureDao(db: BuDatabase) = db.captureDao()
    @Provides fun objectDao(db: BuDatabase) = db.objectDao()
    @Provides fun trackDao(db: BuDatabase) = db.trackDao()
    @Provides fun taskDao(db: BuDatabase) = db.taskDao()
    @Provides fun contractDao(db: BuDatabase) = db.contractDao()
    @Provides fun carryDao(db: BuDatabase) = db.carryDao()
    @Provides fun reminderDao(db: BuDatabase) = db.reminderDao()
    @Provides fun focusDao(db: BuDatabase) = db.focusDao()
    @Provides fun evidenceDao(db: BuDatabase) = db.evidenceDao()
    @Provides fun settingsDao(db: BuDatabase) = db.settingsDao()
    @Provides fun momentumDao(db: BuDatabase) = db.momentumDao()
    @Provides fun notificationLogDao(db: BuDatabase) = db.notificationLogDao()

    @Provides
    @Singleton
    fun clock(): BuClock = SystemBuClock()

    @Provides
    @Singleton
    fun domainStores(impl: RoomDomainStores): DomainStores = impl
}

@Module
@InstallIn(SingletonComponent::class)
abstract class KernelModule {

    @Binds
    abstract fun transactionRunner(impl: RoomTransactionRunner): TransactionRunner

    @Binds
    abstract fun commandInbox(impl: RoomCommandInboxStore): CommandInboxStore

    @Binds
    abstract fun eventStore(impl: RoomEventStore): EventStore

    @Binds
    abstract fun outboxStore(impl: RoomOutboxStore): OutboxStore

    @Binds
    abstract fun captureStore(impl: RoomCaptureStore): CaptureStore

    @Binds
    abstract fun objectStore(impl: RoomObjectStore): ObjectStore

    @Binds
    abstract fun trackStore(impl: RoomTrackStore): TrackStore

    @Binds
    abstract fun taskStore(impl: RoomTaskStore): TaskStore

    @Binds
    abstract fun contractStore(impl: RoomContractStore): ContractStore

    @Binds
    abstract fun reminderStore(impl: RoomReminderStore): ReminderStore

    @Binds
    abstract fun focusStore(impl: RoomFocusStore): FocusStore

    @Binds
    abstract fun settingsStore(impl: RoomSettingsStore): SettingsStore

    @Binds
    abstract fun evidenceStore(impl: RoomEvidenceStore): EvidenceStore

    @Binds
    abstract fun momentumStore(impl: RoomMomentumStore): MomentumStore

    @Binds
    @Singleton
    abstract fun commandGateway(impl: BtkGateway): CommandGateway

    // Outbox effect handlers registered by effect type (P1 core set;
    // the app module adds the alarm + widget handlers).
    @Binds
    @IntoMap
    @EffectKey("CAPTURE_INTERPRET")
    abstract fun captureInterpretHandler(impl: CaptureInterpretHandler): OutboxEffectHandler

    @Binds
    @IntoMap
    @EffectKey("PROJECTIONS_DIRTY")
    abstract fun projectionsDirtyHandler(impl: ProjectionsDirtyHandler): OutboxEffectHandler
}

