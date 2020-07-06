package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.InTheatresListBinding
import com.example.muvies.model.InTheatersResult
import kotlinx.android.synthetic.main.in_theatres_list.view.*

class InTheatersListAdapter(private var list: MutableList<InTheatersResult>):
    RecyclerView.Adapter<InTheatersListAdapter.InTheatersListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return InTheatersListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InTheatersListViewHolder, position: Int) {
        val movie: InTheatersResult = list[position]
        holder.bind(movie)
    }

    fun updateInTheatreList(movieList: MutableList<InTheatersResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    class InTheatersListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.in_theatres_list, parent, false)) {

        private val binding = InTheatresListBinding.inflate(inflater)

        private var poster: ImageView = itemView.in_theatre_recycler_image
        private var title: TextView = itemView.in_theatre_recycler_text


        fun bind(movie: InTheatersResult) {
            binding.inTheatresViewModel = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            binding.executePendingBindings()
        }
    }
}