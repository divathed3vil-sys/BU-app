package com.bu.core.ledger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * The BU ledger — single-writer SQLite source of truth (locked plan §6).
 *
 * v1 (P0): kernel spine — command_inbox · domain_events · outbox · capture_inbox.
 * v2 (P1): object plane + tasks/tracks/contracts/carry + reminders + focus +
 *          evidence + settings + momentum + notification_log.
 * Migrations are additive CREATE TABLE / ADD COLUMN only — a P0 install keeps
 * every capture it ever acknowledged (I1).
 */
@Database(
    entities = [
        CommandRow::class,
        EventRow::class,
        OutboxRow::class,
        CaptureRow::class,
        ObjectRow::class,
        RevisionRow::class,
        RelationshipRow::class,
        TrackRow::class,
        TaskRow::class,
        DayContractRow::class,
        CarryRow::class,
        ReminderRow::class,
        FocusSessionRow::class,
        EvidenceRow::class,
        SettingRow::class,
        MomentumRow::class,
        NotificationLogRow::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class BuDatabase : RoomDatabase() {
    abstract fun commandDao(): CommandDao
    abstract fun eventDao(): EventDao
    abstract fun outboxDao(): OutboxDao
    abstract fun captureDao(): CaptureDao
    abstract fun objectDao(): ObjectDao
    abstract fun trackDao(): TrackDao
    abstract fun taskDao(): TaskDao
    abstract fun contractDao(): ContractDao
    abstract fun carryDao(): CarryDao
    abstract fun reminderDao(): ReminderDao
    abstract fun focusDao(): FocusDao
    abstract fun evidenceDao(): EvidenceDao
    abstract fun settingsDao(): SettingsDao
    abstract fun momentumDao(): MomentumDao
    abstract fun notificationLogDao(): NotificationLogDao

    companion object {
        fun build(context: Context): BuDatabase =
            Room.databaseBuilder(context, BuDatabase::class.java, "bu-ledger.db")
                .addMigrations(MIGRATION_1_2)
                .build()

        /**
         * Hand-written SQL must match Room's expected schema exactly
         * (affinity, NOT NULL, PK, indices). Polymorphic references are
         * validated in the repository layer, not by FKs (locked plan §6.2).
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Object plane -------------------------------------------------
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `objects` (`objectId` TEXT NOT NULL, " +
                        "`kind` TEXT NOT NULL, `title` TEXT NOT NULL, `createdAtMs` INTEGER NOT NULL, " +
                        "`updatedAtMs` INTEGER NOT NULL, `deletedAtMs` INTEGER, PRIMARY KEY(`objectId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_objects_kind` ON `objects` (`kind`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_objects_deletedAtMs` ON `objects` (`deletedAtMs`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `object_revisions` (`revisionId` TEXT NOT NULL, " +
                        "`objectId` TEXT NOT NULL, `revision` INTEGER NOT NULL, `snapshotJson` TEXT NOT NULL, " +
                        "`createdAtMs` INTEGER NOT NULL, PRIMARY KEY(`revisionId`))",
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_object_revisions_objectId` " +
                        "ON `object_revisions` (`objectId`)",
                )

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `relationships` (`relationshipId` TEXT NOT NULL, " +
                        "`fromId` TEXT NOT NULL, `toId` TEXT NOT NULL, `type` TEXT NOT NULL, " +
                        "`createdAtMs` INTEGER NOT NULL, PRIMARY KEY(`relationshipId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_relationships_fromId` ON `relationships` (`fromId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_relationships_toId` ON `relationships` (`toId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_relationships_type` ON `relationships` (`type`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `tracks` (`objectId` TEXT NOT NULL, " +
                        "`estimateMinutes` INTEGER NOT NULL, `estimateSamples` INTEGER NOT NULL, " +
                        "`archivedAtMs` INTEGER, PRIMARY KEY(`objectId`))",
                )

                // Tasks + contracts + carry ------------------------------------
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `tasks` (`objectId` TEXT NOT NULL, " +
                        "`taskClass` TEXT NOT NULL, `status` TEXT NOT NULL, `priority` INTEGER NOT NULL, " +
                        "`estimateMinutes` INTEGER NOT NULL, `actualMinutes` INTEGER, `dueAtMs` INTEGER, " +
                        "`scheduledDate` TEXT, `startMinute` INTEGER, `endMinute` INTEGER, " +
                        "`timezone` TEXT NOT NULL, `movedCount` INTEGER NOT NULL, `energy` TEXT, " +
                        "`sourceCaptureId` TEXT, `talliedAtMs` INTEGER, `talliedDate` TEXT, " +
                        "`createdAtMs` INTEGER NOT NULL, `updatedAtMs` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`objectId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_taskClass` ON `tasks` (`taskClass`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_status` ON `tasks` (`status`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_scheduledDate` ON `tasks` (`scheduledDate`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_tasks_talliedDate` ON `tasks` (`talliedDate`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `day_contracts` (`dateIso` TEXT NOT NULL, " +
                        "`mainFrontTaskId` TEXT, `minimumDay` TEXT, `ifThenJson` TEXT, `ceilingRung` INTEGER, " +
                        "`sealedAtMs` INTEGER, `wakePlan` TEXT, `mood` INTEGER, `updatedAtMs` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`dateIso`))",
                )

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `carry_ledger` (`carryId` TEXT NOT NULL, " +
                        "`taskId` TEXT NOT NULL, `fromDate` TEXT NOT NULL, `toDate` TEXT NOT NULL, " +
                        "`movedAtMs` INTEGER NOT NULL, PRIMARY KEY(`carryId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_carry_ledger_taskId` ON `carry_ledger` (`taskId`)")

                // Care + focus + evidence + settings + projections -------------
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `reminders` (`reminderId` TEXT NOT NULL, " +
                        "`title` TEXT NOT NULL, `dueAtMs` INTEGER NOT NULL, `kind` TEXT NOT NULL, " +
                        "`status` TEXT NOT NULL, `alarmRevision` INTEGER NOT NULL, `alarmExact` INTEGER, " +
                        "`createdAtMs` INTEGER NOT NULL, `updatedAtMs` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`reminderId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_status` ON `reminders` (`status`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminders_dueAtMs` ON `reminders` (`dueAtMs`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `focus_sessions` (`sessionId` TEXT NOT NULL, " +
                        "`trackId` TEXT, `plannedMinutes` INTEGER NOT NULL, `state` TEXT NOT NULL, " +
                        "`startedAtMs` INTEGER NOT NULL, `startedElapsedMs` INTEGER NOT NULL, " +
                        "`endedAtMs` INTEGER, `actualMinutes` INTEGER, PRIMARY KEY(`sessionId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_focus_sessions_state` ON `focus_sessions` (`state`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `evidence_events` (`evidenceId` TEXT NOT NULL, " +
                        "`subjectId` TEXT NOT NULL, `subjectType` TEXT NOT NULL, `grade` TEXT NOT NULL, " +
                        "`statement` TEXT NOT NULL, `occurredAtMs` INTEGER NOT NULL, PRIMARY KEY(`evidenceId`))",
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_evidence_events_subjectId` ON `evidence_events` (`subjectId`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_evidence_events_grade` ON `evidence_events` (`grade`)")

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `settings` (`key` TEXT NOT NULL, `value` TEXT NOT NULL, " +
                        "PRIMARY KEY(`key`))",
                )

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `momentum_cache` (`dateIso` TEXT NOT NULL, " +
                        "`doneCount` INTEGER NOT NULL, `tallyCount` INTEGER NOT NULL, PRIMARY KEY(`dateIso`))",
                )

                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `notification_log` (`logId` TEXT NOT NULL, " +
                        "`reminderId` TEXT NOT NULL, `sentAtMs` INTEGER NOT NULL, `kind` TEXT NOT NULL, " +
                        "PRIMARY KEY(`logId`))",
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_notification_log_reminderId` " +
                        "ON `notification_log` (`reminderId`)",
                )

                // capture_inbox gains its promotion link — nullable ADD COLUMN, P0 rows untouched.
                db.execSQL("ALTER TABLE `capture_inbox` ADD COLUMN `taskObjectId` TEXT")
            }
        }
    }
}
