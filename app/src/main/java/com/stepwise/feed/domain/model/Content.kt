package com.stepwise.feed.domain.model

import com.stepwise.feed.api.ContentApiModel

data class Content(val id: Int, val title: String, val description: String) {
    companion object {
        fun fromApiContent(apiContent: ContentApiModel) : Content {
            return Content(apiContent.id, apiContent.title, apiContent.description)
        }
    }
}
