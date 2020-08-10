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
import com.czech.muvies.databinding.AiringTodayListBinding
import com.czech.muvies.models.AiringTodayTvResult
import com.czech.muvies.models.InTheatersResult
import kotlinx.android.synthetic.main.airing_today_list.view.*

typealias airingTodaySItemClickListener = (AiringTodayTvResult) -> Unit

class AiringTodayListAdapter(private var list: MutableList<AiringTodayTvResult>, private val clickListener: airingTodaySItemClickListener):
    RecyclerView.Adapter<AiringTodayListAdapter.AiringTodayListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringTodayListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AiringTodayListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AiringTodayListViewHolder, position: Int) {
        val tv: AiringTodayTvResult = list[position]

        holder.bind(tv)
    }

    fun updateAiringTodayList(tvList: MutableList<AiringTodayTvResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    inner class AiringTodayListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.airing_today_list, parent, false)), View.OnClickListener {

        val binding = AiringTodayListBinding.inflate(inflater)

        private var poster: ImageView = itemView.airing_today_recycler_image
        private var name: TextView = itemView.airing_today_recycler_text

        fun bind(tv: AiringTodayTvResult) {
            binding.airingTodayViewModel = tv

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
            val airingToday = list[adapterPosition]
            clickListener.invoke(airingToday)
        }

    }
}