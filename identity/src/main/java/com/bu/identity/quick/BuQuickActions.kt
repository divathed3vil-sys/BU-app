package com.bu.identity.quick

import com.bu.core.model.PlanKind
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * One-shot requests from the pebble to whichever screen can honor them.
 * The pebble opens doors; it never duplicates what's behind them.
 */
@Singleton
class BuQuickActions @Inject constructor() {

    data class EditorRequest(val kind: PlanKind, val day: Long)

    private val _editorRequest = MutableStateFlow<EditorRequest?>(null)
    val editorRequest: StateFlow<EditorRequest?> = _editorRequest.asStateFlow()

    fun requestEditor(kind: PlanKind, day: Long) {
        _editorRequest.value = EditorRequest(kind, day)
    }

    fun consume() {
        _editorRequest.value = null
    }
}
