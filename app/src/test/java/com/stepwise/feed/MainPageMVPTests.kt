package com.stepwise.feed

import android.content.res.Resources
import com.stepwise.feed.ui.mainpage.MainPageMVP
import com.stepwise.feed.ui.mainpage.MainPageViewModel
import com.stepwise.feed.ui.mainpage.Presenter
import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel
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
        presenter = Presenter(mockModel, mock(Resources::class.java, RETURNS_MOCKS))
        presenter.setView(mockView)

        item = ContentListItemViewModel("A title", "A description")
    }

    @Test
    fun loadContentFromRepositoryIntoView_WhenContentRequested() {
        val listToSupply = listOf(item)
        val viewModelToExpect = MainPageViewModel(listToSupply)

        `when`(mockModel.getContent()).thenReturn(listToSupply)

        presenter.loadContent()

        verify(mockModel, times(1)).getContent()
        verify(mockView, times(1)).updateContent(viewModelToExpect)
    }

    @Test
    fun navigateToAddItemCalled_WhenOnAddItemTapped() {
        presenter.onAddItemTapped()
        verify(mockView, times(1)).navigateToAddItem()
    }

    @Test
    fun creatingNewItem_WithoutTitle_WillShowError() {
        presenter.createNewItem("", "Description")

        val expectedErrorVM = CreateNewItemErrorViewModel("", null)
        verify(mockView, times(1)).createNewItemError(expectedErrorVM)
        verify(mockView, never()).onNewItemCreated(MockitoHelper.anyObject())
        verify(mockModel, never()).createNewItem(MockitoHelper.anyObject(), MockitoHelper.anyObject())
    }

    @Test
    fun creatingNewItem_WithoutDescription_WillShowError() {
        presenter.createNewItem("Title", "")

        val expectedErrorVM = CreateNewItemErrorViewModel(null, "")
        verify(mockView, times(1)).createNewItemError(expectedErrorVM)
        verify(mockView, never()).onNewItemCreated(MockitoHelper.anyObject())
        verify(mockModel, never()).createNewItem(MockitoHelper.anyObject(), MockitoHelper.anyObject())
    }

    @Test
    fun creatingNewItem_Correctly_WillCreateNewItemInModel() {
        val newItem = ContentListItemViewModel("Title", "Description")
        `when`(mockModel.createNewItem("Title", "Description")).thenReturn(newItem)

        presenter.createNewItem("Title", "Description")

        verify(mockView, never()).createNewItemError(MockitoHelper.anyObject())
        verify(mockView, times(1)).onNewItemCreated(newItem)
        verify(mockModel, times(1)).createNewItem("Title", "Description")
    }

    // Using Mockito matchers with kotlin: http://derekwilson.net/blog/2018/08/23/mokito-kotlin
    object MockitoHelper {
        fun <T> anyObject(): T {
            any<T>()
            return uninitialized()
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> uninitialized(): T =  null as T
    }
}
