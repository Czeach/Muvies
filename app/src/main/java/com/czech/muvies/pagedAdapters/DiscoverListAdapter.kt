package com.czech.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.R
import com.czech.muvies.databinding.DiscoverListBinding
import com.czech.muvies.models.DiscoverResult
import kotlinx.android.synthetic.main.discover_list.view.*
import kotlinx.android.synthetic.main.paged_list.view.*

class DiscoverListAdapter: PagedListAdapter<DiscoverResult, DiscoverListAdapter.DiscoverListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListViewHolder {
        return DiscoverListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
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

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(movie: DiscoverResult) {
            title.text = movie.title
            date.text = movie.releaseDate
            vote.text = movie.voteAverage.toString()

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w780${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}