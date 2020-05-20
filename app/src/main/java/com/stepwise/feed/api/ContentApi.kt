package com.stepwise.feed.api

import retrofit2.http.GET
import retrofit2.http.POST

interface ContentApi {
    @GET("/api/v2/content")
    fun getContent(): List<ContentApiModel>

    @POST("/api/v2/content")
    fun saveNewContent(title: String, description: String): ContentApiModel
}