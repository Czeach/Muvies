package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.InTheatresListBinding
import com.example.muvies.model.InTheatresResult
import kotlinx.android.synthetic.main.in_theatres_list.view.*

class InTheatresListAdapter(private var list: MutableList<InTheatresResult>):
    RecyclerView.Adapter<InTheatresListAdapter.InTheatresListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatresListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return InTheatresListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InTheatresListViewHolder, position: Int) {
        val movie: InTheatresResult = list[position]

        holder.bind(movie)
    }

    fun updateInTheatreList(movieList: MutableList<InTheatresResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    class InTheatresListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.in_theatres_list, parent, false)) {

        private val binding = InTheatresListBinding.inflate(inflater)

        private var mImageView: ImageView? = null
        private var mTextView: TextView? = null

        init {
            binding.apply {
                mImageView = itemView.in_theatre_recycler_image
                mTextView = itemView.in_theatre_recycler_text

                invalidateAll()
            }
        }

        fun bind(movie: InTheatresResult) {
            binding.inTheatresViewModel = movie

            mTextView?.text = movie.title
        }
    }
}