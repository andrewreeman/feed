package com.stepwise.feed.ui.mainpage

import android.util.Log
import com.stepwise.feed.domain.model.Content
import com.stepwise.feed.repository.Repository

class Model(private val repository: Repository): MainPageMVP.Model {
    override suspend fun getContent(): List<Content> {
        return try {
            repository.loadContent()
        } catch (e: Exception) {
            Log.e("Model", e.toString())
            ArrayList()
        }
    }

    override suspend fun createNewItem(title: String, description: String): Content {
        return repository.saveNew(Content(-1, title, description))
    }
}