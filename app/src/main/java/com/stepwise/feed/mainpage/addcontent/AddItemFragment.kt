package com.stepwise.feed.mainpage.addcontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stepwise.feed.R
import com.stepwise.feed.databinding.FragmentAdditemBinding

class AddItemFragment: Fragment() {
    private lateinit var binding: FragmentAdditemBinding

    companion object {
        @JvmStatic
        fun newInstance() = AddItemFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_additem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdditemBinding.bind(view)
    }

}