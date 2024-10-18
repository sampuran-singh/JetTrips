package com.example.jettrips.flights.model

import android.content.Context
import android.util.Log
import com.example.jettrips.utils.DispatcherProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class FlightRepo @Inject constructor(
    @ApplicationContext private val context: Context,
    private val flightDao: FlightDao,
    private val dispatcherProvider: DispatcherProvider
) : IFlightRepo {

    override suspend fun putAllOperatingAirports() {
        return withContext(dispatcherProvider.io) {
            val jsonString = loadJSONFromAsset("airports.json")
            val airports = parseOperatingCities(jsonString)
            flightDao.putAllOperatingCity(airports)
        }

    }

    override suspend fun getAllOperatingAirports(): List<OperatingCity> {
        return withContext(dispatcherProvider.io) {
            flightDao.getAllOperatingCities()
        }
    }

    override suspend fun searchAirport(name: String): List<OperatingCity> {
        return withContext(dispatcherProvider.io) {
            flightDao.searchCities(name)
        }
    }


    override suspend fun searchFlight(): List<Flight> {
        TODO("Not yet implemented")
    }

    private fun loadJSONFromAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }

    private fun parseOperatingCities(jsonString: String): List<OperatingCity> {
        val operatingCities = mutableListOf<OperatingCity>()

        try {
            val json = JSONObject(jsonString)
            if (json.has("airports")) {
                val jsonArray = json.getJSONArray("airports")

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val cityName = jsonObject.optString("cityName", "NA")
                    val country = jsonObject.optString("country", "NA")
                    val iataCode = jsonObject.optString("iataCode", "NA")
                    val timezone = jsonObject.optString("timezone", "NA")
                    val isInternational = jsonObject.optBoolean("isInternational", false)

                    val operatingCity =
                        OperatingCity(iataCode, cityName, country, timezone, isInternational)
                    operatingCities.add(operatingCity)
                }
            }
        } catch (e: Exception) {
            Log.e("JSONParsing", "Error parsing JSON: ${e.message}")
        }
        return operatingCities
    }
}
