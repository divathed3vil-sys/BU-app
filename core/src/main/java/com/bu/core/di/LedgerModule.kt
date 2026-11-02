package com.bu.core.di

import android.content.Context
import androidx.room.Room
import com.bu.core.command.CaptureStore
import com.bu.core.command.BtkGateway
import com.bu.core.command.CommandGateway
import com.bu.core.command.CommandInboxStore
import com.bu.core.command.EventStore
import com.bu.core.command.OutboxStore
import com.bu.core.command.TransactionRunner
import com.bu.core.ledger.BuDatabase
import com.bu.core.ledger.RoomCaptureStore
import com.bu.core.ledger.RoomCommandInboxStore
import com.bu.core.ledger.RoomEventStore
import com.bu.core.ledger.RoomOutboxStore
import com.bu.core.ledger.RoomTransactionRunner
import com.bu.core.time.BuClock
import com.bu.core.time.SystemBuClock
import dagger.Binds
import dagger.Module
import dagger.Provides
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
            .build()

    @Provides
    fun commandDao(db: BuDatabase) = db.commandDao()

    @Provides
    fun eventDao(db: BuDatabase) = db.eventDao()

    @Provides
    fun outboxDao(db: BuDatabase) = db.outboxDao()

    @Provides
    fun captureDao(db: BuDatabase) = db.captureDao()

    @Provides
    @Singleton
    fun clock(): BuClock = SystemBuClock()
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
    @Singleton
    abstract fun commandGateway(impl: BtkGateway): CommandGateway
}
