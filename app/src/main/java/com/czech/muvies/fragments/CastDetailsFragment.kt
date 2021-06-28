package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.*
import com.czech.muvies.databinding.CastDetailsFragmentBinding
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Converter
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.CastDetailsViewModel
import com.czech.muvies.viewModels.CastDetailsViewModelFactory
import kotlinx.android.synthetic.main.cast_details_fragment.*
import kotlinx.android.synthetic.main.cast_details_fragment.name
import kotlinx.android.synthetic.main.tv_show_details_fragment.*

class CastDetailsFragment : Fragment() {

    private lateinit var viewModel: CastDetailsViewModel
    private lateinit var binding: CastDetailsFragmentBinding

    private lateinit var tabAdapter: TabAdapter

    private val castMoviesListener by lazy {
        object : castMoviesClickListener {
            override fun invoke(it: PersonMovies.Cast) {
                val args = CastDetailsFragmentDirections.actionCastDetailsFragmentToDetailsFragment(null, null,
                null, null, null, null, null, null, null,
                null, null, it, null)
                findNavController().navigate(args)
            }
        }
    }

    private var moviesAdapter = CastMoviesTabAdapter(arrayListOf(), castMoviesListener)

    private val castShowsListener by lazy {
        object : castShowsClickListener {
            override fun invoke(it: PersonTvShows.Cast) {
                val args = CastDetailsFragmentDirections.actionCastDetailsFragmentToTvShowsDetailsFragment(null, null,
                    null, null, null, null, null, null, null,
                    null, null, it, null)
                findNavController().navigate(args)
            }
        }
    }

    private var showsAdapter = CastTvShowsTabAdapter(arrayListOf(), castShowsListener)

    val args: CastDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = CastDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, CastDetailsViewModelFactory(MoviesApiService.getService()))
            .get(CastDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.castDetailsViewModel = viewModel

        binding.apply {

            castMovies.apply {
                layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
            }

            castShows.apply {
                layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                adapter = showsAdapter
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviePerson = CastDetailsFragmentArgs.fromBundle(requireArguments()).moviePersonArgs
        val showPerson = CastDetailsFragmentArgs.fromBundle(requireArguments()).showPersonArgs

        if (moviePerson != null) {

            moviePerson.id?.let { getPersonDetails(it) }
        }

        if (showPerson != null) {

            showPerson.id?.let { getPersonDetails(it) }
        }

        cast_movies_toggle.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> {
                    binding.apply {
                        castMoviesToggle.background = ContextCompat.getDrawable(requireContext(), R.drawable.border)
                        castShowsToggle.setBackgroundColor(Color.TRANSPARENT)

                        castMovies.visibility = View.VISIBLE
                        castShows.visibility = View.GONE
                    }
                }

            }
        }

        cast_shows_toggle.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> {
                    binding.apply {
                        castShowsToggle.setBackgroundColor(Color.TRANSPARENT)
                        castShowsToggle.background = ContextCompat.getDrawable(requireContext(), R.drawable.border)

                        castShows.visibility = View.VISIBLE
                        castMovies.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getPersonDetails(personId: Int) {
        viewModel.getCastDetails(personId).observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.LOADING -> {

                        binding.apply {
                            loadingSpinner.visibility = View.VISIBLE
                            castLayout.visibility = View.GONE
                        }
                    }

                    Status.SUCCESS -> {

                        binding.apply {
                            loadingSpinner.visibility = View.GONE
                            castLayout.visibility = View.VISIBLE
                        }

                        resource.data.let { personDetails ->

                            if (personDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${personDetails.profilePath}")
                                    .placeholder(R.drawable.person_placeholder)
                                    .error(R.drawable.person_placeholder)
                                    .into(cast_image)

                                when(personDetails.gender) {

                                    1 -> binding.gender.text = "female"
                                    2 -> binding.gender.text = "male"
                                }

                                binding.apply {
                                    name.text = personDetails.name

                                    birthday.text = "Born on ${Converter.convertDate(personDetails.birthday)}"

                                    if (personDetails.biography?.isEmpty() == true) {
                                        binding.textView.visibility = View.GONE
                                    } else {
                                        binding.biography.text = personDetails.biography
                                    }

                                    biography.setOnClickListener {
                                        if (biography.maxLines != Int.MAX_VALUE) {

                                            binding.apply {
                                                biography.ellipsize = null
                                                biography.maxLines = Int.MAX_VALUE
                                            }
                                        } else {

                                            binding.apply {
                                                biography.ellipsize = TextUtils.TruncateAt.END
                                                biography.maxLines = 5
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                    }
                }
            }
        })

        viewModel.getCastMovies(personId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { credits ->
                            if (credits != null) {

                                moviesAdapter.updateList(credits.cast as List<PersonMovies.Cast>)
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

        viewModel.getCastShows(personId).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { credits ->
                            if (credits != null) {

                                showsAdapter.updateList(credits.cast as List<PersonTvShows.Cast>)
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

}