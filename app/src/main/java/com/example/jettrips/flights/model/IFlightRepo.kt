package com.example.jettrips.flights.model

interface IFlightRepo {

    suspend fun putAllOperatingAirports()

    suspend fun getAllOperatingAirports(): List<OperatingCity>

    suspend fun searchAirport(name: String): List<OperatingCity>

    suspend fun searchFlight(): List<Flight>

}