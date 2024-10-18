package com.example.jettrips.flights.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val flightRepo: FlightRepo) : ViewModel() {

    init {
        getAirportList()
    }

    private var _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery

    private var _isSearchedClicked = mutableStateOf(false)
    val isSearchedClicked: State<Boolean> get() = _isSearchedClicked

    private var _searchResults = mutableStateOf<List<OperatingCity>>(emptyList())
    val searchResults: State<List<OperatingCity>> get() = _searchResults

    fun getAirportList() {
        viewModelScope.launch {
            val countries = flightRepo.getAllOperatingAirports()
            _searchResults.value = countries
        }
    }

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
        viewModelScope.launch {
            val countries = flightRepo.searchAirport(newQuery)
            _searchResults.value = countries
        }
    }

    fun onSearchClicked() {
        _isSearchedClicked.value = true
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _isSearchedClicked.value = false
    }
}