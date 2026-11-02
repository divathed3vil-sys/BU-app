package com.bu.core.ledger

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Query("SELECT * FROM outbox WHERE status = 'PENDING'")
    suspend fun pending(): List<OutboxRow>

    @Query("UPDATE outbox SET status = 'DONE', attempts = attempts + 1 WHERE outboxId = :outboxId")
    suspend fun markDone(outboxId: String)

    @Query("SELECT COUNT(*) FROM outbox WHERE status = 'PENDING'")
    fun pendingCount(): Flow<Int>
}

@Dao
interface CaptureDao {
    @Insert
    suspend fun insert(row: CaptureRow)

    @Query("UPDATE capture_inbox SET status = :status WHERE captureId = :captureId")
    suspend fun updateStatus(captureId: String, status: String)

    @Query("SELECT COUNT(*) FROM capture_inbox WHERE status = 'RAW_CAPTURED' AND capturedAtMs >= :sinceMs")
    fun captureCountSince(sinceMs: Long): Flow<Int>

    @Query("SELECT * FROM capture_inbox ORDER BY capturedAtMs DESC LIMIT :limit")
    suspend fun recent(limit: Int): List<CaptureRow>
}
