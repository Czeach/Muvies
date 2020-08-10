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
import com.czech.muvies.databinding.OnAirListBinding
import com.czech.muvies.models.TvShowsResult
import kotlinx.android.synthetic.main.on_air_list.view.*

typealias onAirSItemClickListener = (TvShowsResult) -> Unit

class OnAirListAdapter(private var list: MutableList<TvShowsResult>, private val clickListener: onAirSItemClickListener):
    RecyclerView.Adapter<OnAirListAdapter.OnAirListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnAirListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return OnAirListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OnAirListViewHolder, position: Int) {
        val tv: TvShowsResult = list[position]

        holder.bind(tv)
    }

    fun updateOnAirList(tvList: MutableList<TvShowsResult>) {
        list = tvList
        notifyDataSetChanged()
    }

    inner class OnAirListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.on_air_list, parent, false)), View.OnClickListener {

        val binding = OnAirListBinding.inflate(inflater)

        private var poster: ImageView = itemView.on_air_recycler_image
        private var name: TextView = itemView.on_air_recycler_text

        fun bind(tv: TvShowsResult) {
            binding.onAirViewModel = tv

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
            val onAir = list[adapterPosition]
            clickListener.invoke(onAir)
        }
    }
}