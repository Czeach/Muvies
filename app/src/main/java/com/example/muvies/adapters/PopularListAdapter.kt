package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.PopularListBinding
import com.example.muvies.model.PopularResult
import kotlinx.android.synthetic.main.popular_list.view.*

class PopularListAdapter(private var list: MutableList<PopularResult>):
        RecyclerView.Adapter<PopularListAdapter.PopularListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PopularListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopularListViewHolder, position: Int) {
        val movie: PopularResult = list[position]

        holder.bind(movie)
    }

    fun updatePopularList(movieList: MutableList<PopularResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    class PopularListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.popular_list, parent, false)) {

        private val binding = PopularListBinding.inflate(inflater)

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.poster_image
                mTextView = itemView.popular_title_text

                invalidateAll()
            }
        }

        fun bind(movie: PopularResult) {
            binding.popularListViewModel = movie

            mTextView?.text = movie.title
        }

    }
}