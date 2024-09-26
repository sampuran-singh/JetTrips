package com.example.jettrips.model

data class TravelBlog(
    val name: String,
    val url: String,
    val img: String?,
    val description: String
)

data class FamousTravelBlogs(
    val famous_travel_blogs: List<TravelBlog>
)
