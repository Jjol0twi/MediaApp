package com.example.mediaapp

import com.example.mediaapp.model.YoutubeSearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {
    @GET("search")
    suspend fun responseSearch(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,
        @Query("regionCode") regionCode: String,
    ): YoutubeSearchModel

}