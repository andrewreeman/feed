package com.stepwise.feed.ui.mainpage

import android.content.res.Resources
import com.stepwise.feed.R
import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel

class Presenter(private val model: MainPageMVP.Model, private val resources: Resources): MainPageMVP.Presenter {
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

    override fun onAddItemTapped() {
        view?.navigateToAddItem()
    }

    override fun createNewItem(title: String, description: String) {
        var titleError: String? = null
        var descriptionError: String? = null

        if(title.isEmpty()) {
            titleError = resources.getString(R.string.new_item_error_title_empty)
        }

        if(description.isEmpty()) {
            descriptionError = resources.getString(R.string.new_item_error_description_empty)
        }

        if(titleError != null || descriptionError != null) {
            view?.createNewItemError(CreateNewItemErrorViewModel(titleError, descriptionError))
        }
        else {
            val newItem = model.createNewItem(title, description)
            view?.onNewItemCreated(newItem)
        }
    }
}