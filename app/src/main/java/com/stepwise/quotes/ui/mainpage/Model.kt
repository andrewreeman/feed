package com.stepwise.quotes.ui.mainpage

import android.util.Log
import com.stepwise.quotes.domain.model.Quote
import com.stepwise.quotes.repository.Repository

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