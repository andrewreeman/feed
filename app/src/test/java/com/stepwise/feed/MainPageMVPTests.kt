package com.stepwise.feed

import android.content.res.Resources
import com.stepwise.feed.domain.model.Quote
import com.stepwise.feed.ui.mainpage.MainPageMVP
import com.stepwise.feed.ui.mainpage.MainPageViewModel
import com.stepwise.feed.ui.mainpage.Presenter
import com.stepwise.feed.ui.mainpage.addquote.CreateQuoteErrorViewModel
import com.stepwise.feed.ui.mainpage.quotelist.QuoteListItemViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainPageMVPTests {
    private lateinit var mockModel: MainPageMVP.Model
    private lateinit var mockView: MainPageMVP.View
    private lateinit var presenter: Presenter
    private lateinit var item: Quote
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        mockModel = mock(MainPageMVP.Model::class.java)
        mockView = mock(MainPageMVP.View::class.java)
        presenter = Presenter(mockModel, mock(Resources::class.java, RETURNS_MOCKS))
        presenter.setView(mockView)

        item = Quote(-1,"A title", "A description")
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun loadContentFromRepositoryIntoView_WhenContentRequested() = runBlockingTest {
        val listToSupply = listOf(item).map{ QuoteListItemViewModel.fromContent(it) }
        val viewModelToExpect = MainPageViewModel(listToSupply)

        `when`(mockModel.getContent()).thenReturn(listOf(item))

        presenter.loadContent()
        verify(mockModel, times(1)).getContent()
        verify(mockView, times(1)).updateContent(viewModelToExpect)

    }

    @Test
    fun navigateToAddItemCalled_WhenOnAddItemTapped()  {
        presenter.onAddItemTapped()
        verify(mockView, times(1)).navigateToAddItem()
    }

    @Test
    fun creatingNewItem_WithoutTitle_WillShowError() = runBlockingTest {
        presenter.createNewItem("", "Description")

        val expectedErrorVM = CreateQuoteErrorViewModel("", null)
        verify(mockView, times(1)).createNewItemError(expectedErrorVM)
        verify(mockView, never()).onNewItemCreated(MockitoHelper.anyObject())
        verify(mockModel, never()).createNewItem(MockitoHelper.anyObject(), MockitoHelper.anyObject())
    }

    @Test
    fun creatingNewItem_WithoutDescription_WillShowError() = runBlockingTest {
        presenter.createNewItem("Title", "")

        val expectedErrorVM = CreateQuoteErrorViewModel(null, "")
        verify(mockView, times(1)).createNewItemError(expectedErrorVM)
        verify(mockView, never()).onNewItemCreated(MockitoHelper.anyObject())
        verify(mockModel, never()).createNewItem(MockitoHelper.anyObject(), MockitoHelper.anyObject())
    }

    @Test
    fun creatingNewItem_Correctly_WillCreateNewItemInModel() = runBlockingTest {
        val newItem = Quote(-1, "Title", "Description")
        `when`(mockModel.createNewItem("Title", "Description")).thenReturn(newItem)

        presenter.createNewItem("Title", "Description")

        verify(mockView, never()).createNewItemError(MockitoHelper.anyObject())
        verify(mockView, times(1)).onNewItemCreated(QuoteListItemViewModel.fromContent(newItem))
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
