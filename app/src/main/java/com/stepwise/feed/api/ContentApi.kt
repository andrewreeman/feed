package com.stepwise.feed.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ContentApi {
    @GET("/api/v2/content")
    fun getContent(): Call<List<ContentApiModel>>

    @POST("/api/v2/content")
    fun saveNewContent(title: String, description: String): Call<ContentApiModel>
}