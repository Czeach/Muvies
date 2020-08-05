package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.databinding.TopRatedListBinding
import com.czech.muvies.models.TopRatedResult
import kotlinx.android.synthetic.main.top_rated_list.view.*

class TopRatedListAdapter(private var list: MutableList<TopRatedResult>):
    RecyclerView.Adapter<TopRatedListAdapter.TopRatedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TopRatedViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        val movie: TopRatedResult = list[position]

        holder.bind(movie)
    }

    fun updateTopRatedList(movieList: MutableList<TopRatedResult>){
        list = movieList
        notifyDataSetChanged()
    }

    class TopRatedViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.top_rated_list, parent, false)) {

        private val binding = TopRatedListBinding.inflate(inflater)

        private var poster: ImageView = itemView.top_rated_recycler_image
        private var title: TextView = itemView.top_rated_recycler_text

        fun bind(movie: TopRatedResult) {
            binding.topRatedViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}