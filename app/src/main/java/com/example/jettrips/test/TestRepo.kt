package com.example.jettrips.test

import com.example.jettrips.utils.DispatcherProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TestRepo @Inject constructor(dispatcherProvider: DispatcherProvider) {

    // Simulating a suspend function for fetching data asynchronously
    suspend fun fetchDataFromNetwork(): String {
        // Simulate data fetching logic
        return "Loaded Data"
    }

    suspend fun getCountryList(): Flow<String> = flow {
        // Simulate network delay
        delay(1000)
        emit("Country: Australia")
        delay(2000)
        emit("Country: India")
        delay(2000)
        emit("Country: USA")
    }

    fun pollData(): Flow<String> = flow {
        var index = 1
        while (index < 100) {
            // Simulate API call
            val apiResponse = "Weather Data at ${index}"
            emit(apiResponse)
            index++
            delay(2000) // Poll every 2 seconds
        }
    }

}