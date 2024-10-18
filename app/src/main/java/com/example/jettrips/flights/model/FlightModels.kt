package com.example.jettrips.flights.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Flight(
    @PrimaryKey
    val flightNumber: String,
    val airline: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: String, // Use LocalDateTime for better time handling
    val arrivalTime: String,   // Use LocalDateTime for better time handling
    val duration: String,      // e.g., "2h 30m"
    val price: Double,
    val availableSeats: Int,
    val isDirect: Boolean
)

@Entity
data class OperatingCity(
    @PrimaryKey
    val iataCode: String, // IATA code for the airport
    val cityName: String,
    val country: String,
    val timezone: String, // Optional: Timezone of the city
    val isInternational: Boolean // Optional: Indicates if the city has an international airport
)
