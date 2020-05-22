package com.stepwise.feed.ui.mainpage.quotelist

import com.stepwise.feed.domain.model.Quote

data class QuoteListItemViewModel(var id: Int, val title: String, val description: String) {
    companion object {
        fun fromContent(c: Quote): QuoteListItemViewModel {
            return QuoteListItemViewModel(c.id, c.author, c.quote)
        }
    }
}