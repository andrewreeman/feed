package com.stepwise.feed.repository

import com.stepwise.feed.domain.model.Quote

interface Repository {
    suspend fun loadQuotes(): List<Quote>
    suspend fun saveNew(q: Quote): Quote
}