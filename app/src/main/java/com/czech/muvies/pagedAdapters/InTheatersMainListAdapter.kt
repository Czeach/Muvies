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
import com.czech.muvies.models.Movies
import kotlinx.android.synthetic.main.paged_list.view.*

typealias inTheatersItemClickListener = (Movies.MoviesResult) -> Unit

class InTheatersMainListAdapter(private val clickListener: inTheatersItemClickListener):
    PagedListAdapter<Movies.MoviesResult, InTheatersMainListAdapter.InTheatersMainListViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatersMainListViewHolder {
        return InTheatersMainListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: InTheatersMainListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class InTheatersMainListViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title
        private var date: TextView = itemView.date
        private var vote: TextView = itemView.vote

        fun bind(result: Movies.MoviesResult) {
            title.text = result.title
            date.text = result.releaseDate
            vote.text = result.voteAverage.toString()
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
            val inTheaters = getItem(adapterPosition)
            inTheaters?.let { clickListener.invoke(it) }
        }
    }
}

class DiffUtilCallBack: DiffUtil.ItemCallback<Movies.MoviesResult>() {
    override fun areItemsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies.MoviesResult, newItem: Movies.MoviesResult): Boolean {
        return oldItem.title == newItem.title
                && oldItem.popularity == newItem.popularity
                && oldItem.voteCount == newItem.voteCount
    }

}