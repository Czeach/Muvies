package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.TrendingTvListBinding
import com.example.muvies.model.TrendingTvResult
import kotlinx.android.synthetic.main.trending_tv_list.view.*

class TrendingTvListAdapter(private var list: MutableList<TrendingTvResult>):
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

    fun updateTrendingTvList(tvList: MutableList<TrendingTvResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    class TrendingTvListViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.trending_tv_list, parent, false)) {

        val binding = TrendingTvListBinding.inflate(layoutInflater)

        private var poster: ImageView = itemView.trending_tv_recycler_image
        private var name: TextView = itemView.trending_tv_recycler_text

        fun bind(tv: TrendingTvResult) {
            binding.trendingTvViewModel = tv

            name.text = tv.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${tv.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}