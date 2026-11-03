package com.bu.core.ledger

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CommandDao {
    @Insert
    suspend fun insert(row: CommandRow)

    @Query(
        "UPDATE command_inbox SET status = :status, errorCode = :errorCode, updatedAtMs = :atMs " +
            "WHERE commandId = :commandId",
    )
    suspend fun updateStatus(commandId: String, status: String, errorCode: String?, atMs: Long)

    @Query("SELECT * FROM command_inbox WHERE idempotencyKey = :key LIMIT 1")
    suspend fun findByIdempotencyKey(key: String): CommandRow?

    @Query("SELECT * FROM command_inbox WHERE commandId = :id LIMIT 1")
    suspend fun findById(id: String): CommandRow?
}

@Dao
interface EventDao {
    @Insert
    suspend fun insert(row: EventRow)

    @Query("SELECT * FROM domain_events WHERE correlationId = :commandId AND type = :type LIMIT 1")
    suspend fun findByCorrelation(commandId: String, type: String): EventRow?

    @Query("SELECT COUNT(*) FROM domain_events WHERE type = 'CAPTURE_SAVED' AND occurredAtMs >= :sinceMs")
    fun captureCountSince(sinceMs: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM domain_events")
    suspend fun totalCount(): Int
}

@Dao
interface OutboxDao {
    @Insert
    suspend fun insert(row: OutboxRow)

    @Query("SELECT * FROM outbox WHERE status = 'PENDING' ORDER BY createdAtMs LIMIT :limit")
    suspend fun pending(limit: Int): List<OutboxRow>

    @Query("UPDATE outbox SET status = 'DONE', attempts = attempts + 1 WHERE outboxId = :outboxId")
    suspend fun markDone(outboxId: String)

    @Query(
        "UPDATE outbox SET status = 'FAILED', attempts = attempts + 1, lastErrorCode = :code " +
            "WHERE outboxId = :outboxId",
    )
    suspend fun markFailed(outboxId: String, code: String)

    @Query("SELECT COUNT(*) FROM outbox WHERE status = 'PENDING'")
    fun pendingCount(): Flow<Int>
}

@Dao
interface CaptureDao {
    @Insert
    suspend fun insert(row: CaptureRow)

    @Query("UPDATE capture_inbox SET status = :status WHERE captureId = :captureId")
    suspend fun updateStatus(captureId: String, status: String)

    @Query(
        "UPDATE capture_inbox SET status = :status, taskObjectId = :taskObjectId, " +
            "interpretedAtMs = :atMs WHERE captureId = :captureId",
    )
    suspend fun interpret(captureId: String, status: String, taskObjectId: String?, atMs: Long)

    @Query("SELECT * FROM capture_inbox WHERE captureId = :captureId LIMIT 1")
    suspend fun findById(captureId: String): CaptureRow?

    @Query("SELECT COUNT(*) FROM capture_inbox WHERE status = 'RAW_CAPTURED' AND capturedAtMs >= :sinceMs")
    fun captureCountSince(sinceMs: Long): Flow<Int>

    @Query(
        "SELECT * FROM capture_inbox WHERE status IN ('SPARK','RAW_CAPTURED') " +
            "ORDER BY capturedAtMs DESC LIMIT 30",
    )
    fun observeSparks(): Flow<List<CaptureRow>>

    @Query("SELECT * FROM capture_inbox ORDER BY capturedAtMs DESC LIMIT :limit")
    suspend fun recent(limit: Int): List<CaptureRow>
}

@Dao
interface ObjectDao {
    @Insert
    suspend fun insert(row: ObjectRow)

    @Upsert
    suspend fun upsert(row: ObjectRow)

    @Query("SELECT * FROM objects WHERE objectId = :objectId LIMIT 1")
    suspend fun findById(objectId: String): ObjectRow?

    @Query("UPDATE objects SET title = :title, updatedAtMs = :atMs WHERE objectId = :objectId")
    suspend fun rename(objectId: String, title: String, atMs: Long)

    @Query("UPDATE objects SET deletedAtMs = :atMs, updatedAtMs = :atMs WHERE objectId = :objectId")
    suspend fun archive(objectId: String, atMs: Long)

    @Query("SELECT * FROM objects WHERE kind = 'TRACK' AND deletedAtMs IS NULL ORDER BY createdAtMs")
    fun observeTracks(): Flow<List<ObjectRow>>

    @Insert
    suspend fun insertRelationship(row: RelationshipRow)

    @Insert
    suspend fun insertRevision(row: RevisionRow)

    @Query("SELECT COUNT(*) FROM object_revisions WHERE objectId = :objectId")
    suspend fun revisionCount(objectId: String): Int
}

@Dao
interface TrackDao {
    @Insert
    suspend fun insert(row: TrackRow)

    @Upsert
    suspend fun upsertTrack(row: TrackRow)

    @Query("UPDATE tracks SET archivedAtMs = :atMs WHERE objectId = :objectId")
    suspend fun archive(objectId: String, atMs: Long)

    @Query("SELECT * FROM tracks WHERE objectId = :objectId LIMIT 1")
    suspend fun findById(objectId: String): TrackRow?

    @Query(
        "SELECT objects.*, tracks.estimateMinutes AS track_estimate, tracks.estimateSamples AS track_samples " +
            "FROM objects INNER JOIN tracks ON objects.objectId = tracks.objectId " +
            "WHERE objects.kind = 'TRACK' AND objects.deletedAtMs IS NULL ORDER BY objects.createdAtMs",
    )
    fun observeTracksWithMeta(): Flow<List<TrackWithObject>>
}

/** Projection row: track metadata joined with its object title. */
data class TrackWithObject(
    val objectId: String,
    val title: String,
    val createdAtMs: Long,
    val updatedAtMs: Long,
    val deletedAtMs: Long?,
    val kind: String,
    val track_estimate: Int,
    val track_samples: Int,
)

/** UI projection: a task row joined with its object title (titles live on objects). */
data class TaskWithTitle(
    val objectId: String,
    val title: String,
    val taskClass: String,
    val status: String,
    val scheduledDate: String?,
    val startMinute: Int?,
    val endMinute: Int?,
    val estimateMinutes: Int,
    val movedCount: Int,
    val dueAtMs: Long?,
    val createdAtMs: Long,
)

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(row: TaskRow)

    @Query("SELECT * FROM tasks WHERE objectId = :objectId LIMIT 1")
    suspend fun findById(objectId: String): TaskRow?

    @Query(
        "SELECT tasks.objectId AS objectId, objects.title AS title, tasks.taskClass AS taskClass, " +
            "tasks.status AS status, tasks.scheduledDate AS scheduledDate, tasks.startMinute AS startMinute, " +
            "tasks.endMinute AS endMinute, tasks.estimateMinutes AS estimateMinutes, " +
            "tasks.movedCount AS movedCount, tasks.dueAtMs AS dueAtMs, tasks.createdAtMs AS createdAtMs " +
            "FROM tasks INNER JOIN objects ON objects.objectId = tasks.objectId " +
            "WHERE objects.deletedAtMs IS NULL AND tasks.taskClass = :taskClass " +
            "AND tasks.scheduledDate = :dateIso AND tasks.status IN ('OPEN','DONE','PARTIAL') " +
            "ORDER BY tasks.createdAtMs",
    )
    fun observeDayTasks(taskClass: String, dateIso: String): Flow<List<TaskWithTitle>>

    @Query(
        "SELECT tasks.objectId AS objectId, objects.title AS title, tasks.taskClass AS taskClass, " +
            "tasks.status AS status, tasks.scheduledDate AS scheduledDate, tasks.startMinute AS startMinute, " +
            "tasks.endMinute AS endMinute, tasks.estimateMinutes AS estimateMinutes, " +
            "tasks.movedCount AS movedCount, tasks.dueAtMs AS dueAtMs, tasks.createdAtMs AS createdAtMs " +
            "FROM tasks INNER JOIN objects ON objects.objectId = tasks.objectId " +
            "WHERE objects.deletedAtMs IS NULL AND tasks.taskClass = 'SPARK' AND tasks.status = 'OPEN' " +
            "ORDER BY tasks.createdAtMs DESC LIMIT 30",
    )
    fun observeOpenSparksWithTitle(): Flow<List<TaskWithTitle>>

    @Query(
        "SELECT * FROM tasks WHERE taskClass = 'BIG' AND scheduledDate = :dateIso " +
            "AND status IN ('OPEN','DONE','PARTIAL') ORDER BY createdAtMs",
    )
    fun observeBigThree(dateIso: String): Flow<List<TaskRow>>

    @Query(
        "SELECT * FROM tasks WHERE taskClass = 'BLOCK' AND scheduledDate = :dateIso " +
            "AND status IN ('OPEN','DONE','PARTIAL') ORDER BY startMinute",
    )
    fun observeBlocks(dateIso: String): Flow<List<TaskRow>>

    @Query(
        "SELECT * FROM tasks WHERE taskClass = 'SPARK' AND status = 'OPEN' " +
            "ORDER BY createdAtMs DESC LIMIT 30",
    )
    fun observeOpenSparks(): Flow<List<TaskRow>>

    @Query(
        "SELECT * FROM tasks WHERE status = 'OPEN' AND scheduledDate IS NULL " +
            "AND taskClass IN ('SPARK','BIG') ORDER BY createdAtMs DESC LIMIT 30",
    )
    fun observeUnscheduled(): Flow<List<TaskRow>>

    @Query(
        "SELECT * FROM tasks WHERE taskClass = 'BLOCK' AND scheduledDate = :dateIso " +
            "AND status = 'OPEN' AND startMinute < :endMinute AND endMinute > :startMinute LIMIT 1",
    )
    suspend fun overlappingBlock(dateIso: String, startMinute: Int, endMinute: Int): TaskRow?

    @Query(
        "UPDATE tasks SET status = 'REPLACED', updatedAtMs = :atMs " +
            "WHERE taskClass = 'BIG' AND scheduledDate = :dateIso AND status = 'OPEN'",
    )
    suspend fun replaceOpenBig(dateIso: String, atMs: Long)

    @Query(
        "UPDATE tasks SET status = :status, talliedAtMs = :atMs, talliedDate = :dateIso, " +
            "actualMinutes = :actualMinutes, updatedAtMs = :atMs WHERE objectId = :objectId",
    )
    suspend fun setTally(
        objectId: String,
        status: String,
        atMs: Long,
        dateIso: String,
        actualMinutes: Int?,
    )

    @Query(
        "UPDATE tasks SET scheduledDate = :toDate, startMinute = :startMinute, " +
            "movedCount = :movedCount, updatedAtMs = :atMs WHERE objectId = :objectId",
    )
    suspend fun setSchedule(
        objectId: String,
        toDate: String,
        startMinute: Int?,
        movedCount: Int,
        atMs: Long,
    )

    @Query("SELECT COUNT(*) FROM tasks WHERE talliedDate = :dateIso AND status = 'DONE'")
    suspend fun countDoneOnDate(dateIso: String): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE talliedDate = :dateIso")
    suspend fun countTalliedOnDate(dateIso: String): Int

    @Query(
        "SELECT COUNT(*) FROM tasks WHERE scheduledDate = :dateIso AND status IN ('OPEN','DONE','PARTIAL')",
    )
    suspend fun countPlannedOnDate(dateIso: String): Int

    @Query(
        "SELECT COUNT(*) FROM tasks WHERE scheduledDate = :dateIso AND status = 'DONE'",
    )
    suspend fun countCompletedOnDate(dateIso: String): Int

    @Query("SELECT COALESCE(SUM(endMinute - startMinute), 0) FROM tasks WHERE taskClass = 'BLOCK' AND scheduledDate = :dateIso AND status IN ('OPEN','DONE','PARTIAL')")
    suspend fun plannedBlockMinutesOnDate(dateIso: String): Int
}

@Dao
interface ContractDao {
    @Upsert
    suspend fun upsert(row: DayContractRow)

    @Query("SELECT * FROM day_contracts WHERE dateIso = :dateIso LIMIT 1")
    suspend fun byDate(dateIso: String): DayContractRow?

    @Query("SELECT * FROM day_contracts WHERE dateIso = :dateIso LIMIT 1")
    fun observeByDate(dateIso: String): Flow<DayContractRow?>
}

@Dao
interface CarryDao {
    @Insert
    suspend fun insert(row: CarryRow)
}

@Dao
interface ReminderDao {
    @Insert
    suspend fun insert(row: ReminderRow)

    @Query("SELECT * FROM reminders WHERE reminderId = :reminderId LIMIT 1")
    suspend fun findById(reminderId: String): ReminderRow?

    @Query("SELECT * FROM reminders WHERE status IN ('PENDING','DUE') ORDER BY dueAtMs")
    fun observeActive(): Flow<List<ReminderRow>>

    @Query("SELECT * FROM reminders WHERE status = 'PENDING'")
    suspend fun listPending(): List<ReminderRow>

    @Query("UPDATE reminders SET status = :status, updatedAtMs = :atMs WHERE reminderId = :reminderId")
    suspend fun setStatus(reminderId: String, status: String, atMs: Long)

    @Query(
        "UPDATE reminders SET dueAtMs = :dueAtMs, alarmRevision = alarmRevision + 1, " +
            "status = 'PENDING', updatedAtMs = :atMs WHERE reminderId = :reminderId",
    )
    suspend fun reschedule(reminderId: String, dueAtMs: Long, atMs: Long)

    @Query(
        "UPDATE reminders SET alarmExact = :exact, updatedAtMs = :atMs WHERE reminderId = :reminderId",
    )
    suspend fun setAlarmExact(reminderId: String, exact: Boolean, atMs: Long)

    @Query("UPDATE reminders SET status = 'DUE', updatedAtMs = :atMs WHERE status = 'PENDING' AND dueAtMs <= :nowMs")
    suspend fun markOverdue(nowMs: Long, atMs: Long): Int
}

@Dao
interface FocusDao {
    @Insert
    suspend fun insert(row: FocusSessionRow)

    @Query("SELECT * FROM focus_sessions WHERE sessionId = :sessionId LIMIT 1")
    suspend fun findById(sessionId: String): FocusSessionRow?

    @Query("SELECT * FROM focus_sessions WHERE state = 'RUNNING' LIMIT 1")
    suspend fun running(): FocusSessionRow?

    @Query("SELECT * FROM focus_sessions WHERE state = 'RUNNING' LIMIT 1")
    fun observeRunning(): Flow<FocusSessionRow?>

    @Query(
        "UPDATE focus_sessions SET state = :state, endedAtMs = :endedAtMs, " +
            "actualMinutes = :actualMinutes WHERE sessionId = :sessionId AND state = 'RUNNING'",
    )
    suspend fun end(sessionId: String, state: String, endedAtMs: Long, actualMinutes: Int?): Int

    @Query(
        "SELECT COALESCE(SUM(actualMinutes), 0) FROM focus_sessions " +
            "WHERE state IN ('COMPLETED','ENDED_EARLY') AND startedAtMs >= :sinceMs",
    )
    fun focusMinutesSince(sinceMs: Long): Flow<Int>

    @Query("SELECT * FROM focus_sessions ORDER BY startedAtMs DESC LIMIT :limit")
    fun observeRecent(limit: Int): Flow<List<FocusSessionRow>>
}

@Dao
interface EvidenceDao {
    @Insert
    suspend fun insert(row: EvidenceRow)

    @Query("SELECT * FROM evidence_events WHERE subjectId = :subjectId ORDER BY occurredAtMs")
    fun observeForSubject(subjectId: String): Flow<List<EvidenceRow>>
}

@Dao
interface SettingsDao {
    @Upsert
    suspend fun upsert(row: SettingRow)

    @Query("SELECT * FROM settings")
    fun observeAll(): Flow<List<SettingRow>>

    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun get(key: String): SettingRow?
}

@Dao
interface MomentumDao {
    @Upsert
    suspend fun upsert(row: MomentumRow)

    @Query("SELECT * FROM momentum_cache ORDER BY dateIso DESC LIMIT :days")
    fun observeRecent(days: Int): Flow<List<MomentumRow>>
}

@Dao
interface NotificationLogDao {
    @Insert
    suspend fun insert(row: NotificationLogRow)
}
