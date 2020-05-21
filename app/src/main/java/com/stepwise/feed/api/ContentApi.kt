package com.stepwise.feed.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContentApi {
    @GET("/api/v2/content")
    suspend fun getContent(): List<ContentApiModel>

    @POST("/api/v2/content")
    suspend fun saveNewContent(@Body newContent: ContentApiModel): ContentApiModel
}