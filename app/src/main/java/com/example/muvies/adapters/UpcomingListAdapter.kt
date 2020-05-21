package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.UpcomingListBinding
import com.example.muvies.model.Result
import kotlinx.android.synthetic.main.upcoming_list.view.*
import java.util.zip.Inflater

class UpcomingListAdapter(private var list: MutableList<Result>):
    RecyclerView.Adapter<UpcomingListAdapter.UpcomingListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return UpcomingListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UpcomingListViewHolder, position: Int) {
        val movie: Result = list[position]

        holder.bind(movie)
    }

    fun updateUpcomingList(movieList: MutableList<Result>) {
        list = movieList
        notifyDataSetChanged()
    }

    class UpcomingListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.upcoming_list, parent, false)) {

        private val binding = UpcomingListBinding.inflate(inflater)

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.backdrop_image
                mTextView = itemView.title_text

                invalidateAll()
            }
        }

        fun bind(movie: Result) {
            binding.upcomingListViewModel = movie

            mTextView?.text = movie.title
        }
    }
}