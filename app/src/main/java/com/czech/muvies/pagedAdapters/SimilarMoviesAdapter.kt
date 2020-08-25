package com.czech.muvies.pagedAdapters

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
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.paged_list.view.*
import kotlinx.android.synthetic.main.similar_list.view.*

class SimilarMoviesAdapter(): PagedListAdapter<SimilarMovies.SimilarMoviesResult, SimilarMoviesAdapter.SimilarMoviesViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        return SimilarMoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.similar_list, parent, false))
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimilarMovies.SimilarMoviesResult>() {

            override fun areItemsTheSame(oldItem: SimilarMovies.SimilarMoviesResult, newItem: SimilarMovies.SimilarMoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SimilarMovies.SimilarMoviesResult, newItem: SimilarMovies.SimilarMoviesResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class SimilarMoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster

        fun bind(movie: SimilarMovies.SimilarMoviesResult) {
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }
    }
}