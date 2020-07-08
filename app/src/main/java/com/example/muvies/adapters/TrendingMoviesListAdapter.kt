package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.TrendingMoviesListBinding
import com.example.muvies.models.TrendingMoviesResult
import kotlinx.android.synthetic.main.trending_movies_list.view.*

class TrendingMoviesListAdapter(private var list: MutableList<TrendingMoviesResult>):
    RecyclerView.Adapter<TrendingMoviesListAdapter.TrendingMoviesListVieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesListVieHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TrendingMoviesListVieHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TrendingMoviesListVieHolder, position: Int) {
        val movies = list[position]

        holder.bind(movies)
    }

    fun updateTrendingMoviesList(moviesList: MutableList<TrendingMoviesResult>) {
        list = moviesList
        notifyDataSetChanged()
    }

    class TrendingMoviesListVieHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.trending_movies_list, parent, false)) {

        val binding = TrendingMoviesListBinding.inflate(layoutInflater)

        private var poster: ImageView = itemView.trending_movies_recycler_image
        private var title: TextView = itemView.trending_movies_recycler_text

        fun bind(movie: TrendingMoviesResult) {
            binding.trendingMoviesViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}