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
import com.czech.muvies.models.PopularResult
import com.czech.muvies.models.PopularTVResult
import kotlinx.android.synthetic.main.paged_list.view.*

class PopularShowsMainAdapter():
    PagedListAdapter<PopularTVResult, PopularShowsMainAdapter.PopularShowsMainViewHolder>(diffUtil)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularShowsMainViewHolder {
        return PopularShowsMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: PopularShowsMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PopularTVResult>() {
            override fun areItemsTheSame(oldItem: PopularTVResult, newItem: PopularTVResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PopularTVResult, newItem: PopularTVResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PopularShowsMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(result: PopularTVResult) {
            title.text = result.name
            date.text = result.firstAirDate
            vote.text = result.voteAverage.toString()
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}