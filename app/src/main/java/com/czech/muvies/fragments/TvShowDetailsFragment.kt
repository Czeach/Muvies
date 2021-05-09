package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.SeasonsAdapter
import com.czech.muvies.adapters.ShowCastAdapter
import com.czech.muvies.adapters.ShowsGenreAdapter
import com.czech.muvies.adapters.showCastItemClickListener
import com.czech.muvies.databinding.TvShowDetailsFragmentBinding
import com.czech.muvies.models.SimilarTvShows
import com.czech.muvies.models.TvShowCredits
import com.czech.muvies.models.TvShowDetails
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.pagedAdapters.SimilarTvShowsAdapter
import com.czech.muvies.pagedAdapters.similarTvItemClickListener
import com.czech.muvies.utils.Converter
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.TvShowDetailsViewModel
import com.czech.muvies.viewModels.TvShowDetailsViewModelFactory
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.android.synthetic.main.movie_details_fragment.backdrop
import kotlinx.android.synthetic.main.movie_details_fragment.lang_text
import kotlinx.android.synthetic.main.movie_details_fragment.rating_bar
import kotlinx.android.synthetic.main.movie_details_fragment.rating_fraction
import kotlinx.android.synthetic.main.movie_details_fragment.release_year
import kotlinx.android.synthetic.main.tv_show_details_fragment.*
import kotlinx.android.synthetic.main.tv_show_details_fragment.details
import kotlinx.android.synthetic.main.tv_show_details_fragment.homepage
import kotlinx.android.synthetic.main.tv_show_details_fragment.status
import kotlinx.android.synthetic.main.tv_show_details_fragment.vote_count
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TvShowDetailsFragment() : Fragment() {

    private lateinit var viewModel: TvShowDetailsViewModel
    private lateinit var binding: TvShowDetailsFragmentBinding

    var navController: NavController? = null

    private var genreAdapter = ShowsGenreAdapter(arrayListOf())

    private val showCastListener by lazy {
        object : showCastItemClickListener {
            override fun invoke(it: TvShowCredits.Cast) {
                val args = TvShowDetailsFragmentDirections.actionTvShowsDetailsFragmentToCastDetailsFragment(null, it)
                findNavController().navigate(args)
            }

        }
    }
    private var castAdapter = ShowCastAdapter(arrayListOf(), showCastListener)

    private var seasonsAdapter = SeasonsAdapter(arrayListOf())

    private val similarTvClickListener by lazy {
        object : similarTvItemClickListener {
            override fun invoke(it: SimilarTvShows.SimilarTvShowsResult) {
                val args = TvShowDetailsFragmentDirections.actionTvShowsDetailsFragmentSelf(
                    null, null, null, null, null, null,
                    null, null, null, null, it
                )
                findNavController().navigate(args)
            }

        }
    }
    private var similarAdapter = SimilarTvShowsAdapter(similarTvClickListener)

    private fun genreSkeleton() {

        binding.showsGenreList.loadSkeleton(R.layout.genre_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun castSkeleton() {

        binding.castList.loadSkeleton(R.layout.cast_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun seasonSkeleton() {

        binding.seasonsList.loadSkeleton(R.layout.seasons_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun similarSkeleton() {

        binding.similarShows.loadSkeleton(R.layout.similar_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvShowDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, TvShowDetailsViewModelFactory(MoviesApiService.getService()))
            .get(TvShowDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.tvShowsDetailsViewModel = viewModel

        binding.apply {

            showsGenreList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = genreAdapter
            }

            seasonsList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = seasonsAdapter
            }

            similarShows.apply {
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
                adapter = similarAdapter
            }

            castList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
            }

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

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
        val similarTvArgs = TvShowDetailsFragmentArgs.fromBundle(requireArguments()).similarTvArgs

        if (airingTodaySArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodaySArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = airingTodaySArgs.name

            release_year.text = Converter.convertDateToYear(airingTodaySArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = airingTodaySArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodaySArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodaySArgs.originalLanguage

            getDetails(airingTodaySArgs.id)
        }

        if (airingTodayArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${airingTodayArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = airingTodayArgs.name

            release_year.text = Converter.convertDateToYear(airingTodayArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = airingTodayArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = airingTodayArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = airingTodayArgs.originalLanguage

            getDetails(airingTodayArgs.id)
        }

        if (onAirSArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = onAirSArgs.name

            release_year.text = Converter.convertDateToYear(onAirSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = onAirSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirSArgs.originalLanguage

            getDetails(onAirSArgs.id)
        }

        if (onAirArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${onAirArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = onAirArgs.name

            release_year.text = Converter.convertDateToYear(onAirArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = onAirArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = onAirArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = onAirArgs.originalLanguage

            getDetails(onAirArgs.id)
        }

        if (popularTvSArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = popularTvSArgs.name

            release_year.text = Converter.convertDateToYear(popularTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = popularTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvSArgs.originalLanguage

            getDetails(popularTvSArgs.id)
        }

        if (popularTvArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${popularTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = popularTvArgs.name

            release_year.text = Converter.convertDateToYear(popularTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = popularTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = popularTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = popularTvArgs.originalLanguage

            getDetails(popularTvArgs.id)
        }

        if (topRatedTvSArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = topRatedTvSArgs.name

            release_year.text = Converter.convertDateToYear(topRatedTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = topRatedTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvSArgs.originalLanguage

            getDetails(topRatedTvSArgs.id)
        }

        if (topRatedTvArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${topRatedTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = topRatedTvArgs.name

            release_year.text = Converter.convertDateToYear(topRatedTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = topRatedTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = topRatedTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = topRatedTvArgs.originalLanguage

            getDetails(topRatedTvArgs.id)
        }

        if (trendingTvSArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvSArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = trendingTvSArgs.name

            release_year.text = Converter.convertDateToYear(trendingTvSArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = trendingTvSArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvSArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvSArgs.originalLanguage

            getDetails(trendingTvSArgs.id)
        }

        if (trendingTvArgs != null) {

            Glide.with(this)
                .load("$BASE_IMAGE_PATH${trendingTvArgs.backdropPath}")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(backdrop)

            name.text = trendingTvArgs.name

            release_year.text = Converter.convertDateToYear(trendingTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = trendingTvArgs.voteAverage/2
            ratingBar.rating = rating.toFloat()

            rating_fraction.text = trendingTvArgs.voteAverage.toFloat().toString() + "/10.0"

            lang_text.text = trendingTvArgs.originalLanguage

            getDetails(trendingTvArgs.id)
        }

        if (similarTvArgs != null) {

            name.text = similarTvArgs.name

            release_year.text = Converter.convertDateToYear(similarTvArgs.firstAirDate)

            val ratingBar = rating_bar
            val rating = similarTvArgs.voteAverage?.div(2)
            if (rating != null) {
                ratingBar.rating = rating.toFloat()
            }

            rating_fraction.text = similarTvArgs.voteAverage?.toFloat().toString() + "/10.0"

            lang_text.text = similarTvArgs.originalLanguage

            similarTvArgs.id?.let { getDetails(it) }
        }



        homepage.movementMethod = LinkMovementMethod.getInstance()
        homepage.setOnClickListener {

            homepage.highlightColor = resources.getColor(R.color.colorPrimary)

            val url = homepage.text.toString()
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(url)
            startActivity(browserIntent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDetails(showId: Int) {

        genreSkeleton()
        castSkeleton()
        seasonSkeleton()
        similarSkeleton()

        viewModel.getTvShowDetails(showId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                        resource.data.let { showDetails ->

                            if (showDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${showDetails.backdropPath}")
                                    .placeholder(R.drawable.backdrop_placeholder)
                                    .into(backdrop)

                                Handler().postDelayed({

                                    binding.showsGenreList.hideSkeleton()
                                }, 2000)

                                genreAdapter.updateList(showDetails.genres as List<TvShowDetails.Genre>)

                                original_name.text = showDetails.originalName

                                status.text = showDetails.status

                                first_date.text = Converter.convertDate(showDetails.firstAirDate)

                                last_date.text = Converter.convertDate(showDetails.lastAirDate)

                                next_date.text = Converter.convertDate(showDetails.nextEpisodeToAir?.airDate)

                                next_episode.text =
                                    "Season ${showDetails.nextEpisodeToAir?.seasonNumber} episode ${showDetails.nextEpisodeToAir?.episodeNumber}"

                                next_episode.text =
                                    if (showDetails.nextEpisodeToAir?.airDate == null) ""
                                else "Season ${showDetails.nextEpisodeToAir.seasonNumber} episode ${showDetails.nextEpisodeToAir.episodeNumber}"

                                vote_count.text = "${showDetails.voteCount} votes"

                                seasons.text =
                                if (showDetails.numberOfSeasons == 1) "${showDetails.numberOfSeasons} season ${showDetails.numberOfEpisodes} episodes"
                                    else "${showDetails.numberOfSeasons} seasons ${showDetails.numberOfEpisodes} episodes"

                                homepage.text = showDetails.homepage

                                synopsis.text = showDetails.overview

                                Handler().postDelayed({

                                    binding.seasonsList.hideSkeleton()
                                }, 2000)

                                seasonsAdapter.updateList(showDetails.seasons as List<TvShowDetails.Season>)

                                seasonsAdapter.setUpListener(object : SeasonsAdapter.ItemCLickedListener {
                                    override fun onItemClicked(season: TvShowDetails.Season) {
                                        val bundle = bundleOf(
                                            "seasonArgs" to season,
                                            "showId" to showDetails.id,
                                            "showName" to showDetails.name,
                                            "backdrop" to showDetails.backdropPath
                                        )
                                        navController!!.navigate(
                                            R.id.action_tvShowsDetailsFragment_to_seasonDetailsFragment, bundle
                                        )
                                    }

                                })
                            }
                        }
                        details.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {

                        genreSkeleton()
                        seasonSkeleton()

                        details.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        viewModel.getSimilarTvShows(showId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            Handler().postDelayed({
                binding.similarShows.hideSkeleton()
            }, 2000)

            similarAdapter.submitList(it)
        })

        viewModel.getCast(showId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {

                        Handler().postDelayed({

                            binding.castList.hideSkeleton()
                        }, 2000)

                        resource.data.let {credits ->
                            if (credits != null) {

                                castAdapter.updateList(credits.cast as List<TvShowCredits.Cast>)
                            }
                        }
                    }
                    Status.LOADING -> {
                        castSkeleton()
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