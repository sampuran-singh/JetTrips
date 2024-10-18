package com.example.jettrips.flights.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class FlightDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun putAllOperatingCity(countries: List<OperatingCity>)

    @Query("Select * from OperatingCity")
    abstract fun getAllOperatingCities(): List<OperatingCity>

    @Query("SELECT * FROM OperatingCity WHERE cityName LIKE '%' || :query || '%'")
    abstract fun searchCities(query: String): List<OperatingCity>
}


