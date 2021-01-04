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
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.TvShowDetails
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.cast_list.view.*
import kotlinx.android.synthetic.main.seasons_list.view.*

typealias castItemClickListener = (MovieCredits.Cast) -> Unit

class MovieCastAdapter(private var list: List<MovieCredits.Cast>, private val clickListener: castItemClickListener):
        RecyclerView.Adapter<MovieCastAdapter.MovieCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {
        return MovieCastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_list, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        val cast: MovieCredits.Cast = list[position]
        holder.bind(cast)
    }

    fun updateList(castList: List<MovieCredits.Cast>) {
        list = castList
        notifyDataSetChanged()
    }

    inner class MovieCastViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var castImage: ImageView = itemView.cast_poster
        private var character: TextView = itemView.character
        private var name: TextView = itemView.name

        fun bind(cast: MovieCredits.Cast) {
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${cast.profilePath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(castImage)

            character.text = cast.character
            name.text = cast.name
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val cast = list[adapterPosition]
            clickListener.invoke(cast)
        }
    }
}