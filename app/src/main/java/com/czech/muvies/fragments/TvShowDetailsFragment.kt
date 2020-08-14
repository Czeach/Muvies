package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.viewModels.TvShowDetailsViewModel
import kotlinx.android.synthetic.main.movie_details_fragment.backdrop
import kotlinx.android.synthetic.main.movie_details_fragment.lang_text
import kotlinx.android.synthetic.main.movie_details_fragment.overview
import kotlinx.android.synthetic.main.movie_details_fragment.rating_bar
import kotlinx.android.synthetic.main.movie_details_fragment.rating_fraction
import kotlinx.android.synthetic.main.movie_details_fragment.release_year
import kotlinx.android.synthetic.main.tv_show_details_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TvShowDetailsFragment : Fragment() {

    private lateinit var viewModel: TvShowDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tv_show_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TvShowDetailsViewModel::class.java)

        val airingTodaySArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).airingTodaySArgs
        val airingTodayArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).airingTodayArgs
        val onAirSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).onAirSArgs
        val onAirArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).onAirArgs
        val popularTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).popularTvSArgs
        val popularTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).popularTvArgs
        val topRatedTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).topRatedTvSArgs
        val topRatedTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).topRatedTvArgs
        val trendingTvSArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).trendingTvSArgs
        val trendingTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).trendingTvArgs

        if (airingTodaySArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = airingTodaySArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(airingTodaySArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = airingTodaySArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodaySArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodaySArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = airingTodaySArgs.overview
        }

        if (airingTodayArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = airingTodayArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(airingTodayArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = airingTodayArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodayArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodayArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = airingTodayArgs.overview
        }

        if (onAirSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = onAirSArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(onAirSArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = onAirSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = onAirSArgs.overview
        }

        if (onAirArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = onAirArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(onAirArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = onAirArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = onAirArgs.overview
        }

        if (popularTvSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = popularTvSArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(popularTvSArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = popularTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = popularTvSArgs.overview
        }

        if (popularTvArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = popularTvArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(popularTvArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = popularTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = popularTvArgs.overview
        }

        if (topRatedTvSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = topRatedTvSArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(topRatedTvSArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = topRatedTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = topRatedTvSArgs.overview
        }

        if (topRatedTvArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = topRatedTvArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(topRatedTvArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = topRatedTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = topRatedTvArgs.overview
        }

        if (trendingTvSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvSArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = trendingTvSArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(trendingTvSArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = trendingTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvSArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = trendingTvSArgs.overview
        }

        if (trendingTvArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .into(poster)

            name.text = trendingTvArgs.name

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(trendingTvArgs.firstAirDate + " 09:10:46", dateFormatter)

            val year = date.year.toString()
            val month = date.month.toString().toLowerCase(Locale.ROOT)

            release_year.text = "$month $year"

            val ratingBar = rating_bar
            val rating = trendingTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvArgs.originalLanguage.toUpperCase(Locale.ROOT)

            overview.text = trendingTvArgs.overview
        }
    }
}