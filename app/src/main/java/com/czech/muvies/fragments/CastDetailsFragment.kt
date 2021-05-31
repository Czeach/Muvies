package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

            castMoviesToggle.isChecked = true
            castShowsToggle.isChecked = false
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

        binding.castMoviesToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

                cast_movies.visibility = View.VISIBLE
                cast_shows.visibility = View.GONE
            }
        }

        binding.castShowsToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cast_shows.visibility = View.VISIBLE
                cast_movies.visibility = View.GONE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getPersonDetails(personId: Int) {
        viewModel.getCastDetails(personId).observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { personDetails ->

                            if (personDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${personDetails.profilePath}")
                                    .placeholder(R.drawable.person_placeholder)
                                    .error(R.drawable.person_placeholder)
                                    .into(cast_image)

                                name.text = personDetails.name

                                when(personDetails.gender) {

                                    1 -> gender.text = "female"
                                    2 -> gender.text = "male"
                                }

                                birthday.text = "Born on ${Converter.convertDate(personDetails.birthday)}"

                                biography.text = personDetails.biography

                                biography.setOnClickListener {
                                    if (biography.maxLines != Int.MAX_VALUE) {

                                        biography.ellipsize = null
                                        biography.maxLines = Int.MAX_VALUE
                                    } else {

                                        biography.ellipsize = TextUtils.TruncateAt.END
                                        biography.maxLines = 5
                                    }
                                }
                            }
                        }

                        bio.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        bio.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        bio.visibility = View.GONE
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