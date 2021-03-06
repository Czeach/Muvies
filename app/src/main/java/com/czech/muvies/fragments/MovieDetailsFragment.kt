package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.MovieCastAdapter
import com.czech.muvies.adapters.MoviesGenreAdapter
import com.czech.muvies.adapters.castItemClickListener
import com.czech.muvies.databinding.MovieDetailsFragmentBinding
import com.czech.muvies.models.MovieCredits
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.pagedAdapters.SimilarMoviesAdapter
import com.czech.muvies.pagedAdapters.similarItemClickListener
import com.czech.muvies.utils.Converter
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.MovieDetailsViewModel
import com.czech.muvies.viewModels.MovieDetailsViewModelFactory
import kotlinx.android.synthetic.main.movie_details_fragment.*

class MovieDetailsFragment : Fragment() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: MovieDetailsFragmentBinding

    private var genreAdapter = MoviesGenreAdapter(arrayListOf())

    private val castClickListener by lazy {
        object : castItemClickListener {
            override fun invoke(it: MovieCredits.Cast) {
                val args = MovieDetailsFragmentDirections.actionDetailsFragmentToCastDetailsFragment(it, null)
                findNavController().navigate(args)
            }

        }
    }
    private val castAdapter = MovieCastAdapter(arrayListOf(), castClickListener)

    private val similarClickListener by lazy {
        object : similarItemClickListener {
            override fun invoke(it: SimilarMovies.SimilarMoviesResult) {
                val args = MovieDetailsFragmentDirections.actionDetailsFragmentSelf(
                    null, null, null, null, null, null, null,
                    null, null, null, it
                )
                findNavController().navigate(args)
            }

        }
    }
    private var similarMoviesAdapter = SimilarMoviesAdapter(similarClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MovieDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, MovieDetailsViewModelFactory(MoviesApiService.getService()))
            .get(MovieDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.moviesDetailsViewModel = viewModel

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inTheatersArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).inTheaterArgs
        val inTheatersSArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).inTheaterSArgs
        val upcomingArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).upcomingArgs
        val upcomingSArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).upcomingSArgs
        val popularArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).popularArgs
        val popularSArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).popularSArgs
        val topRatedSArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).topRatedSArgs
        val topRatedArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).topRatedArgs
        val trendingSArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).trendingSArgs
        val trendingArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).trendingArgs
        val similarArgs = MovieDetailsFragmentArgs.fromBundle(requireArguments()).similarArgs

        if (inTheatersArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = inTheatersArgs.title

            release_year.text = Converter.convertDateToYear(inTheatersArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = inTheatersArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = inTheatersArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = inTheatersArgs.originalLanguage

            getDetails(inTheatersArgs.id)
        }

        if (inTheatersSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${inTheatersSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = inTheatersSArgs.title

            release_year.text = Converter.convertDateToYear(inTheatersSArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = inTheatersSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = inTheatersSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = inTheatersSArgs.originalLanguage

            getDetails(inTheatersSArgs.id)

        }

        if (upcomingArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = upcomingArgs.title

            release_year.text = Converter.convertDateToYear(upcomingArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = upcomingArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = upcomingArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = upcomingArgs.originalLanguage

            getDetails(upcomingArgs.id)
        }

        if (upcomingSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${upcomingSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = upcomingSArgs.title

            release_year.text = Converter.convertDateToYear(upcomingSArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = upcomingSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = upcomingSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = upcomingSArgs.originalLanguage

            getDetails(upcomingSArgs.id)
        }

        if (popularSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = popularSArgs.title

            release_year.text = Converter.convertDateToYear(popularSArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = popularSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularSArgs.originalLanguage

            getDetails(popularSArgs.id)
        }

        if (popularArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = popularArgs.title

            release_year.text = Converter.convertDateToYear(popularArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = popularArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularArgs.originalLanguage

            getDetails(popularArgs.id)
        }

        if (topRatedSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = topRatedSArgs.title

            release_year.text = Converter.convertDateToYear(topRatedSArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = topRatedSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedSArgs.originalLanguage

            getDetails(topRatedSArgs.id)
        }

        if (topRatedArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = topRatedArgs.title

            release_year.text = Converter.convertDateToYear(topRatedArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = topRatedArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedArgs.originalLanguage

            getDetails(topRatedArgs.id)
        }

        if (trendingSArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = trendingSArgs.title

            release_year.text = Converter.convertDateToYear(trendingSArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = trendingSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingSArgs.originalLanguage

            getDetails(trendingSArgs.id)
        }

        if (trendingArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            title.text = trendingArgs.title

            release_year.text = Converter.convertDateToYear(trendingArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = trendingArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingArgs.originalLanguage

            getDetails(trendingArgs.id)
        }

        if (similarArgs != null) {

            title.text = similarArgs.title

            release_year.text = Converter.convertDateToYear(similarArgs.releaseDate)

            val ratingBar = rating_bar
            val rating = similarArgs.voteAverage?.div(2)
            if (rating != null) {
                ratingBar.rating = rating.toFloat()
            }

            rating_fraction.text = similarArgs.voteAverage?.toFloat().toString() + "/10.0"

            lang_text.text = similarArgs.originalLanguage

            similarArgs.id?.let { getDetails(it) }

        }

        binding.moviesGenreList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }

        binding.similarMovies.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = similarMoviesAdapter
        }

        binding.castList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getDetails(movieId: Int) {

        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let {movieDetails ->

                            if (movieDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${movieDetails.backdropPath}")
                                    .placeholder(R.drawable.backdrop_placeholder)
                                    .into(backdrop)

                                genreAdapter.updateList(movieDetails.genres as List<MovieDetails.Genre>)

                                lang_text.text = movieDetails.originalLanguage

                                original_Title.text = movieDetails.originalTitle

                                tag_line.text = movieDetails.tagline

                                homepage.text = movieDetails.homepage

                                status.text = movieDetails.status

                                time.text = Converter.convertTime(movieDetails.runtime!!)

                                release_date.text = Converter.convertDate(movieDetails.releaseDate)

                                vote_count.text = "${movieDetails.voteCount.toString()} votes"

                                overview.text = movieDetails.overview
                            }
                        }
                        details.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        details.visibility = View.GONE
                    }
                    Status.ERROR -> {

                    }
                }
            }
        })

        viewModel.getSimilarMovies(movieId).observe(viewLifecycleOwner, Observer {
            similarMoviesAdapter.submitList(it)
        })

        viewModel.getCast(movieId).observe(viewLifecycleOwner, Observer {
            it?.let {  resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let {credits ->
                            if (credits != null) {
                                castAdapter.updateList(credits.cast as List<MovieCredits.Cast>)
                            }
                        }
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
    }
}