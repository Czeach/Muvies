package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.InTheatersMainListBinding
import com.example.muvies.databinding.InTheatresMiniListBinding
import com.example.muvies.model.InTheatersResult
import kotlinx.android.synthetic.main.in_theaters_main_list.view.*
import kotlinx.android.synthetic.main.in_theatres_mini_list.view.*

class InTheatersListAdapter(private var list: MutableList<InTheatersResult>):
    RecyclerView.Adapter<InTheatersListAdapter.BaseViewHolder<*>>() {

    abstract class BaseViewHolder<T>(view: View):
        RecyclerView.ViewHolder(view) {
        abstract fun bind(movie: T)
    }

    companion object{
        private const val MINI_LIST = 0
        private const val MAIN_LIST = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
       return when (viewType) {
            MINI_LIST -> {
                InTheatersMiniListViewHolder(LayoutInflater.from(parent.context), parent)
            }
           MAIN_LIST -> {
               InTheatersMainListViewHolder(LayoutInflater.from(parent.context), parent)
           }
           else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val movie: InTheatersResult = list[position]

        when (holder) {
            is InTheatersMiniListViewHolder -> holder.bind(movie)

            is InTheatersMainListViewHolder -> holder.bind(movie)
        }

    }

    fun updateInTheatreList(movieList: MutableList<InTheatersResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    inner class InTheatersMiniListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<InTheatersResult>(inflater.inflate(R.layout.in_theatres_mini_list, parent, false)) {

        private val binding = InTheatresMiniListBinding.inflate(inflater)

        private var poster: ImageView = itemView.in_theatre_recycler_image
        private var title: TextView = itemView.in_theatre_recycler_text


        override fun bind(movie: InTheatersResult) {
            binding.inTheatresMini = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            binding.executePendingBindings()
        }
    }

    inner class InTheatersMainListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
            BaseViewHolder<InTheatersResult>(inflater.inflate(R.layout.in_theaters_main_list, parent, false)) {

        private val binding = InTheatersMainListBinding.inflate(inflater)

        private var poster: ImageView = itemView.in_theaters_main_recycler_image
        private var title: TextView = itemView.in_theaters_main_recycler_text

        override fun bind(movie: InTheatersResult) {
            binding.inTheatersMain = movie

            title.text = movie.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${movie.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            binding.executePendingBindings()
        }

    }

}