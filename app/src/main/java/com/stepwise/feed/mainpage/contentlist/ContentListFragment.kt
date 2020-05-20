package com.stepwise.feed.mainpage.contentlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stepwise.feed.R
import com.stepwise.feed.databinding.FragmentContentlistBinding
import com.stepwise.feed.mainpage.MainPageViewModel

class ContentListFragment: Fragment() {
    private lateinit var binding: FragmentContentlistBinding
    private lateinit var contentItemAdapter: ContentAdapter

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
        binding.mainActivityListItems.adapter = contentItemAdapter
        binding.mainActivityListItems.layoutManager = LinearLayoutManager(this.context)
    }


    fun updateContent(viewModel: MainPageViewModel) {
        contentItemList.addAll(viewModel.newItems)
        contentItemAdapter.notifyDataSetChanged()
    }
}