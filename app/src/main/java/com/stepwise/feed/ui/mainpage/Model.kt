package com.stepwise.feed.ui.mainpage

import com.stepwise.feed.domain.model.Content
import com.stepwise.feed.repository.Repository
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel


class Model(private val repository: Repository): MainPageMVP.Model {
    override suspend fun getContent(): List<ContentListItemViewModel> {
        return repository.loadContent().map { ContentListItemViewModel.fromContent(it)}
    }

    override suspend fun createNewItem(title: String, description: String): ContentListItemViewModel {
        val createdContent = repository.saveNew(Content(title, description))
        return ContentListItemViewModel.fromContent(createdContent)
    }
}