package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.databinding.PopularListBinding
import com.czech.muvies.models.Movies
import kotlinx.android.synthetic.main.popular_list.view.*

typealias popularSItemClickListener = (Movies.MoviesResult) -> Unit

class PopularListAdapter(private var list: MutableList<Movies.MoviesResult>, private val clickListener: popularSItemClickListener):
        RecyclerView.Adapter<PopularListAdapter.PopularListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PopularListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopularListViewHolder, position: Int) {
        val movie: Movies.MoviesResult = list[position]

        holder.bind(movie)
    }

    fun updatePopularList(movieList: MutableList<Movies.MoviesResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class PopularListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.popular_list, parent, false)), View.OnClickListener {

        private val binding = PopularListBinding.inflate(inflater)

        private var poster: ImageView = itemView.popular_recycler_image
        private var title: TextView = itemView.popular_recycler_text

        fun bind(movie: Movies.MoviesResult) {
            binding.popularListViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val popular = list[adapterPosition]
            clickListener.invoke(popular)
        }

    }
}