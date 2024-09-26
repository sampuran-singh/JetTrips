package com.example.jettrips.utils

import kotlinx.coroutines.CoroutineDispatcher


interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}
