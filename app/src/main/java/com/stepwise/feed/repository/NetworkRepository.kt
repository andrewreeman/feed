package com.stepwise.feed.repository

import com.stepwise.feed.api.ContentApi
import com.stepwise.feed.domain.model.Content

class NetworkRepository(private val api: ContentApi): Repository {
    override fun loadContent(): List<Content> {
        return api.getContent().map { Content(it.title, it.description) }
    }

    override fun saveNew(c: Content): Content {
        val savedContent = api.saveNewContent(c.title, c.description)
        return Content(savedContent.title, savedContent.description)
    }
}