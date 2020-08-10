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
import com.czech.muvies.databinding.TrendingMoviesListBinding
import com.czech.muvies.models.MoviesResult
import kotlinx.android.synthetic.main.trending_movies_list.view.*

typealias trendingSItemClickListener = (MoviesResult) -> Unit

class TrendingMoviesListAdapter(private var list: MutableList<MoviesResult>, private val clickListener: trendingSItemClickListener):
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

    fun updateTrendingMoviesList(moviesList: MutableList<MoviesResult>) {
        list = moviesList
        notifyDataSetChanged()
    }

    inner class TrendingMoviesListVieHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.trending_movies_list, parent, false)), View.OnClickListener {

        val binding = TrendingMoviesListBinding.inflate(layoutInflater)

        private var poster: ImageView = itemView.trending_movies_recycler_image
        private var title: TextView = itemView.trending_movies_recycler_text

        fun bind(movie: MoviesResult) {
            binding.trendingMoviesViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val trending = list[adapterPosition]
            clickListener.invoke(trending)
        }

    }
}