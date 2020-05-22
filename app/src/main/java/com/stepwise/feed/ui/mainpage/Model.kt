package com.stepwise.feed.ui.mainpage

import android.util.Log
import com.stepwise.feed.domain.model.Quote
import com.stepwise.feed.repository.Repository

class Model(private val repository: Repository): MainPageMVP.Model {
    override suspend fun getContent(): List<Quote> {
        return try {
            repository.loadQuotes()
        } catch (e: Exception) {
            Log.e("Model", e.toString())
            ArrayList()
        }
    }

    override suspend fun createNewItem(title: String, description: String): Quote {
        return repository.saveNew(Quote(-1, title, description))
    }
}