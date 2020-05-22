package com.stepwise.feed.domain.model

import com.stepwise.feed.api.QuoteApiModel

data class Quote(val id: Int, val author: String, val quote: String) {
    companion object {
        fun fromApiContent(apiQuote: QuoteApiModel) : Quote {
            return Quote(apiQuote.id, apiQuote.author, apiQuote.quote)
        }
    }
}
