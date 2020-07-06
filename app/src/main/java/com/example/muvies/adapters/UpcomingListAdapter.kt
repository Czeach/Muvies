package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.UpcomingListBinding
import com.example.muvies.model.UpcomingResult
import kotlinx.android.synthetic.main.upcoming_list.view.*

class UpcomingListAdapter(private var list: MutableList<UpcomingResult>):
    RecyclerView.Adapter<UpcomingListAdapter.UpcomingListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return UpcomingListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UpcomingListViewHolder, position: Int) {
        val movie: UpcomingResult = list[position]

        holder.bind(movie)
    }

    fun updateUpcomingList(movieList: MutableList<UpcomingResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    class UpcomingListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.upcoming_list, parent, false)) {

        private val binding = UpcomingListBinding.inflate(inflater)

        private var poster: ImageView = itemView.upcoming_recycler_image
        private var title: TextView = itemView.upcoming_recycler_text

        fun bind(movie: UpcomingResult) {
            binding.upcomingListViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}