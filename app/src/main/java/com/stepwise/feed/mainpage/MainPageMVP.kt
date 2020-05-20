package com.stepwise.feed.mainpage

import androidx.lifecycle.ViewModel
import com.stepwise.feed.mainpage.contentlist.ContentListItemViewModel

interface MainPageMVP {
    interface View {
        fun updateContent(viewModel: MainPageViewModel)
    }

    interface Presenter {
        fun setView(view: MainPageMVP.View)
        fun loadContent()
    }

    interface Model {
        fun getContent(): List<ContentListItemViewModel>
    }
}