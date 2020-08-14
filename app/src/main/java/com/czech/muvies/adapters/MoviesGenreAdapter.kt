package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.models.MovieDetails
import kotlinx.android.synthetic.main.genre_list.view.*
import java.util.zip.Inflater

class MoviesGenreAdapter(private var list: List<MovieDetails.Genre>):
    RecyclerView.Adapter<MoviesGenreAdapter.MoviesGenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesGenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MoviesGenreViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MoviesGenreViewHolder, position: Int) {
        val genre: MovieDetails.Genre = list[position]

        holder.bind(genre)
    }

    fun updateList(genreList: List<MovieDetails.Genre>) {
        list = genreList
        notifyDataSetChanged()
    }

    inner class MoviesGenreViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.genre_list, parent, false)) {

        private var genre: TextView = itemView.genre

        fun bind(genreList: MovieDetails.Genre) {
             genre.text = genreList.name
        }

    }
}