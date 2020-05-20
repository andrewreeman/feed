package com.stepwise.feed.mainpage

import com.stepwise.feed.mainpage.contentlist.ContentListItemViewModel


class Model: MainPageMVP.Model {
    private var lastItemNumber = 0

    override fun getContent(): List<ContentListItemViewModel> {
        val contentItemList = ArrayList<ContentListItemViewModel>()
        for( i in lastItemNumber..lastItemNumber+20) {
            contentItemList.add(
                ContentListItemViewModel(
                    "Title " + i,
                    "Description " + i
                )
            )
        }

        lastItemNumber += 20

        return contentItemList
    }
}