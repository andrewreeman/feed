@file:Suppress("RemoveRedundantQualifierName")

package com.stepwise.feed.ui.mainpage

import com.stepwise.feed.domain.model.Quote
import com.stepwise.feed.ui.mainpage.addquote.CreateQuoteErrorViewModel
import com.stepwise.feed.ui.mainpage.quotelist.QuoteListItemViewModel

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