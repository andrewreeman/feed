package com.stepwise.feed.ui.mainpage.quotelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stepwise.feed.R
import com.stepwise.feed.databinding.QuoteItemBinding

class QuoteAdapter(private val quote: List<QuoteListItemViewModel>): RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contentItemView = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        val binding = QuoteItemBinding.bind(contentItemView)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position >= quote.size) { return }
        holder.set(quote.get(position))
    }

    override fun getItemCount(): Int {
        return quote.size
    }

    class ViewHolder(view: QuoteItemBinding): RecyclerView.ViewHolder(view.root) {
        var title: TextView = view.mainPageListItemTitle
        var description: TextView = view.mainPageListItemDescription

        fun set(vm: QuoteListItemViewModel) {
            title.text = vm.title
            description.text = vm.description
        }
    }
}