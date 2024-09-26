package com.example.jettrips.repo

import com.example.jettrips.model.TouristDestination

interface ITouristDestinationRepo {
    suspend fun getMostVisitedTouristDestination(): List<TouristDestination>
}