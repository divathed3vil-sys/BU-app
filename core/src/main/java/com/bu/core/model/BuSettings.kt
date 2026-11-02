package com.bu.core.model

/**
 * BU settings — the single-row config document (Room in P1; JSON-file store for P0).
 * Defaults per doctrine: day runs 5:00 → 23:00, nothing pre-seeded.
 */
data class BuSettings(
    val username: String = "",
    val dayStartMin: Int = 5 * 60, // 05:00
    val dayEndMin: Int = 23 * 60, // 23:00
    val dawnMin: Int = 6 * 60, // Dawn Push 06:00
    val duskMin: Int = 21 * 60 + 30, // Dusk Close 21:30
    val sundayReviewMin: Int = 10 * 60, // Sunday 10:00
    val examName: String = "",
    val examDateEpochDay: Long? = null, // customizable ACS countdown
    val motionLevel: Int = 2, // 0 calm · 1 standard · 2 full bounce doctrine
    val setupDone: Boolean = false,
) {
    val hasExam: Boolean get() = examName.isNotBlank() && examDateEpochDay != null
}
