package com.example.jettrips.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrips.repo.HomePageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homePageRepo: HomePageRepo) : ViewModel() {

    private val _blogsUiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
    val blogsUiState: State<HomeUiState> = _blogsUiState

    private val _destinationUiState = mutableStateOf<HomeUiState>(HomeUiState.Loading)
    val destinationUiState: State<HomeUiState> = _destinationUiState

    init {
        getTouristDestination()
        getTravelBlogs()
    }

    private fun getTouristDestination() {
        viewModelScope.launch {
            _destinationUiState.value = HomeUiState.Loading
            try {
                val listOfTourist = homePageRepo.getMostVisitedTouristDestination()
                _destinationUiState.value = HomeUiState.Success(listOfTourist)
            } catch (e: Exception) {
                _destinationUiState.value =
                    HomeUiState.Error("Failed to load tourist destinations: ${e.message}")
            }
        }
    }

    private fun getTravelBlogs() {
        viewModelScope.launch {
            _blogsUiState.value = HomeUiState.Loading
            try {
                val listOfBlogs = homePageRepo.getTravelBlogs()
                _blogsUiState.value = HomeUiState.Success(listOfBlogs)
            } catch (e: Exception) {
                _blogsUiState.value = HomeUiState.Error("Failed to load travel blogs: ${e.message}")
            }
        }
    }
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val data: List<Any>) :
        HomeUiState()  // `data` can hold any type, be it `TouristDestination` or `TravelBlog`

    data class Error(val message: String) : HomeUiState()
}
