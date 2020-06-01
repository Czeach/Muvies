package com.example.muvies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.databinding.InTheatresListBinding
import com.example.muvies.model.InTheatersResult
import kotlinx.android.synthetic.main.in_theatres_list.view.*

class InTheatersListAdapter(private var list: MutableList<InTheatersResult>):
    RecyclerView.Adapter<InTheatersListAdapter.InTheatersListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InTheatersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return InTheatersListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: InTheatersListViewHolder, position: Int) {
        val movie: InTheatersResult = list[position]

        holder.bind(movie)
    }

    fun updateInTheatreList(movieList: MutableList<InTheatersResult>) {
        list = movieList
        notifyDataSetChanged()
    }

    class InTheatersListViewHolder(inflater: LayoutInflater, parent: ViewGroup):
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

        fun bind(movie: InTheatersResult) {
            binding.inTheatresViewModel = movie

            mTextView?.text = movie.title
        }
    }
}