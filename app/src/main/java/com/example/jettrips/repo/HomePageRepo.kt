package com.example.jettrips.repo

import android.content.Context
import com.example.jettrips.model.TouristDestination
import com.example.jettrips.model.TravelBlog
import com.example.jettrips.utils.DispatcherProvider
import com.example.jettrips.utils.parseTouristDestination
import com.example.jettrips.utils.parseTravelBlog
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HomePageRepo @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcherProvider: DispatcherProvider
) : ITravelBlogs, ITouristDestinationRepo {


    override suspend fun getTravelBlogs(): List<TravelBlog> {

        return withContext(dispatcherProvider.io) {
            loadJSONFromAsset("travel_blogs.json").parseTravelBlog()
        }
    }

    override suspend fun getMostVisitedTouristDestination(): List<TouristDestination> {

        return withContext(dispatcherProvider.io) {
            loadJSONFromAsset("explore_india.json").parseTouristDestination()
        }

    }

    private fun loadJSONFromAsset(fileName: String): String {

        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
}

