package com.czech.muvies.adapters

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
import com.czech.muvies.databinding.InTheatresMiniListBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.in_theatres_mini_list.view.*
import kotlinx.android.synthetic.main.paged_list.view.*

typealias pagedMovieClickListener = (Movies.MoviesResult) -> Unit

class PagedMoviesAdapter(private val clickListener: pagedMovieClickListener):
    PagedListAdapter<Movies.MoviesResult, PagedMoviesAdapter.PagedMoviesViewModel>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedMoviesViewModel {
        return PagedMoviesViewModel(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: PagedMoviesViewModel, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movies.MoviesResult>() {
            override fun areItemsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
                return  oldItem == newItem
            }
        }
    }

    inner class PagedMoviesViewModel(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(result: Movies.MoviesResult) {
            date.text = result.releaseDate
            vote.text = result.voteAverage.toString()
            title.text = result.title
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