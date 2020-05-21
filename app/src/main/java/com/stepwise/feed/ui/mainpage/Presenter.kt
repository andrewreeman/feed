package com.stepwise.feed.ui.mainpage

import android.content.res.Resources
import android.util.Log
import com.stepwise.feed.R
import com.stepwise.feed.api.MockApiServer
import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class Presenter(private val model: MainPageMVP.Model, private val resources: Resources): MainPageMVP.Presenter {
    private var view: MainPageMVP.View? = null

    override fun setView(view: MainPageMVP.View) {
        this.view = view
    }

    override suspend fun loadContent() {
        if(view == null) {
            Log.w("Presenter", "Loading content. View is null but continuing anyway")
        }

        val content = model.getContent().map{ ContentListItemViewModel.fromContent(it) }
        view?.updateContent(MainPageViewModel(content))
    }

    override fun onAddItemTapped() {
        view?.navigateToAddItem()
    }

    override fun validateNewItem(title: String, description: String): CreateNewItemErrorViewModel? {
        var titleError: String? = null
        var descriptionError: String? = null

        if(title.isEmpty()) {
            titleError = resources.getString(R.string.new_item_error_title_empty)
        }

        if(description.isEmpty()) {
            descriptionError = resources.getString(R.string.new_item_error_description_empty)
        }

        if(titleError != null || descriptionError != null) {
            return CreateNewItemErrorViewModel(titleError, descriptionError)
        }
        else {
            return null
        }
    }

    override suspend fun createNewItem(title: String, description: String) {
        val validationResult = validateNewItem(title, description)
        if(validationResult != null) {
            view?.createNewItemError(validationResult)
        }
        else {
            val newItem = model.createNewItem(title, description)
            view?.onNewItemCreated(ContentListItemViewModel.fromContent(newItem))
        }
    }
}