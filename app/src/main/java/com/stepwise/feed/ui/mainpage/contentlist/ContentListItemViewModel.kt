package com.stepwise.feed.ui.mainpage.contentlist

import com.stepwise.feed.domain.model.Content

data class ContentListItemViewModel(var id: Int, val title: String, val description: String) {
    companion object {
        fun fromContent(c: Content): ContentListItemViewModel {
            return ContentListItemViewModel(c.id, c.title, c.description)
        }
    }
}