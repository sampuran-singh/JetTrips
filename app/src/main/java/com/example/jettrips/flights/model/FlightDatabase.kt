package com.example.jettrips.flights.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Flight::class, OperatingCity::class], version = 1)
abstract class FlightDatabase() : RoomDatabase() {

    abstract fun get(): FlightDao

}