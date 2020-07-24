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
import com.example.muvies.R
import com.example.muvies.databinding.DiscoverListBinding
import com.example.muvies.models.DiscoverResult
import kotlinx.android.synthetic.main.discover_list.view.*

class DiscoverListAdapter: PagedListAdapter<DiscoverResult, DiscoverListAdapter.DiscoverListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListViewHolder {
        return DiscoverListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.discover_list, parent, false))
    }

    override fun onBindViewHolder(holder: DiscoverListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DiscoverResult>() {

            override fun areItemsTheSame(
                oldItem: DiscoverResult,
                newItem: DiscoverResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DiscoverResult,
                newItem: DiscoverResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class DiscoverListViewHolder(view: View):
        RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.discover_recycler_image
        private var title: TextView = itemView.discover_recycler_text

        fun bind(movie: DiscoverResult) {
            title.text = movie.title
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w780${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}