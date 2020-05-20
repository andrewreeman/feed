package com.stepwise.feed.mainpage

class Presenter(private val model: MainPageMVP.Model): MainPageMVP.Presenter {
    private var view: MainPageMVP.View? = null

    override fun setView(view: MainPageMVP.View) {
        this.view = view
    }

    override fun loadContent() {
        view?.let {
            val content = model.getContent()
            it.updateContent(MainPageViewModel(content))
        }
    }
}