package com.czech.muvies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.databinding.TopRatedTvListBinding
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.top_rated_tv_list.view.*

typealias topRatedTvSItemClickListener = (TvShows.TvShowsResult) -> Unit

class TopRatedTvListAdapter(private var list: MutableList<TvShows.TvShowsResult>, private val clickListener: topRatedTvSItemClickListener):
    RecyclerView.Adapter<TopRatedTvListAdapter.TopRatedTvListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedTvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TopRatedTvListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: TopRatedTvListViewHolder, position: Int) {
        val tv: TvShows.TvShowsResult = list[position]

        holder.bind(tv)
    }

    fun updateTopRatedTvList(tvList: MutableList<TvShows.TvShowsResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    inner class TopRatedTvListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.top_rated_tv_list, parent, false)), View.OnClickListener {

        val binding = TopRatedTvListBinding.inflate(inflater)

        private var poster: ImageView = itemView.top_rated_tv_recycler_image
        private var name: TextView = itemView.top_rated_tv_recycler_text

        fun bind(tv: TvShows.TvShowsResult) {
            binding.topRatedTvViewModel = tv

            name.text = tv.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${tv.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val topRated = list[adapterPosition]
            clickListener.invoke(topRated)
        }
    }

}