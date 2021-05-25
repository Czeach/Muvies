package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.models.SimilarTvShows
import kotlinx.android.synthetic.main.similar_list.view.*

typealias similarTvItemClickListener = (SimilarTvShows.SimilarTvShowsResult) -> Unit

class SimilarTvShowsAdapter(private val clickListener: similarTvItemClickListener): PagedListAdapter<SimilarTvShows.SimilarTvShowsResult, SimilarTvShowsAdapter.SimilarTvShowsViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarTvShowsViewHolder {
        return SimilarTvShowsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.similar_list, parent, false))
    }

    override fun onBindViewHolder(holder: SimilarTvShowsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimilarTvShows.SimilarTvShowsResult>() {

            override fun areItemsTheSame(oldItem: SimilarTvShows.SimilarTvShowsResult, newItem: SimilarTvShows.SimilarTvShowsResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SimilarTvShows.SimilarTvShowsResult, newItem: SimilarTvShows.SimilarTvShowsResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SimilarTvShowsViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster

        fun bind(show: SimilarTvShows.SimilarTvShowsResult) {
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${show.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val show = getItem(adapterPosition)
            show?.let { clickListener.invoke(it) }
        }
    }
}