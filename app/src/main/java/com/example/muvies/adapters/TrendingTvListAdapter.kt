package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.trending_tv_recycler_image
                mTextView = itemView.trending_tv_recycler_text
            }
        }

        fun bind(tv: TrendingTvResult) {
            binding.trendingTvViewModel = tv

            mTextView?.text = tv.name
        }

    }
}