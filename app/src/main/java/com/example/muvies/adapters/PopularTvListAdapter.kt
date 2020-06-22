package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.muvies.BASE_IMAGE_PATH
import com.example.muvies.R
import com.example.muvies.databinding.PopularTvListBinding
import com.example.muvies.model.PopularTVResult
import kotlinx.android.synthetic.main.popular_tv_list.view.*

class PopularTvListAdapter(private var list: MutableList<PopularTVResult>):
    RecyclerView.Adapter<PopularTvListAdapter.PopularTvListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularTvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return PopularTvListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PopularTvListViewHolder, position: Int) {
        val tv: PopularTVResult = list[position]

        holder.bind(tv)
    }

    fun updatePopularTvList(tvList: MutableList<PopularTVResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    class PopularTvListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.popular_tv_list, parent, false)) {

        val binding = PopularTvListBinding.inflate(inflater)

        private var poster: ImageView = itemView.popular_tv_recycler_image
        private var name: TextView = itemView.popular_tv_recycler_text

        fun bind(tv: PopularTVResult) {
            binding.popularTvViewModel = tv

            name.text = tv.name
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${tv.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)
        }

    }
}