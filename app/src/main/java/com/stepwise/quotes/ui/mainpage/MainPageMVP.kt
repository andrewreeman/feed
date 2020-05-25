@file:Suppress("RemoveRedundantQualifierName")

package com.stepwise.quotes.ui.mainpage

import com.stepwise.quotes.domain.model.Quote
import com.stepwise.quotes.ui.mainpage.addquote.CreateQuoteErrorViewModel
import com.stepwise.quotes.ui.mainpage.quotelist.QuoteListItemViewModel

interface MainPageMVP {
    interface View {
        fun updateContent(viewModel: MainPageViewModel)
        fun navigateToAddItem()
        fun onNewItemCreated(newItem: QuoteListItemViewModel)
        fun createNewItemError(error: CreateQuoteErrorViewModel)
    }

    interface Presenter {
        fun setView(view: MainPageMVP.View)
        fun onAddItemTapped()
        fun validateNewItem(title: String, description: String): CreateQuoteErrorViewModel?
        suspend fun loadContent()
        suspend fun createNewItem(title: String, description: String)
    }

    interface Model {
        suspend fun createNewItem(title: String, description: String): Quote
        suspend fun getContent(): List<Quote>
    }
}