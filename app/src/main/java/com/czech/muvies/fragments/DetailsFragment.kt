package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.viewModels.DetailsViewModel
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.paged_list.view.*
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inTheatersArgs = DetailsFragmentArgs.fromBundle(requireArguments()).inTheaterArgs

        Glide.with(this)
            .load("$BASE_IMAGE_PATH${inTheatersArgs.backdropPath}")
            .placeholder(R.drawable.backdrop_placeholder)
            .into(backdrop)

        Glide.with(this)
            .load("$BASE_IMAGE_PATH${inTheatersArgs.posterPath}")
            .placeholder(R.drawable.poster_placeholder)
            .into(poster)

        title.text = inTheatersArgs.title

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse("${inTheatersArgs.releaseDate} 09:10:46", dateFormatter)

        val year = date.year.toString()
        val month = date.month.toString().toLowerCase()

        release_date.text = "$month $year"

        var ratingBar = rating_bar
        val rating = inTheatersArgs.voteAverage/2
        ratingBar.rating = rating.toFloat()

        rating_fraction.text = "${inTheatersArgs.voteAverage.toFloat()}/10.0"

        lang_text.text = inTheatersArgs.originalLanguage.toUpperCase()

        overview.text = inTheatersArgs.overview

    }

}