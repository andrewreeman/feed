package com.stepwise.feed.ui.mainpage

import com.stepwise.feed.domain.model.Content
import com.stepwise.feed.repository.Repository
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel


class Model(private val repository: Repository): MainPageMVP.Model {
    override suspend fun getContent(): List<Content> {
        return repository.loadContent()
    }

    override suspend fun createNewItem(title: String, description: String): Content {
        return repository.saveNew(Content(-1, title, description))
    }
}