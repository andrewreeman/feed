package com.stepwise.feed.mainpage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.stepwise.feed.R


class ContentAdapter(private val content: List<ContentViewModel>): RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contentItemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item, parent, false)
        return ViewHolder(contentItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position >= content.size) { return }
        holder.set(content.get(position))
    }

    override fun getItemCount(): Int {
        return content.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        @BindView(R.id.main_page_list_item_title)
        lateinit var title: TextView

        @BindView(R.id.main_page_list_item_description)
        lateinit var description: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun set(vm: ContentViewModel) {
            title.text = vm.title
            description.text = vm.description
        }

    }
}