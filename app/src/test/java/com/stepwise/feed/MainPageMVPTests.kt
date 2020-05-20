package com.stepwise.feed

import com.stepwise.feed.mainpage.MainPageMVP
import com.stepwise.feed.mainpage.MainPageViewModel
import com.stepwise.feed.mainpage.Presenter
import com.stepwise.feed.mainpage.contentlist.ContentListItemViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MainPageMVPTests {
    private lateinit var mockModel: MainPageMVP.Model
    private lateinit var mockView: MainPageMVP.View
    private lateinit var presenter: Presenter
    private lateinit var item: ContentListItemViewModel

    @Before
    fun setup() {
        mockModel = mock(MainPageMVP.Model::class.java)
        mockView = mock(MainPageMVP.View::class.java)
        presenter = Presenter(mockModel)
        presenter.setView(mockView)

        item = ContentListItemViewModel("A title", "A description")
    }

    @Test
    fun loadContentFromRepositoryIntoViewWhenContentRequested() {
        val listToSupply = listOf(item)
        val viewModelToExpect = MainPageViewModel(listToSupply)

        `when`(mockModel.getContent()).thenReturn(listToSupply)

        presenter.loadContent()

        verify(mockModel, times(1)).getContent()
        verify(mockView, times(1)).updateContent(viewModelToExpect)
    }
}
