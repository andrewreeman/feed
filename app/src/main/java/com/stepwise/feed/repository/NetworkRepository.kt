package com.stepwise.feed.repository

import android.util.Log
import com.stepwise.feed.api.ContentApi
import com.stepwise.feed.api.ContentApiModel
import com.stepwise.feed.domain.model.Content
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository(private val api: ContentApi): Repository {
    override suspend fun loadContent(): List<Content> {
//        val call = api.getContent()
//        call.enqueue( object: Callback<List<ContentApiModel>> {
//            override fun onFailure(call: Call<List<ContentApiModel>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onResponse(
//                call: Call<List<ContentApiModel>>,
//                response: Response<List<ContentApiModel>>
//            ) {
//
//            }
//
//        })
        return api.getContent().map { Content(it.title, it.description) }
    }

    override suspend fun saveNew(c: Content): Content {
        val savedContent = api.saveNewContent(c.title, c.description)
//        return Content(savedContent.title, savedContent.description)
        return Content(savedContent.title, savedContent.description)
    }
}