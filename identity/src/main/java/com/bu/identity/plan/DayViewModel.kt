package com.bu.identity.plan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bu.core.model.PlanItem
import com.bu.core.model.PlanKind
import com.bu.core.model.PlanState
import com.bu.core.model.Track
import com.bu.core.plan.CarryLogic
import com.bu.core.settings.BuSettingsStore
import com.bu.core.time.BUTime
import com.bu.identity.data.PlanRepository
import com.bu.identity.quick.BuQuickActions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The Day Arc's brain: one selected day (today or tomorrow), its plan items,
 * the tracks they hang from, and the plan editor's open/closed state.
 * The pebble's quick requests land here too.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DayViewModel @Inject constructor(
    private val repo: PlanRepository,
    settingsStore: BuSettingsStore,
    private val quick: BuQuickActions,
) : ViewModel() {

    val settings = settingsStore.settings

    private val _day = MutableStateFlow(BUTime.todayEpochDay())
    val day: StateFlow<Long> = _day.asStateFlow()

    val tracks: StateFlow<List<Track>> = repo.observeActiveTracks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val items: StateFlow<List<PlanItem>> = _day
        .flatMapLatest { selected -> repo.observeDay(selected) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val newSparks: StateFlow<Int> = repo.observeNewCaptureCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    // ---- editor state (compose-observable, survives rotation inside the VM) ----

    var editorItem: PlanItem? by mutableStateOf(null)
        private set
    var editorOpen: Boolean by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            quick.editorRequest.collect { request ->
                if (request != null) {
                    _day.value = request.day
                    openNew(request.kind)
                    quick.consume()
                }
            }
        }
    }

    fun selectDay(value: Long) {
        _day.value = value
    }

    fun openNew(kind: PlanKind) {
        editorItem = PlanItem(date = _day.value, kind = kind, title = "")
        editorOpen = true
    }

    fun openEdit(item: PlanItem) {
        editorItem = item
        editorOpen = true
    }

    fun closeEditor() {
        editorOpen = false
        editorItem = null
    }

    fun save(item: PlanItem) {
        viewModelScope.launch {
            repo.saveItem(item)
            closeEditor()
        }
    }

    fun delete(item: PlanItem) {
        viewModelScope.launch {
            repo.deleteItem(item)
            closeEditor()
        }
    }

    fun setState(item: PlanItem, state: PlanState) {
        viewModelScope.launch { repo.setItemState(item, state) }
    }

    fun carry(item: PlanItem) {
        viewModelScope.launch { repo.carryItem(item, targetDay = item.date + 1) }
    }

    /** Big Three headroom — the editor's save gate reads this. */
    fun canAddBig(): Boolean = CarryLogic.canAddBig(items.value, _day.value)
}
