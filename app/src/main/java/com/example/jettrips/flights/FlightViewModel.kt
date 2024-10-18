package com.example.jettrips.flights

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrips.flights.model.FlightRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(val flightRepo: FlightRepo) : ViewModel() {

    init {
        saveAllAirports()
    }

    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState: State<UiState> = _uiState

    fun getDefaultStart(): String {
        return "BLR Kempegowda International Airport"
    }

    private fun saveAllAirports() {
        viewModelScope.launch {
            flightRepo.putAllOperatingAirports()
        }

    }

    fun getDefaultDestination(): String {
        return "DEL Delhi International Airport"

    }

}

sealed class UiState {
    data object Loading : UiState()
    data class Success(val countries: List<Any>) : UiState()
    data class Error(val message: String) : UiState()
}