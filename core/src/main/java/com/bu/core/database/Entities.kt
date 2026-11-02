package com.bu.core.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room rows — the storage mirror of the Part 3 schema.
 * Enums are stored as ints (domain model ordinal). Time is epochDay / minutes / epochMillis.
 * Storage-first: 10 years of this stays far under 50 MB.
 */

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val colorToken: Int,
    val kind: Int,
    val weeklyTargetMin: Int? = null,
    val windowStartMin: Int? = null,
    val windowEndMin: Int? = null,
    val sort: Int = 0,
    val archived: Boolean = false,
    val createdAt: Long,
)

@Entity(
    tableName = "plan_items",
    foreignKeys = [
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("date", "kind"), Index("trackId", "date")],
)
data class PlanItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long, // epochDay
    val kind: Int,
    val title: String,
    val trackId: Long? = null,
    val startMin: Int? = null,
    val durMin: Int? = null,
    val state: Int,
    val locked: Boolean = false,
    val carriedFromId: Long? = null,
    val evidence: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
)

@Entity(tableName = "captures", indices = [Index("status"), Index("ts")])
data class CaptureEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val raw: String,
    val ts: Long,
    val status: Int,
    val parsedKind: Int? = null,
    val parsedRef: Long? = null,
    val source: Int,
)
