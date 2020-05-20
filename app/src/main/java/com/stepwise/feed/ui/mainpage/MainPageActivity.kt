package com.stepwise.feed.ui.mainpage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.stepwise.feed.R
import com.stepwise.feed.ui.mainpage.addcontent.AddItemFragment
import com.stepwise.feed.ui.mainpage.addcontent.AddItemFragmentListener
import com.stepwise.feed.ui.mainpage.addcontent.CreateNewItemErrorViewModel
import com.stepwise.feed.ui.mainpage.contentlist.ContentListFragment
import com.stepwise.feed.ui.mainpage.contentlist.ContentListFragmentListener
import com.stepwise.feed.ui.mainpage.contentlist.ContentListItemViewModel
import com.stepwise.feed.ui.root.App
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

interface MainPageFragment {
    fun configurePrimaryButton(fab: FloatingActionButton)
}

class MainPageActivity : AppCompatActivity(), MainPageMVP.View,
    ContentListFragmentListener,
    AddItemFragmentListener
{
    private lateinit var addItemFragment: AddItemFragment
    private lateinit var contentListFragment: ContentListFragment

    @Inject
    lateinit var presenter: MainPageMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        (application as App).appComponent.inject(this)

        contentListFragment = ContentListFragment.newInstance()
        addItemFragment = AddItemFragment.newInstance()
        supportFragmentManager.addOnBackStackChangedListener {
            configurePrimaryButton(supportFragmentManager.fragments.first())
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.main_page_fragment_container, contentListFragment)
            .commit()
        presenter.setView(this)

        configurePrimaryButton(contentListFragment)
    }

    override fun onResume() {
        super.onResume()

        presenter.loadContent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateContent(viewModel: MainPageViewModel) {
        contentListFragment.updateContent(viewModel)
    }

    override fun navigateToAddItem() {
        supportFragmentManager
            .beginTransaction()
            .remove(contentListFragment)
            .add(R.id.main_page_fragment_container, addItemFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNewItemCreated(newItem: ContentListItemViewModel) {
        supportFragmentManager.popBackStack()
        contentListFragment.updateContent(MainPageViewModel(listOf(newItem)))
        showSnackbarMessage(getString(R.string.new_item_created))
    }

    override fun createNewItemError(error: CreateNewItemErrorViewModel) {
        if(addItemFragment.isVisible) {
            addItemFragment.showCreateNewItemError(error)
            showSnackbarMessage(getString(R.string.new_item_error))
        }
    }

    override fun onAddItemTapped(fragment: ContentListFragment) {
        presenter.onAddItemTapped()
    }

    override fun onNewItemCreated(title: String, description: String) {
        presenter.createNewItem(title, description)
    }

    private fun configurePrimaryButton(fragment: Fragment?) {
        if(fragment is MainPageFragment) {
            fragment.configurePrimaryButton(main_activity_primary_button)
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(activity_main_page_container, message, Snackbar.LENGTH_LONG).show()
    }
}
