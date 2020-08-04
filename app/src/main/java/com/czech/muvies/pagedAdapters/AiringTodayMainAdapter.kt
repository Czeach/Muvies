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
import com.czech.muvies.models.AiringTodayTvResult
import kotlinx.android.synthetic.main.paged_list.view.*

class AiringTodayMainAdapter: PagedListAdapter<AiringTodayTvResult, AiringTodayMainAdapter.AiringTodayMainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayMainViewHolder {
        return AiringTodayMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: AiringTodayMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AiringTodayTvResult>() {
            override fun areItemsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AiringTodayTvResult, newItem: AiringTodayTvResult): Boolean {
                return  oldItem == newItem
            }
        }
    }

    inner class AiringTodayMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title

        fun bind(result: AiringTodayTvResult) {
            title.text = result.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}