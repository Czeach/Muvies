package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.databinding.SeasonDetailsFragmentBinding
import com.czech.muvies.utils.Converter
import com.czech.muvies.viewModels.SeasonDetailsViewModel
import kotlinx.android.synthetic.main.season_details_fragment.*

class SeasonDetailsFragment : Fragment() {

    private lateinit var viewModel: SeasonDetailsViewModel
    private lateinit var binding: SeasonDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SeasonDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(SeasonDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.seasonDetailsViewModel = viewModel

        return inflater.inflate(R.layout.season_details_fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seasonArgs = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).seasonArgs
        val showArgs = SeasonDetailsFragmentArgs.fromBundle(requireArguments()).showArgs

        if (showArgs != null) {
            title.text = showArgs.name
        }

        if (seasonArgs != null) {
            Glide.with(this)
                .load("$BASE_IMAGE_PATH${seasonArgs.posterPath}")
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.poster_error)
                .into(poster)

            season_number.text =
                if (seasonArgs.seasonNumber == 0) "Specials"
                else "Season ${seasonArgs.seasonNumber}"

            val releaseYear = Converter.convertDateToYear(seasonArgs.airDate)
            release_year.text = "($releaseYear)"

            episode_number.text = "${seasonArgs.episodeCount} episodes"
        }
    }

}