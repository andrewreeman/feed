@file:Suppress("RemoveRedundantQualifierName")

package com.stepwise.feed.mainpage

import com.stepwise.feed.mainpage.contentlist.ContentListItemViewModel

interface MainPageMVP {
    interface View {
        fun updateContent(viewModel: MainPageViewModel)
        fun navigateToAddItem()
    }

    interface Presenter {
        fun setView(view: MainPageMVP.View)
        fun loadContent()
        fun onAddItemTapped()
    }

    interface Model {
        fun getContent(): List<ContentListItemViewModel>
    }
}