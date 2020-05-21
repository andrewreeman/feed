@file:Suppress("RemoveRedundantQualifierName")

package com.stepwise.feed.ui.mainpage

import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel

interface MainPageMVP {
    interface View {
        fun updateContent(viewModel: MainPageViewModel)
        fun navigateToAddItem()
        fun onNewItemCreated(newItem: ContentListItemViewModel)
        fun createNewItemError(error: CreateNewItemErrorViewModel)
    }

    interface Presenter {
        fun setView(view: MainPageMVP.View)
        fun loadContent()
        fun onAddItemTapped()
        fun createNewItem(title: String, description: String)
        fun onDestroy()
    }

    interface Model {
        suspend fun createNewItem(title: String, description: String): ContentListItemViewModel
        suspend fun getContent(): List<ContentListItemViewModel>
    }
}