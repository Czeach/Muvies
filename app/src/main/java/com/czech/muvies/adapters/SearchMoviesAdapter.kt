package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.models.SearchMovies
import kotlinx.android.synthetic.main.search_list.view.*

typealias searchMoviesClickListener = (SearchMovies.Result) -> Unit

class SearchMoviesAdapter(private var list: List<SearchMovies.Result>, private val clickListener: searchMoviesClickListener):
    RecyclerView.Adapter<SearchMoviesAdapter.SearchMoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return SearchMoviesViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        val movie: SearchMovies.Result = list[position]

        holder.bind(movie)
    }

    fun updateSearchList(movieList: List<SearchMovies.Result>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class SearchMoviesViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.search_list, parent, false)), View.OnClickListener {

        private var title: TextView = itemView.resultText

        fun bind(movie: SearchMovies.Result) {

            title.text = movie.title
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(it: View?) {
            val searchMovie = list[adapterPosition]
            clickListener.invoke(searchMovie)
        }

    }
}