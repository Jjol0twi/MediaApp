package com.example.mediaapp

import com.example.mediaapp.data.api.RetrofitInstance.api
import com.example.mediaapp.data.model.SearchResponse
import retrofit2.Response

class SearchRepositoryImpl:SearchRepository {
    override suspend fun search(
        key : String,
        q: String,
        chart: String,
        hl: String,
        maxResults: Int,
        regionCode: String
    ): Response<SearchResponse> {
        return api.searchYoutube(key,q, chart, hl, maxResults, regionCode)
    }
}