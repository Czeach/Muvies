package com.example.muvies.pagedAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.models.UpcomingResult
import kotlinx.android.synthetic.main.paged_list.view.*

class UpcomingMainAdapter: PagedListAdapter<UpcomingResult, UpcomingMainAdapter.UpcomingMainViewHolder>(diffUtilCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMainViewHolder {
        return UpcomingMainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.paged_list, parent, false))
    }

    override fun onBindViewHolder(holder: UpcomingMainViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffUtilCallBack = object : DiffUtil.ItemCallback<UpcomingResult>()  {
            override fun areItemsTheSame(oldItem: UpcomingResult, newItem: UpcomingResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UpcomingResult, newItem: UpcomingResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class UpcomingMainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var poster: ImageView = itemView.poster_image
        private var title: TextView = itemView.title

        fun bind(result: UpcomingResult) {
            title.text = result.title
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${result.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }
    }
}