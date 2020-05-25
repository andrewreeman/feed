package com.stepwise.quotes.repository

import com.stepwise.quotes.api.QuoteApi
import com.stepwise.quotes.api.QuoteApiModel
import com.stepwise.quotes.domain.model.Quote

class NetworkRepository(private val api: QuoteApi): Repository {
    override suspend fun loadQuotes(): List<Quote> {
        return api.getContent().map { Quote.fromApiContent(it) }
    }

    override suspend fun saveNew(q: Quote): Quote {
        val savedContent = api.saveNewContent(QuoteApiModel(q.id, q.author, q.quote))
        return Quote.fromApiContent(savedContent)
    }
}