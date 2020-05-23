package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.TopRatedListBinding
import com.example.muvies.databinding.UpcomingListBinding
import com.example.muvies.model.TopRatedResult
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

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.top_rated_recycler_image
                mTextView = itemView.top_rated_recycler_text
            }
        }

        fun bind(movie: TopRatedResult) {
            binding.topRatedViewModel = movie

            mTextView?.text = movie.title
        }

    }
}