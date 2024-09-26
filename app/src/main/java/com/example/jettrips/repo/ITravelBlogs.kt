package com.example.jettrips.repo

import com.example.jettrips.model.TravelBlog

interface ITravelBlogs {

    suspend fun getTravelBlogs(): List<TravelBlog>
}