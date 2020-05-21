package com.stepwise.feed.ui.mainpage

import android.content.res.Resources
import com.stepwise.feed.R
import com.stepwise.feed.api.MockApiServer
import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class Presenter(private val model: MainPageMVP.Model, private val resources: Resources): MainPageMVP.Presenter, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val job = Job()

    private var view: MainPageMVP.View? = null

    override fun setView(view: MainPageMVP.View) {
        this.view = view
    }

    override fun loadContent() {
        if(view == null) { return }
        launch {
            val content = model.getContent()
            withContext(Dispatchers.Main) {
                view?.updateContent(MainPageViewModel(content))
            }
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
            launch {
                val newItem = model.createNewItem(title, description)

                withContext(Dispatchers.Main) {
                    view?.onNewItemCreated(newItem)
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
    }
}