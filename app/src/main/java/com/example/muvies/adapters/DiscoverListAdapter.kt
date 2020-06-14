package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.DiscoverListBinding
import com.example.muvies.model.DiscoverResult
import kotlinx.android.synthetic.main.discover_list.view.*

class DiscoverListAdapter(private var list: MutableList<DiscoverResult>):
    RecyclerView.Adapter<DiscoverListAdapter.DiscoverListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return DiscoverListViewHolder(inflater, parent)
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

    class DiscoverListViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.discover_list, parent, false)) {

        val binding = DiscoverListBinding.inflate(layoutInflater)

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            mImageView = itemView.discover_recycler_image
            mTextView = itemView.discover_recycler_text
        }

        fun bind(movie: DiscoverResult) {
            binding.discoverViewModel = movie

            mTextView?.text = movie.title
        }

    }
}