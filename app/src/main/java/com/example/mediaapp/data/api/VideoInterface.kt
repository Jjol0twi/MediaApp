package com.example.mediaapp.data.api


import com.example.mediaapp.Constants
import com.example.mediaapp.data.model.channel.ChannelResponse
import com.example.mediaapp.data.model.video.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoInterface {

    @GET("videos")
    suspend fun searchYoutube(
        @Query("key") key: String = Constants.API_KEY,
        @Query("part") part: String,
        @Query("videoCategoryId") videoCategoryId :String,
        @Query("chart") chart:String,
        @Query("hl") hl:String,
        @Query("maxResults")maxResults:Int,
        @Query("regionCode")regionCode:String,
    ) : Response<SearchResponse>

    @GET("channels")
    suspend fun searchChannel(
        @Query("key") key : String = Constants.API_KEY,
        @Query("part") part : String,
        @Query("hl") hl : String,
        @Query("id") id : String,
    ): Response<ChannelResponse>
}

