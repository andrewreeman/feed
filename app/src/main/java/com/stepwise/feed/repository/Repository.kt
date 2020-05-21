package com.stepwise.feed.repository

import com.stepwise.feed.domain.model.Content

interface Repository {
    suspend fun loadContent(): List<Content>
    suspend fun saveNew(c: Content): Content
}