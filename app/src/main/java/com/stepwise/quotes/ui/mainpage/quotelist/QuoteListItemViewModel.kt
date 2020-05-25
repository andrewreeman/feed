package com.stepwise.quotes.ui.mainpage.quotelist

import com.stepwise.quotes.domain.model.Quote

data class QuoteListItemViewModel(var id: Int, val title: String, val description: String) {
    companion object {
        fun fromContent(c: Quote): QuoteListItemViewModel {
            return QuoteListItemViewModel(c.id, c.author, c.quote)
        }
    }
}