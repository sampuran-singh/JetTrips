package com.example.jettrips.utils

import android.util.Log
import com.example.jettrips.model.TouristDestination
import com.example.jettrips.model.TravelBlog
import org.json.JSONObject

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


fun String.parseTravelBlog(): List<TravelBlog> {
    val travelBlogList = mutableListOf<TravelBlog>()

    try {
        val json = JSONObject(this)  // Assuming the root is a JSONObject
        if (json.has("travel_blogs")) {
            val jsonArray = json.getJSONArray("travel_blogs")

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.optString("name", "Unknown")
                val url = jsonObject.optString("url", "No image available")
                val img = jsonObject.optString("img", "No image available")
                val description = jsonObject.optString("description", "No description available")

                val travelBlog = TravelBlog(name, url, img, description)
                travelBlogList.add(travelBlog)
            }
        }
    } catch (e: Exception) {
        Log.e("JSONParsing", "Error parsing JSON: ${e.message}")
    }

    return travelBlogList
}


fun String.parseTouristDestination(): List<TouristDestination> {
    val touristDestinations = mutableListOf<TouristDestination>()

    try {
        val json = JSONObject(this)  // Assuming the root is a JSONObject
        if (json.has("most_visited_india")) {
            val jsonArray = json.getJSONArray("most_visited_india")

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.optString("name", "Unknown")
                val location = jsonObject.optString("location", "Unknown")
                val img = jsonObject.optString("img", "No image available")
                val description = jsonObject.optString("description", "No description available")

                val touristDestination = TouristDestination(name, location, img, description)
                touristDestinations.add(touristDestination)
            }
        }
    } catch (e: Exception) {
        Log.e("JSONParsing", "Error parsing JSON: ${e.message}")
    }

    return touristDestinations
}


