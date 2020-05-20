package com.stepwise.feed.mainpage.addcontent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.feed.R
import com.stepwise.feed.databinding.FragmentAdditemBinding
import com.stepwise.feed.mainpage.MainPageFragment
import kotlinx.android.synthetic.main.activity_main.*

interface AddItemFragmentListener {
    fun onNewItemCreated(title: String, description: String)
}

class AddItemFragment: Fragment(), MainPageFragment {
    private lateinit var listener: AddItemFragmentListener
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

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is AddItemFragmentListener) {
            this.listener = context
        }
        else {
            throw IllegalMonitorStateException("Context should implement AddItemFragmentListener interface")
        }
    }
    
    override fun configurePrimaryButton(fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.baseline_check_white_18)
        fab.setOnClickListener { _ ->
            listener.onNewItemCreated(binding.mainActivityTitleEdit.text.toString(), binding.mainActivityDescriptionEdit.text.toString())
        }
    }

    fun showCreateNewItemError(error: CreateNewItemErrorViewModel) {
        error.titleError?.let {
            binding.mainActivityTitleEdit.error = it
        }

        error.descriptionError?.let {
            binding.mainActivityDescriptionEdit.error = it
        }
    }

}