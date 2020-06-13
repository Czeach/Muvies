package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.AiringTodayListBinding
import com.example.muvies.model.AiringTodayTvResult
import kotlinx.android.synthetic.main.airing_today_list.view.*

class AiringTodayListAdapter(private var list: MutableList<AiringTodayTvResult>):
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

    class AiringTodayListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.airing_today_list, parent, false)) {

        val binding = AiringTodayListBinding.inflate(inflater)

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.airing_today_recycler_image
                mTextView = itemView.airing_today_recycler_text
            }
        }

        fun bind(tv: AiringTodayTvResult) {
            binding.airingTodayViewModel = tv

            mTextView?.text = tv.name
        }

    }
}