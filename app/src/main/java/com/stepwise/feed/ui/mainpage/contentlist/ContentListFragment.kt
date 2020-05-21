package com.stepwise.feed.ui.mainpage.contentlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.feed.R
import com.stepwise.feed.databinding.FragmentContentlistBinding
import com.stepwise.feed.ui.mainpage.MainPageFragment
import com.stepwise.feed.ui.mainpage.MainPageViewModel

interface ContentListFragmentListener {
    fun onAddItemTapped(fragment: ContentListFragment)
    fun onRefresh()
}

class ContentListFragment: Fragment(), MainPageFragment {
    private lateinit var binding: FragmentContentlistBinding
    private lateinit var contentItemAdapter: ContentAdapter
    private lateinit var listener: ContentListFragmentListener

    private val contentItemList = ArrayList<ContentListItemViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = ContentListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contentlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentContentlistBinding.bind(view)

        contentItemAdapter = ContentAdapter(contentItemList)
        binding.mainActivityListItems.apply {
            adapter = contentItemAdapter
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }

        binding.mainActivitySwipeRefreshContainer.apply {
            setOnRefreshListener {
                listener.onRefresh()
            }

            setColorSchemeColors(
                resources.getColor(R.color.colorPrimary, null), resources.getColor(R.color.colorAccent, null)
            )
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is ContentListFragmentListener) {
            this.listener = context
        }
        else {
            throw IllegalMonitorStateException("Context should implement ContentListFragmentListener interface")
        }
    }


    fun updateContent(viewModel: MainPageViewModel) {
        binding.mainActivitySwipeRefreshContainer.isRefreshing = false

        contentItemList.addAll(viewModel.newItems)
        contentItemList.sortByDescending { it.id }
        contentItemAdapter.notifyDataSetChanged()
    }

    override fun configurePrimaryButton(fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.baseline_add_white_18)
        fab.setOnClickListener { _ ->
           this.listener.onAddItemTapped(this)
        }
    }

    fun setRefreshing(b: Boolean) {
        binding.mainActivitySwipeRefreshContainer.isRefreshing = b
    }
}