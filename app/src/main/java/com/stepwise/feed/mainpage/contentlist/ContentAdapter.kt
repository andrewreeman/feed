package com.stepwise.feed.mainpage.contentlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stepwise.feed.R
import com.stepwise.feed.databinding.ContentItemBinding

class ContentAdapter(private val content: List<ContentListItemViewModel>): RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contentItemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        val binding = ContentItemBinding.bind(contentItemView)
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position >= content.size) { return }
        holder.set(content.get(position))
    }

    override fun getItemCount(): Int {
        return content.size
    }

    class ViewHolder(view: ContentItemBinding): RecyclerView.ViewHolder(view.root) {
        var title: TextView = view.mainPageListItemTitle
        var description: TextView = view.mainPageListItemDescription

        fun set(vm: ContentListItemViewModel) {
            title.text = vm.title
            description.text = vm.description
        }
    }
}