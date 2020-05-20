package com.stepwise.feed.mainpage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.stepwise.feed.R
import com.stepwise.feed.mainpage.addcontent.AddItemFragment
import com.stepwise.feed.mainpage.contentlist.ContentListFragment
import com.stepwise.feed.root.App
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainPageActivity : AppCompatActivity(), MainPageMVP.View {

    private lateinit var addItemFragment: AddItemFragment
    private lateinit var contentListFragment: ContentListFragment

    @Inject
    lateinit var presenter: MainPageMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        (application as App).appComponent.inject(this)
        
        fab.setOnClickListener { view ->
            presenter.onAddItemTapped()
        }

        contentListFragment = ContentListFragment.newInstance()
        addItemFragment = AddItemFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_page_fragment_container, contentListFragment)
            .commit()

        presenter.setView(this)
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
            .commit()
    }
}
