package com.bu.identity.model

/**
 * The universal object model kinds (locked plan §6.1).
 * Everything in BU is an object with a stable identity and typed relationships.
 */
enum class ObjectKind {
    TRACK,
    TASK,
    NOTE,
    THREAD,
    EXAM,
    PERSON,
    VIRTUE,
}

/** Task classes: Big Three · Blocks · Sparks · Exam prep (locked plan §3). */
enum class TaskClass { BIG, BLOCK, SPARK, EXAM }

/** Tally Trio — right = Done, left = Carry, down = Partial (locked plan §4.3). */
enum class TallyState { DONE, CARRIED, PARTIAL }

data class Track(
    val id: String,
    val name: String,
    val estimateMinutes: Int = 30,
    val estimateSamples: Int = 0,
)

data class BigThreeSlot(
    val title: String,
    val isMainFront: Boolean = false,
    val timeSlot: String? = null,
)

/**
 * The Big Three: at most three daily priorities, exactly one Main Front.
 * Enforced here in pure Kotlin so no screen can violate it.
 */
object BigThreeRules {

    fun validate(slots: List<BigThreeSlot>): List<String> {
        val errors = mutableListOf<String>()
        if (slots.size > 3) errors += "MAX_THREE"
        val fronts = slots.count { it.isMainFront }
        if (slots.isNotEmpty() && fronts != 1) errors += "EXACTLY_ONE_MAIN_FRONT"
        if (slots.any { it.title.isBlank() }) errors += "EMPTY_TITLE"
        return errors
    }

    fun isValid(slots: List<BigThreeSlot>): Boolean = validate(slots).isEmpty()
}
