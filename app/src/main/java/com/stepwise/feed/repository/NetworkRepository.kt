package com.stepwise.feed.repository

import com.stepwise.feed.api.QuoteApi
import com.stepwise.feed.api.QuoteApiModel
import com.stepwise.feed.domain.model.Quote

class NetworkRepository(private val api: QuoteApi): Repository {
    override suspend fun loadQuotes(): List<Quote> {
        return api.getContent().map { Quote.fromApiContent(it) }
    }

    override suspend fun saveNew(q: Quote): Quote {
        val savedContent = api.saveNewContent(QuoteApiModel(q.id, q.author, q.quote))
        return Quote.fromApiContent(savedContent)
    }
}