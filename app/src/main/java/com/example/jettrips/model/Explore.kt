package com.example.jettrips.model

data class TouristDestination(
    val name: String,
    val location: String,
    val img: String,
    val description: String
)

data class MostVisitedIndia(
    val most_visited_india: List<TouristDestination>
)
