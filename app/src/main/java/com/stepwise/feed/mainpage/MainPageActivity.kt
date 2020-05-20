package com.stepwise.feed.mainpage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.feed.R
import com.stepwise.feed.mainpage.addcontent.AddItemFragment
import com.stepwise.feed.mainpage.addcontent.AddItemFragmentListener
import com.stepwise.feed.mainpage.contentlist.ContentListFragment
import com.stepwise.feed.mainpage.contentlist.ContentListFragmentListener
import com.stepwise.feed.root.App
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

    override fun onAddItemTapped(fragment: ContentListFragment) {
        presenter.onAddItemTapped()
    }

    override fun onNewItemCreated(title: String, description: String) {
        //presenter.onNewItemCreated(title, description)
    }

    private fun configurePrimaryButton(fragment: Fragment?) {
        if(fragment is MainPageFragment) {
            fragment.configurePrimaryButton(main_activity_primary_button)
        }
    }
}
