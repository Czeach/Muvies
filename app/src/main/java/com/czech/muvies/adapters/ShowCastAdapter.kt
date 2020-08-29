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
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.TvShowCredits
import kotlinx.android.synthetic.main.cast_list.view.*

typealias showCastItemClickListener = (TvShowCredits.Cast) -> Unit

class ShowCastAdapter(private var list: List<TvShowCredits.Cast>, private val clickListener: showCastItemClickListener):
    RecyclerView.Adapter<ShowCastAdapter.ShowCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCastViewHolder {
        return ShowCastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cast_list, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ShowCastViewHolder, position: Int) {
        val cast: TvShowCredits.Cast = list[position]

        holder.bind(cast)
    }

    fun updateList(castList: List<TvShowCredits.Cast>) {
        list = castList
        notifyDataSetChanged()
    }

    inner class ShowCastViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var castImage: ImageView = itemView.cast_poster
        private var character: TextView = itemView.character
        private var name: TextView = itemView.name

        fun bind(cast: TvShowCredits.Cast) {
            Glide.with(itemView)
                .load("$BASE_IMAGE_PATH${cast.profilePath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(castImage)

            character.text = cast.character
            name.text = cast.name
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val cast = list[adapterPosition]
            clickListener.invoke(cast)
        }
    }
}