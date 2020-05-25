package com.stepwise.quotes.ui.mainpage.quotelist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.quotes.R
import com.stepwise.quotes.databinding.FragmentQuotelistBinding
import com.stepwise.quotes.ui.mainpage.MainPageFragment
import com.stepwise.quotes.ui.mainpage.MainPageViewModel

interface QuoteListFragmentListener {
    fun onAddItemTapped(fragment: QuoteListFragment)
    fun onRefresh()
}

class QuoteListFragment: Fragment(), MainPageFragment {
    private lateinit var binding: FragmentQuotelistBinding
    private lateinit var quoteItemAdapter: QuoteAdapter
    private lateinit var listener: QuoteListFragmentListener

    private val contentItemList = ArrayList<QuoteListItemViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = QuoteListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quotelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentQuotelistBinding.bind(view)

        quoteItemAdapter = QuoteAdapter(contentItemList)
        binding.mainActivityListItems.apply {
            adapter = quoteItemAdapter
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

        if(context is QuoteListFragmentListener) {
            this.listener = context
        }
        else {
            throw IllegalMonitorStateException("Context should implement QuoteListFragmentListener interface")
        }
    }

    fun hasContentItems(): Boolean {
        return contentItemList.isNotEmpty()
    }

    fun updateContent(viewModel: MainPageViewModel) {
        binding.mainActivitySwipeRefreshContainer.isRefreshing = false

        contentItemList.addAll(viewModel.newItems)
        contentItemList.sortByDescending { it.id }
        quoteItemAdapter.notifyDataSetChanged()
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