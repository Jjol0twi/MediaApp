package com.example.mediaapp.data.model


import com.google.gson.annotations.SerializedName

data class Snippet(
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("channelTitle")
    val channelTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("liveBroadcastContent")
    val liveBroadcastContent: String,
    @SerializedName("localized")
    val localized: Localized,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,
    @SerializedName("title")
    val title: String
)

data class Localized(
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)