package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.R
import com.example.muvies.databinding.DiscoverListBinding
import com.example.muvies.models.DiscoverResult
import kotlinx.android.synthetic.main.discover_list.view.*

class DiscoverListAdapter(private var list: MutableList<DiscoverResult>):
    RecyclerView.Adapter<DiscoverListAdapter.DiscoverListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListViewHolder {

        return DiscoverListViewHolder(DiscoverListBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DiscoverListViewHolder, position: Int) {
        val movie: DiscoverResult = list[position]
        holder.bind(movie)
    }

    fun updateDiscoverList(movieList: MutableList<DiscoverResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class DiscoverListViewHolder(private var binding: DiscoverListBinding):
        RecyclerView.ViewHolder(binding.root) {

        private var poster: ImageView = itemView.discover_recycler_image
        private var title: TextView = itemView.discover_recycler_text

        fun bind(movie: DiscoverResult) {
            binding.discoverViewModel = movie

            title.text = movie.title

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w780${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            binding.executePendingBindings()
        }

    }
}