package com.stepwise.feed.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuoteApi {
    @GET("/api/v2/quote")
    suspend fun getContent(): List<QuoteApiModel>

    @POST("/api/v2/quote")
    suspend fun saveNewContent(@Body newQuote: QuoteApiModel): QuoteApiModel
}