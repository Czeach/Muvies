package com.example.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.models.PopularResult
import kotlinx.android.synthetic.main.paged_list.view.*

class PopularMainAdapter: PagedListAdapter<PopularResult, PopularMainAdapter.PopularMainViewHolder>(diffUtil)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMainViewHolder {
        return PopularMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: PopularMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PopularResult>() {

            override fun areItemsTheSame(oldItem: PopularResult, newItem: PopularResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PopularResult, newItem: PopularResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PopularMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title

        fun bind(result: PopularResult) {
            title.text = result.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}