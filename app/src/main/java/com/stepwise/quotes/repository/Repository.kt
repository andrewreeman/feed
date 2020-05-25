package com.stepwise.quotes.repository

import com.stepwise.quotes.domain.model.Quote

interface Repository {
    suspend fun loadQuotes(): List<Quote>
    suspend fun saveNew(q: Quote): Quote
}