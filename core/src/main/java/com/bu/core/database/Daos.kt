package com.bu.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM tracks WHERE archived = 0 ORDER BY sort, createdAt")
    fun observeActive(): Flow<List<TrackEntity>>

    @Insert
    suspend fun insert(track: TrackEntity): Long

    @Update
    suspend fun update(track: TrackEntity)

    @Query("UPDATE tracks SET archived = 1 WHERE id = :id")
    suspend fun archive(id: Long)

    @Query("SELECT COUNT(*) FROM tracks")
    suspend fun count(): Int
}

@Dao
interface PlanDao {
    /** BIG first, then timed blocks in clock order, then the loose tail. */
    @Query(
        "SELECT * FROM plan_items WHERE date = :day " +
            "ORDER BY kind, startMin IS NULL, startMin, createdAt",
    )
    fun observeDay(day: Long): Flow<List<PlanItemEntity>>

    @Insert
    suspend fun insert(item: PlanItemEntity): Long

    @Update
    suspend fun update(item: PlanItemEntity)

    @Delete
    suspend fun delete(item: PlanItemEntity)
}

@Dao
interface CaptureDao {
    @Query("SELECT * FROM captures WHERE status != 3 ORDER BY ts DESC LIMIT 200")
    fun observeInbox(): Flow<List<CaptureEntity>>

    @Query("SELECT COUNT(*) FROM captures WHERE status = 0")
    fun observeNewCount(): Flow<Int>

    @Insert
    suspend fun insert(capture: CaptureEntity): Long

    @Query("UPDATE captures SET status = :status, parsedKind = :parsedKind, parsedRef = :parsedRef WHERE id = :id")
    suspend fun resolve(id: Long, status: Int, parsedKind: Int?, parsedRef: Long?)

    @Query("UPDATE captures SET status = :status WHERE id = :id")
    suspend fun setStatus(id: Long, status: Int)
}
