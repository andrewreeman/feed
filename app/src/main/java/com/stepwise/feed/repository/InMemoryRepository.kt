package com.stepwise.feed.repository

import com.stepwise.feed.domain.model.Content

class InMemoryRepository: Repository {
    private var _content = ArrayList<Content>()

    override suspend fun loadContent(): List<Content> {

        if(_content.isEmpty()) {
            for (i in 0..20) {
                _content.add(
                    Content(
                        "Title " + i,
                        "Description " + i
                    )
                )
            }
        }

        return _content
    }

    override suspend fun saveNew(c: Content): Content {
        _content.add(c)
        return c
    }
}