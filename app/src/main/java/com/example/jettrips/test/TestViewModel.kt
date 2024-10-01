package com.example.jettrips.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject


@HiltViewModel
class TestViewModel @Inject constructor(val testRepo: TestRepo) : ViewModel() {

    // Flow to emit data to the UI
    private val _dataFlow = MutableStateFlow<String>("")
    val dataFlow: StateFlow<String> = _dataFlow

    private val _countryFlow = MutableStateFlow<String>("")
    val countryFlow: StateFlow<String> = _countryFlow

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.d("21000", "Caught: $exception")
    }

    private val _weatherData = MutableStateFlow<String>("")
    val weatherData: StateFlow<String> = _weatherData

    // Job for managing the coroutine
    private var job: Job? = null

    private var newJob: Job? = null

    init {
        viewModelScope.launch {
            testRepo.pollData()
                .conflate()
                .collect { data ->
                    delay(5000)
                    _weatherData.value = data
                }
        }
    }

    fun getNameOfCountry() {
        job = viewModelScope.launch(Dispatchers.IO) {
            testRepo.getCountryList().collect{
                _countryFlow.value = it
            }
        }
    }

    fun loadNewData() {
        newJob?.cancel()

        newJob = viewModelScope.launch {

        }
    }


    fun main() = runBlocking {
        // Launch a coroutine and get its Job
        val job = launch {
            repeat(5) { i ->
                delay(500)
                Log.d("21000", "Coroutine is working... $i")
            }
        }

        // Delay to let the coroutine do some work
        delay(1000)

        // Cancel the coroutine after 1 second
        Log.d("21000", "Cancelling the coroutine...")

        job.cancel()

        // Wait for the job to complete
        job.join()

        Log.d("21000", "Job is completed: ${job.isCompleted}")
    }


    fun mainSuperTest() = runBlocking {
        // Create a SupervisorJob for the scope
//        val supervisor = SupervisorJob()
        val supervisor = Job()

        // Launch a coroutine scope with the SupervisorJob
        val scope = CoroutineScope(Dispatchers.Default + supervisor + exceptionHandler)


        // Child coroutine 1 - Throws an exception
        val child1 = scope.launch {
            Log.d("21000", "Child 1 is starting...")
            delay(500)
            throw Exception("Child 1 failed!")
        }

        // Child coroutine 2 - Continues working even if child1 fails
        val child2 = scope.launch {
            Log.d("21000", "Child 2 is starting...")

            repeat(5) { i ->
                delay(300)
                Log.d("21000", "Child 2 is working... $i")
            }
        }

        // Wait for child1 to complete
        child1.join()

        Log.d("21000", "Child 1 is completed, isCancelled: ${child1.isCancelled}")

        // Wait for child2 to complete
        child2.join()
        Log.d("21000", "Child 2 is completed")
    }


    // Function to load data asynchronously using coroutine
    fun loadData() {
        // Cancel any previous running job before starting a new one
        job?.cancel()

        // Start a new coroutine in the viewModelScope (lifecycle-aware)
        job = viewModelScope.launch(Dispatchers.IO) {
            // Simulating a network or database operation
            delay(2000) // Simulate delay
            val result = testRepo.fetchDataFromNetwork()
            // Emit the result to the flow
            _dataFlow.emit(result)
        }
    }


    // Cancel the job when ViewModel is cleared
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun loadDataConcurrently() {
        viewModelScope.launch {
            // Run two tasks concurrently
            val task1 = async(Dispatchers.IO) { fetchDataFromNetworkAsync() }
            val task2 = async(Dispatchers.IO) { fetchAnotherData() }

            // Wait for both tasks to complete
            val result1 = task1.await()
            val result2 = task2.await()

            // Combine results and emit to the flow
            _dataFlow.emit("$result1 and $result2")
        }
    }

    private suspend fun fetchDataFromNetworkAsync(): String {
        delay(2000)
        return "Data 1"
    }

    private suspend fun fetchAnotherData(): String {
        delay(1000)
        return "Data 2"
    }


}


