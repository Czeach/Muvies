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
import com.czech.muvies.databinding.TrendingTvListBinding
import com.czech.muvies.models.TvShows
import kotlinx.android.synthetic.main.trending_tv_list.view.*

typealias trendingTvSItemClickListener = (TvShows.TvShowsResult) -> Unit

class TrendingTvListAdapter(private var list: MutableList<TvShows.TvShowsResult>, private val clickListener: trendingTvSItemClickListener):
    RecyclerView.Adapter<TrendingTvListAdapter.TrendingTvListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingTvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return TrendingTvListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TrendingTvListViewHolder, position: Int) {
        val tv = list[position]

        holder.bind(tv)
    }

    fun updateTrendingTvList(tvList: MutableList<TvShows.TvShowsResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    inner class TrendingTvListViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.trending_tv_list, parent, false)), View.OnClickListener {

        val binding = TrendingTvListBinding.inflate(layoutInflater)

        private var poster: ImageView = itemView.trending_tv_recycler_image
        private var name: TextView = itemView.trending_tv_recycler_text

        fun bind(tv: TvShows.TvShowsResult) {
            binding.trendingTvViewModel = tv

            name.text = tv.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${tv.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val trending = list[adapterPosition]
            clickListener.invoke(trending)
        }

    }
}