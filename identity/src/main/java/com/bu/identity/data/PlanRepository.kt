package com.bu.identity.data

import com.bu.core.database.CaptureDao
import com.bu.core.database.CaptureEntity
import com.bu.core.database.PlanDao
import com.bu.core.database.PlanItemEntity
import com.bu.core.database.TrackDao
import com.bu.core.database.TrackEntity
import com.bu.core.model.Capture
import com.bu.core.model.CaptureSource
import com.bu.core.model.CaptureStatus
import com.bu.core.model.PlanItem
import com.bu.core.model.PlanKind
import com.bu.core.model.PlanState
import com.bu.core.model.Track
import com.bu.core.model.TrackKind
import com.bu.core.plan.CarryLogic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The identity plane's hands on the data core.
 * Domain in, domain out — Room rows never leak past this seam.
 */
@Singleton
class PlanRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val planDao: PlanDao,
    private val captureDao: CaptureDao,
) {
    // ---- observation ----

    fun observeActiveTracks(): Flow<List<Track>> =
        trackDao.observeActive().map { rows -> rows.map { it.toDomain() } }

    fun observeDay(day: Long): Flow<List<PlanItem>> =
        planDao.observeDay(day).map { rows -> rows.map { it.toDomain() } }

    fun observeInbox(): Flow<List<Capture>> =
        captureDao.observeInbox().map { rows -> rows.map { it.toDomain() } }

    fun observeNewCaptureCount(): Flow<Int> = captureDao.observeNewCount()

    // ---- tracks ----

    suspend fun createTrack(name: String, colorToken: Int, kind: TrackKind): Long =
        trackDao.insert(
            TrackEntity(
                name = name.trim(),
                colorToken = colorToken,
                kind = kind.ordinal,
                sort = trackDao.count(),
                createdAt = System.currentTimeMillis(),
            ),
        )

    suspend fun archiveTrack(id: Long) = trackDao.archive(id)

    // ---- plan items ----

    suspend fun saveItem(item: PlanItem): Long {
        val now = System.currentTimeMillis()
        return if (item.id == 0L) {
            planDao.insert(item.copy(createdAt = now, updatedAt = now).toEntity())
        } else {
            planDao.update(item.copy(updatedAt = now).toEntity())
            item.id
        }
    }

    suspend fun deleteItem(item: PlanItem) = planDao.delete(item.toEntity())

    suspend fun setItemState(item: PlanItem, state: PlanState) {
        saveItem(item.copy(state = state))
    }

    /** The cooling carry: original goes CARRIED, a fresh linked copy opens on the target day. */
    suspend fun carryItem(item: PlanItem, targetDay: Long) {
        val now = System.currentTimeMillis()
        val (cooled, fresh) = CarryLogic.carryForward(item, targetDay, now)
        planDao.update(cooled.toEntity())
        planDao.insert(fresh.toEntity())
    }

    // ---- sparks ----

    suspend fun quickCapture(raw: String, source: CaptureSource = CaptureSource.TYPED): Long =
        captureDao.insert(
            CaptureEntity(
                raw = raw.trim(),
                ts = System.currentTimeMillis(),
                status = CaptureStatus.NEW.ordinal,
                source = source.ordinal,
            ),
        )

    /** A spark becomes a loose plan item on the given day — the user arranges it in the arc. */
    suspend fun promoteCapture(capture: Capture, day: Long): Long {
        val now = System.currentTimeMillis()
        val itemId = planDao.insert(
            PlanItemEntity(
                date = day,
                kind = PlanKind.SPARK.ordinal,
                title = capture.raw,
                state = PlanState.PENDING.ordinal,
                createdAt = now,
                updatedAt = now,
            ),
        )
        captureDao.resolve(capture.id, CaptureStatus.RESOLVED.ordinal, PlanKind.SPARK.ordinal, itemId)
        return itemId
    }

    suspend fun discardCapture(id: Long) = captureDao.setStatus(id, CaptureStatus.DISCARDED.ordinal)

    // ---- mappers (the seam) ----

    private fun TrackEntity.toDomain() = Track(
        id = id,
        name = name,
        colorToken = colorToken,
        kind = TrackKind.entries[kind],
        weeklyTargetMin = weeklyTargetMin,
        windowStartMin = windowStartMin,
        windowEndMin = windowEndMin,
        sort = sort,
        archived = archived,
        createdAt = createdAt,
    )

    private fun Track.toEntity() = TrackEntity(
        id = id,
        name = name,
        colorToken = colorToken,
        kind = kind.ordinal,
        weeklyTargetMin = weeklyTargetMin,
        windowStartMin = windowStartMin,
        windowEndMin = windowEndMin,
        sort = sort,
        archived = archived,
        createdAt = createdAt,
    )

    private fun PlanItemEntity.toDomain() = PlanItem(
        id = id,
        date = date,
        kind = PlanKind.entries[kind],
        title = title,
        trackId = trackId,
        startMin = startMin,
        durMin = durMin,
        state = PlanState.entries[state],
        locked = locked,
        carriedFromId = carriedFromId,
        evidence = evidence,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun PlanItem.toEntity() = PlanItemEntity(
        id = id,
        date = date,
        kind = kind.ordinal,
        title = title,
        trackId = trackId,
        startMin = startMin,
        durMin = durMin,
        state = state.ordinal,
        locked = locked,
        carriedFromId = carriedFromId,
        evidence = evidence,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun CaptureEntity.toDomain() = Capture(
        id = id,
        raw = raw,
        ts = ts,
        status = CaptureStatus.entries[status],
        parsedKind = parsedKind?.let { PlanKind.entries[it] },
        parsedRef = parsedRef,
        source = CaptureSource.entries[source],
    )
}
