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
import com.czech.muvies.models.SeasonDetails
import com.czech.muvies.utils.Converter
import kotlinx.android.synthetic.main.episodes_list.view.*

class EpisodeAdapter(private var list: List<SeasonDetails.Episode>): RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return EpisodeViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode: SeasonDetails.Episode = list[position]

        holder.bind(episode)
    }

    fun updateList(episodeList: List<SeasonDetails.Episode>) {
        list = episodeList
        notifyDataSetChanged()
    }

    inner class EpisodeViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.episodes_list, parent, false)) {

        private val image: ImageView = itemView.image
        private val episodeNum: TextView = itemView.episode_number
        private val title: TextView = itemView.episode_title
        private val date: TextView = itemView.release_date
        private val overview: TextView = itemView.overview

        fun bind(list: SeasonDetails.Episode) {

            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${list.stillPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(image)

            episodeNum.text = list.episodeNumber.toString()

            title.text = list.name

            date.text = Converter.convertDate(list.airDate)

            overview.text = list.overview
        }
    }
}