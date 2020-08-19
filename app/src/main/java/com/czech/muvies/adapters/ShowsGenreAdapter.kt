package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.czech.muvies.R
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.TvShowDetails
import kotlinx.android.synthetic.main.genre_list.view.*

class ShowsGenreAdapter(private var list: List<TvShowDetails.Genre>):
    RecyclerView.Adapter<ShowsGenreAdapter.ShowsGenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsGenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ShowsGenreViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ShowsGenreViewHolder, position: Int) {
        val genre: TvShowDetails.Genre = list[position]

        holder.bind(genre)
    }

    fun updateList(genreList: List<TvShowDetails.Genre>) {
        list = genreList
        notifyDataSetChanged()
    }

    inner class ShowsGenreViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.genre_list, parent, false)) {

        private var genre: TextView = itemView.genre

        fun bind(genreList: TvShowDetails.Genre) {
            genre.text = genreList.name
        }

    }
}