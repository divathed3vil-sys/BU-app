package com.bu.core.model

/**
 * BU domain models — the vocabulary of the loop.
 *
 * Doctrine: enum ordinal IS the stored int (Part 3 schema). Never reorder an
 * enum — only append. Renames are free; order is forever.
 */

enum class TrackKind(val label: String) { GOAL("goal"), HABIT("habit"), AREA("area") }

/** A user-created track. BU ships zero of these — ever. */
data class Track(
    val id: Long = 0,
    val name: String,
    val colorToken: Int, // index into the designsystem TrackColors palette
    val kind: TrackKind = TrackKind.AREA,
    val weeklyTargetMin: Int? = null,
    val windowStartMin: Int? = null,
    val windowEndMin: Int? = null,
    val sort: Int = 0,
    val archived: Boolean = false,
    val createdAt: Long = 0,
)

enum class PlanKind(val label: String) { BIG("big three"), BLOCK("block"), SPARK("spark") }

enum class PlanState(val label: String) {
    PENDING("open"),
    DONE("done"),
    PARTIAL("partial"),
    MISSED("missed"),
    CARRIED("carried"), // cooling, never shaming
    DROPPED("dropped"), // a decision, not a debt
}

/** One row of the plan. Big Three, time blocks, and promoted sparks all live here. */
data class PlanItem(
    val id: Long = 0,
    val date: Long, // epochDay
    val kind: PlanKind = PlanKind.BLOCK,
    val title: String,
    val trackId: Long? = null,
    val startMin: Int? = null, // minutes of day
    val durMin: Int? = null,
    val state: PlanState = PlanState.PENDING,
    val locked: Boolean = false, // Big Three commitment lock — P2 will turn this on
    val carriedFromId: Long? = null,
    val evidence: String? = null, // Needle 3 pre-fill lands in P6
    val createdAt: Long = 0,
    val updatedAt: Long = 0,
) {
    val isTimed: Boolean get() = startMin != null && durMin != null
    val endMin: Int? get() = if (startMin != null && durMin != null) startMin + durMin else null
}

enum class CaptureStatus(val label: String) { NEW("new"), PARSED("parsed"), RESOLVED("planned"), DISCARDED("discarded") }

enum class CaptureSource(val label: String) { TYPED("typed"), VOICE("voice"), SHARE("share") }

/** A spark — raw captured thought, straight from the user's mind. Their words: serif, untouchable. */
data class Capture(
    val id: Long = 0,
    val raw: String,
    val ts: Long = 0,
    val status: CaptureStatus = CaptureStatus.NEW,
    val parsedKind: PlanKind? = null,
    val parsedRef: Long? = null,
    val source: CaptureSource = CaptureSource.TYPED,
)
