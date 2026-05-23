package pl.polutek.suweren.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.polutek.suweren.data.ScoringLogic
import pl.polutek.suweren.data.SuwerenData
import pl.polutek.suweren.data.SuwerenRepository
import java.time.Instant

class SuwerenViewModel(private val repository: SuwerenRepository) : ViewModel() {

    val uiState: StateFlow<SuwerenData> = repository.suwerenData
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SuwerenData())

    private val _pIndex = MutableStateFlow(0)
    val pIndex: StateFlow<Int> = _pIndex.asStateFlow()

    init {
        viewModelScope.launch {
            combine(uiState, flow {
                while(true) {
                    emit(Unit)
                    delay(60000) // Update every minute
                }
            }) { data, _ ->
                ScoringLogic.calculatePIndex(data)
            }.collect {
                _pIndex.value = it
            }
        }
    }

    fun resetNicotine() = viewModelScope.launch { repository.resetNicotine() }
    fun resetThc() = viewModelScope.launch { repository.resetThc() }
    fun resetNoFap() = viewModelScope.launch { repository.resetNoFap() }
    fun addRunningKm(km: Float) = viewModelScope.launch { repository.addRunningKm(km) }

    fun setLastReset(type: String, instant: Instant) = viewModelScope.launch {
        repository.setLastReset(type, instant)
    }
}
