package com.stepwise.quotes.ui.mainpage

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.stepwise.quotes.R
import com.stepwise.quotes.root.App
import com.stepwise.quotes.ui.mainpage.addquote.AddQuoteFragment
import com.stepwise.quotes.ui.mainpage.addquote.AddQuoteFragmentListener
import com.stepwise.quotes.ui.mainpage.addquote.CreateQuoteErrorViewModel
import com.stepwise.quotes.ui.mainpage.quotelist.QuoteListFragment
import com.stepwise.quotes.ui.mainpage.quotelist.QuoteListFragmentListener
import com.stepwise.quotes.ui.mainpage.quotelist.QuoteListItemViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


interface MainPageFragment {
    fun configurePrimaryButton(fab: FloatingActionButton)
}

class MainPageActivity : AppCompatActivity(), MainPageMVP.View,
    CoroutineScope,
    QuoteListFragmentListener,
    AddQuoteFragmentListener
{
    private val animator = FabAnimator()
    private lateinit var addQuoteFragment: AddQuoteFragment
    private lateinit var quoteListFragment: QuoteListFragment

    @Inject
    lateinit var presenter: MainPageMVP.Presenter

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.IO + job

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        quoteListFragment = QuoteListFragment.newInstance()
        addQuoteFragment = AddQuoteFragment.newInstance()
        supportFragmentManager.addOnBackStackChangedListener {
            configurePrimaryButton(supportFragmentManager.fragments.first())
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.main_page_fragment_container, quoteListFragment)
            .commit()

        configurePrimaryButton(quoteListFragment)

        val app = application as App
        app.mockServer.onReady {
            app.appComponent.inject(this)
            presenter.setView(this@MainPageActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        if(!quoteListFragment.hasContentItems()) {
            loadContentWhenInitialized()
        }
    }

    private fun loadContentWhenInitialized() {
        startRefreshing()

        launch {
            var i = 0
            while (!this@MainPageActivity::presenter.isInitialized && i < 100) {
                ++i
                delay(100)
            }

            if (i < 100) {
                presenter.setView(this@MainPageActivity)
                presenter.loadContent()
            } else {
                withContext(Dispatchers.Main) {
                    showSnackbarMessage("Could not initialize presenter")
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
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
        runOnUiThread {
            stopRefreshing()
            quoteListFragment.updateContent(viewModel)
        }
    }

    override fun navigateToAddItem() {
        supportFragmentManager
            .beginTransaction()
            .remove(quoteListFragment)
            .add(R.id.main_page_fragment_container, addQuoteFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNewItemCreated(newItem: QuoteListItemViewModel) {
        runOnUiThread {
            supportFragmentManager.popBackStack()
            stopRefreshing()
            quoteListFragment.updateContent(MainPageViewModel(listOf(newItem)))
            showSnackbarMessage(getString(R.string.new_item_created))
        }
    }

    override fun createNewItemError(error: CreateQuoteErrorViewModel) {
        runOnUiThread {
            if(addQuoteFragment.isVisible) {
                addQuoteFragment.showCreateNewItemError(error)
            }
            showSnackbarMessage(getString(R.string.new_item_error))
        }
    }

    override fun onAddItemTapped(fragment: QuoteListFragment) {
        presenter.onAddItemTapped()
    }

    override fun onRefresh() {
        disableFab()
        launch {
            presenter.loadContent()
        }
    }

    override fun onAddItemFragmentComplete(title: String, description: String) {
        val validationResult = presenter.validateNewItem(title, description)
        if(validationResult != null) {
            createNewItemError(validationResult)
            return
        }

        hideKeyboard()

        showSnackbarMessage(getString(R.string.new_item_saving))
        startRefreshing()
        launch {
            presenter.createNewItem(title, description)
        }
    }

    private fun configurePrimaryButton(fragment: Fragment?) {
        if(fragment is MainPageFragment) {
            fragment.configurePrimaryButton(main_activity_primary_button)
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(activity_main_page_container, message, Snackbar.LENGTH_LONG).show()
    }

    private fun startRefreshing() {
        disableFab()
        quoteListFragment.setRefreshing(true)
    }

    private fun stopRefreshing() {
        enableFab()
        quoteListFragment.setRefreshing(false)
    }

    private fun disableFab() {
        main_activity_primary_button.isClickable = false
        animator.shrink(main_activity_primary_button) {
            if(addQuoteFragment.isVisible) {
                main_activity_primary_button.setImageResource(R.drawable.baseline_check_white_18)
            }
            else{
                main_activity_primary_button.setImageResource(R.drawable.baseline_add_white_18)
            }
        }
    }

    private fun enableFab() {
        main_activity_primary_button.isClickable = true
        animator.grow(main_activity_primary_button)
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(main_activity_primary_button.windowToken, 0)
    }
}
