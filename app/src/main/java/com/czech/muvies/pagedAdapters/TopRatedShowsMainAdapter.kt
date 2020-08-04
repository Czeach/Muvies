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
import com.czech.muvies.models.TopRatedTVResult
import kotlinx.android.synthetic.main.paged_list.view.*

class TopRatedShowsMainAdapter: PagedListAdapter<TopRatedTVResult, TopRatedShowsMainAdapter.TopRatedShowsMainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedShowsMainViewHolder {
        return TopRatedShowsMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: TopRatedShowsMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TopRatedTVResult>() {

            override fun areItemsTheSame(
                oldItem: TopRatedTVResult,
                newItem: TopRatedTVResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TopRatedTVResult,
                newItem: TopRatedTVResult
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TopRatedShowsMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title

        fun bind(result: TopRatedTVResult) {
            title.text = result.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}