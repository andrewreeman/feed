package com.stepwise.feed.repository

import com.stepwise.feed.domain.model.Content

class InMemoryRepository: Repository {
    private var _content = ArrayList<Content>()

    override fun loadContent(): List<Content> {

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

    override fun saveNew(c: Content): Content {
        _content.add(c)
        return c
    }
}