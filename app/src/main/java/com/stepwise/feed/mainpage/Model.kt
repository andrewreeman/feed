package com.stepwise.feed.mainpage

import com.stepwise.feed.mainpage.contentlist.ContentListItemViewModel


class Model: MainPageMVP.Model {
    private var _content = ArrayList<ContentListItemViewModel>()

    override fun getContent(): List<ContentListItemViewModel> {

        if(_content.isEmpty()) {
            for (i in 0..20) {
                _content.add(
                    ContentListItemViewModel(
                        "Title " + i,
                        "Description " + i
                    )
                )
            }
        }

        return _content
    }

    override fun createNewItem(title: String, description: String): ContentListItemViewModel {
        val newContent = ContentListItemViewModel(title, description)
        _content.add(newContent)
        return newContent
    }

}