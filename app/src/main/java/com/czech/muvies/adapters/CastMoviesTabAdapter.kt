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
import com.czech.muvies.models.PersonMovies
import kotlinx.android.synthetic.main.cast_list.view.*
import kotlinx.android.synthetic.main.cast_other.view.*
import kotlinx.android.synthetic.main.in_theatres_mini_list.view.*
import kotlinx.android.synthetic.main.paged_list.view.*
import kotlinx.android.synthetic.main.similar_list.view.*
import kotlinx.android.synthetic.main.similar_list.view.poster

class CastMoviesTabAdapter(private var list: List<PersonMovies.Cast>):
    RecyclerView.Adapter<CastMoviesTabAdapter.CastMoviesTabViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMoviesTabViewHolder {
        return CastMoviesTabViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_other, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CastMoviesTabViewHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie)
    }

    fun updateList(movieList: List<PersonMovies.Cast>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class CastMoviesTabViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView? = itemView.poster
        private var title: TextView? = itemView.movie_title
//        private var date: TextView = itemView.date
//        private var rating: TextView = itemView.vote

        fun bind(movie: PersonMovies.Cast) {
            poster?.let {
                Glide.with(itemView)
                    .load("$BASE_IMAGE_PATH${movie.posterPath}")
                    .placeholder(R.drawable.poster_placeholder)
                    .error(R.drawable.poster_error)
                    .into(it)
            }

            title?.text = movie.title

//            title.text = movie.originalTitle
//            date.text = movie.releaseDate
//            rating.text = movie.voteAverage.toString()
        }
    }

}