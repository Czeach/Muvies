package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.adapters.EpisodeAdapter
import com.czech.muvies.databinding.SeasonDetailsFragmentBinding
import com.czech.muvies.models.SeasonDetails
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Converter
import com.czech.muvies.utils.EpisodesListTransformer
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.SeasonDetailsViewModel
import com.czech.muvies.viewModels.SeasonDetailsViewModelFactory
import com.yarolegovich.discretescrollview.DSVOrientation
import kotlinx.android.synthetic.main.season_details_fragment.*

class SeasonDetailsFragment : Fragment() {

    private lateinit var viewModel: SeasonDetailsViewModel
    private lateinit var binding: SeasonDetailsFragmentBinding

    private var episodeAdapter = EpisodeAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SeasonDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, SeasonDetailsViewModelFactory(MoviesApiService.getService()))
            .get(SeasonDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.seasonDetailsViewModel = viewModel

        return inflater.inflate(R.layout.season_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showId = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).showId
        val showName = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).showName
        val backdrop = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).backdrop
        val seasonArgs = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).seasonArgs

        if (seasonArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${seasonArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)

            Glide.with(this)
                .load("$BASE_IMAGE_PATH$backdrop")
                .placeholder(R.drawable.backdrop_placeholder)
                .into(back_drop)

            title.text = showName

            season_number.text =
                if (seasonArgs.seasonNumber == 0) "Specials"
                else "Season ${seasonArgs.seasonNumber}"

            val releaseYear = Converter.convertDateToYear(seasonArgs.airDate)
            release_year.text = "($releaseYear)"

            episode_number.text = "${seasonArgs.episodeCount} episodes"

            getDetails(showId, seasonArgs.seasonNumber!!)
        }

        episodes_list.apply {
            adapter = episodeAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer(CompositePageTransformer().also {
                it.addTransformer(EpisodesListTransformer())
                it.addTransformer(MarginPageTransformer(100))
            })
        }
    }

    private fun getDetails(showId: Int, seasonNum: Int) {

        viewModel.getSeasonDetails(showId, seasonNum).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { seasonDetails ->

                            if (seasonDetails != null) {

                                if (seasonDetails.overview == null) {
                                    overview_layout.visibility = View.GONE
                                } else {
                                    overview_layout.visibility = View.VISIBLE
                                    overview.text = seasonDetails.overview
                                }

                                episodeAdapter.updateList(seasonDetails.episodes as List<SeasonDetails.Episode>)
                            }
                        }
                        details.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        details.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        details.visibility = View.GONE
                    }
                }
            }
        })
    }

}