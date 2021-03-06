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
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.paged_list.view.*

typealias airingTodayItemClickListener = (TvShows.TvShowsResult) -> Unit

class AiringTodayMainAdapter(private val clickListener: airingTodayItemClickListener):
    PagedListAdapter<TvShows.TvShowsResult, AiringTodayMainAdapter.AiringTodayMainViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayMainViewHolder {
        return AiringTodayMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: AiringTodayMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TvShows.TvShowsResult>() {
            override fun areItemsTheSame(oldItem: TvShows.TvShowsResult, newItem: TvShows.TvShowsResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShows.TvShowsResult, newItem: TvShows.TvShowsResult): Boolean {
                return  oldItem == newItem
            }
        }
    }

    inner class AiringTodayMainViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(result: TvShows.TvShowsResult) {
            date.text = result.firstAirDate
            vote.text = result.voteAverage.toString()
            title.text = result.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val airingToday = getItem(adapterPosition)
            airingToday?.let { clickListener.invoke(it) }
        }
    }
}