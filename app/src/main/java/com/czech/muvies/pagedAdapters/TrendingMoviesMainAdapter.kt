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
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.models.TrendingMoviesResult
import kotlinx.android.synthetic.main.paged_list.view.*

class TrendingMoviesMainAdapter: PagedListAdapter<TrendingMoviesResult, TrendingMoviesMainAdapter.TrendingMoviesMainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesMainViewHolder {
        return TrendingMoviesMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: TrendingMoviesMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TrendingMoviesResult>() {
            override fun areItemsTheSame(oldItem: TrendingMoviesResult, newItem: TrendingMoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TrendingMoviesResult, newItem: TrendingMoviesResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TrendingMoviesMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title

        fun bind(result: TrendingMoviesResult) {
            title.text = result.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}