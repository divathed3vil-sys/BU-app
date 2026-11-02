package com.bu.identity.loop

/**
 * The BU Loop — CAPTURE → PLAN → DO → CLOSE → GROW (locked plan §1).
 * LOOM maps it to: Intention → Organise/Plan → Act → Reflect → Adapt.
 */
enum class BuLoopStage(val userFacingName: String, val loomName: String) {
    CAPTURE("Capture", "Intention"),
    PLAN("Plan", "Organise"),
    DO("Do", "Act"),
    CLOSE("Close", "Reflect"),
    GROW("Grow", "Adapt"),
    ;

    companion object {
        fun cycle(): List<BuLoopStage> = entries.toList()
    }
}
