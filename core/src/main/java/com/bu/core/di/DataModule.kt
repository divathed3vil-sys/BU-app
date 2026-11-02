package com.bu.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bu.core.database.BuDatabase
import com.bu.core.database.CaptureDao
import com.bu.core.database.PlanDao
import com.bu.core.database.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BuDatabase =
        Room.databaseBuilder(context, BuDatabase::class.java, "bu_core.db")
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .build()

    @Provides
    fun trackDao(db: BuDatabase): TrackDao = db.trackDao()

    @Provides
    fun planDao(db: BuDatabase): PlanDao = db.planDao()

    @Provides
    fun captureDao(db: BuDatabase): CaptureDao = db.captureDao()
}
