package com.stepwise.quotes.repository

import com.stepwise.quotes.domain.model.Quote

class InMemoryRepository: Repository {
    private var _content = ArrayList<Quote>()

    override suspend fun loadQuotes(): List<Quote> {

        if(_content.isEmpty()) {
            for (i in 0..20) {
                _content.add(
                    Quote(
                        i,
                        "Title " + i,
                        "Description " + i
                    )
                )
            }
        }

        return _content
    }

    override suspend fun saveNew(q: Quote): Quote {
        _content.add(q)
        return q
    }
}