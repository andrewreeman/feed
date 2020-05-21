package com.stepwise.feed.repository

import com.stepwise.feed.api.ContentApi
import com.stepwise.feed.api.ContentApiModel
import com.stepwise.feed.domain.model.Content

class NetworkRepository(private val api: ContentApi): Repository {
    override suspend fun loadContent(): List<Content> {
        return api.getContent().map { Content.fromApiContent(it) }
    }

    override suspend fun saveNew(c: Content): Content {
        val savedContent = api.saveNewContent(ContentApiModel(c.id, c.title, c.description))
        return Content.fromApiContent(savedContent)
    }
}