package com.stepwise.feed.mainpage

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.stepwise.feed.R
import kotlinx.android.synthetic.main.activity_main.*

class MainPageActivity : AppCompatActivity() {

    @BindView(R.id.scrolling_activity_list_items)
    lateinit var contentRecyclerView: RecyclerView

    private lateinit var contentItemAdapter: ContentAdapter
    private val contentItemList = ArrayList<ContentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        ButterKnife.bind(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        contentItemAdapter = ContentAdapter(contentItemList)
        contentRecyclerView.adapter = contentItemAdapter
        contentRecyclerView.layoutManager = LinearLayoutManager(this)

        contentItemList.add(ContentViewModel("Title", "Description"))
        contentItemAdapter.notifyDataSetChanged()
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
}
