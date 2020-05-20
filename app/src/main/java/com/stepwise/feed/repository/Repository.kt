package com.stepwise.feed.repository

import com.stepwise.feed.domain.model.Content

interface Repository {
    fun loadContent(): List<Content>
    fun saveNew(c: Content): Content
}