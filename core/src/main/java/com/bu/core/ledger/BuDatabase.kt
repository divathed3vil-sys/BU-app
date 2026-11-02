package com.bu.core.ledger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The BU ledger — single-writer SQLite source of truth (locked plan §6).
 * P0 carries the kernel tables; P1 adds objects/documents/relationships/tasks.
 */
@Database(
    entities = [CommandRow::class, EventRow::class, OutboxRow::class, CaptureRow::class],
    version = 1,
    exportSchema = true,
)
abstract class BuDatabase : RoomDatabase() {
    abstract fun commandDao(): CommandDao
    abstract fun eventDao(): EventDao
    abstract fun outboxDao(): OutboxDao
    abstract fun captureDao(): CaptureDao

    companion object {
        fun build(context: Context): BuDatabase =
            Room.databaseBuilder(context, BuDatabase::class.java, "bu-ledger.db").build()
    }
}
