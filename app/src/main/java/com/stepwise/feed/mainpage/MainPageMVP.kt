package com.stepwise.feed.mainpage

import androidx.lifecycle.ViewModel

interface MainPageMVP {
    interface View {
        fun updateContent(viewModel: MainPageViewModel)
    }

    interface Presenter {
        fun loadContent()
    }

    interface Model {
        fun getContent(): List<ViewModel>
    }
}