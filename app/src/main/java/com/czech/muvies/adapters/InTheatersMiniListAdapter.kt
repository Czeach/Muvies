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
import com.czech.muvies.databinding.InTheatresMiniListBinding
import com.czech.muvies.models.Movies
import kotlinx.android.synthetic.main.in_theatres_mini_list.view.*

typealias inTheatersItemClickListenerS = (Movies.MoviesResult) -> Unit

class InTheatersMiniListAdapter(private var list: List<Movies.MoviesResult>, private val clickListener: inTheatersItemClickListenerS):
    RecyclerView.Adapter<InTheatersMiniListAdapter.InTheatersMiniListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatersMiniListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return InTheatersMiniListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InTheatersMiniListViewHolder, position: Int) {
        val movie: Movies.MoviesResult = list[position]

        holder.bind(movie)
    }

    fun updateInTheatreList(movieList: List<Movies.MoviesResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class InTheatersMiniListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.in_theatres_mini_list, parent, false)), View.OnClickListener {

        private val binding = InTheatresMiniListBinding.inflate(inflater)

        private var poster: ImageView = itemView.in_theatre_recycler_image
        private var title: TextView = itemView.in_theatre_recycler_text


        fun bind(movie: Movies.MoviesResult) {
            binding.inTheatresMini = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)

            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val inTheatersResult = list[adapterPosition]
            clickListener.invoke(inTheatersResult)
        }
    }

}
