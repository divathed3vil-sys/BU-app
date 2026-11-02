package com.bu.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The one data core. Both planes meet here — identity writes its world,
 * guardian will write its evidence, and nothing ever leaves the device.
 */
@Database(
    entities = [TrackEntity::class, PlanItemEntity::class, CaptureEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class BuDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun planDao(): PlanDao
    abstract fun captureDao(): CaptureDao
}
