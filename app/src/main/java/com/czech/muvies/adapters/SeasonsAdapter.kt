package com.czech.muvies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.models.TvShowDetails
import com.czech.muvies.models.TvShows
import com.czech.muvies.utils.Converter
import kotlinx.android.synthetic.main.seasons_list.view.*

class SeasonsAdapter(private var list: List<TvShowDetails.Season>):
    RecyclerView.Adapter<SeasonsAdapter.SeasonsViewHolder>() {

    lateinit var seasonItemCLicked: ItemCLickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonsViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return SeasonsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SeasonsViewHolder, position: Int) {
        val season: TvShowDetails.Season = list[position]
        holder.bind(season)
        holder.itemView.setOnClickListener{
            seasonItemCLicked.let {
                seasonItemCLicked.onItemClicked(season)
            }
        }
    }

    fun updateList(seasonList: List<TvShowDetails.Season>) {
        list = seasonList
        notifyDataSetChanged()
    }

    fun setUpListener(itemCLicked: ItemCLickedListener){
        seasonItemCLicked = itemCLicked
    }

    inner class SeasonsViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.seasons_list, parent, false)){

        private var seasonPoster: ImageView = itemView.season_poster
        private var seasonNumber: TextView = itemView.season_number
        private var airDate: TextView = itemView.air_date
        private var episodeCount: TextView = itemView.number_of_episodes


        @SuppressLint("SetTextI18n")
        fun bind(seasonList: TvShowDetails.Season) {

            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${seasonList.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(seasonPoster)

            seasonNumber.text =
                if (seasonList.seasonNumber == 0) "Specials"
                else "Season ${seasonList.seasonNumber}"

            val year = Converter.convertDateToYear(seasonList.airDate)
            airDate.text = "($year)"

            episodeCount.text = "${seasonList.episodeCount} episodes"
        }
    }

    interface ItemCLickedListener {
        fun onItemClicked(season: TvShowDetails.Season)
    }
}