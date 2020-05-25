package com.stepwise.quotes.ui.mainpage.addquote

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stepwise.quotes.R
import com.stepwise.quotes.databinding.FragmentAddquoteBinding
import com.stepwise.quotes.ui.mainpage.MainPageFragment

interface AddQuoteFragmentListener {
    fun onAddItemFragmentComplete(title: String, description: String)
}

class AddQuoteFragment: Fragment(), MainPageFragment {
    private lateinit var listener: AddQuoteFragmentListener
    private lateinit var binding: FragmentAddquoteBinding

    companion object {
        @JvmStatic
        fun newInstance() = AddQuoteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_addquote, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddquoteBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is AddQuoteFragmentListener) {
            this.listener = context
        }
        else {
            throw IllegalMonitorStateException("Context should implement AddQuoteFragmentListener interface")
        }
    }
    
    override fun configurePrimaryButton(fab: FloatingActionButton) {
        fab.setImageResource(R.drawable.baseline_check_white_18)
        fab.setOnClickListener { _ ->
            listener.onAddItemFragmentComplete(binding.mainActivityTitleEdit.text.toString(), binding.mainActivityDescriptionEdit.text.toString())
        }
    }

    fun showCreateNewItemError(error: CreateQuoteErrorViewModel) {
        error.titleError?.let {
            binding.mainActivityTitleEdit.error = it
        }

        error.descriptionError?.let {
            binding.mainActivityDescriptionEdit.error = it
        }
    }

}