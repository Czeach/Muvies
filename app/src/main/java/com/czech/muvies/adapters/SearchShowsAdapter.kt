package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.models.SearchMovies
import com.czech.muvies.models.SearchShows
import kotlinx.android.synthetic.main.search_list.view.*

typealias searchShowsClickListener = (SearchShows.Result) -> Unit

class SearchShowsAdapter(private var list: List<SearchShows.Result>, private val clickListener: searchShowsClickListener):
    RecyclerView.Adapter<SearchShowsAdapter.SearchShowsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchShowsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return SearchShowsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SearchShowsViewHolder, position: Int) {
        val movie: SearchShows.Result = list[position]

        holder.bind(movie)
    }

    fun updateSearchList(movieList: List<SearchShows.Result>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class SearchShowsViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.search_list, parent, false)), View.OnClickListener {

        private var title: TextView = itemView.resultText

        fun bind(movie: SearchShows.Result) {

            title.text = movie.name
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val showSearch = list[adapterPosition]
            clickListener.invoke(showSearch)
        }

    }
}