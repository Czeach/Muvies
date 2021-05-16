package com.czech.muvies.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.*
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.MovieViewModelFactory
import com.czech.muvies.viewModels.MoviesViewModel

@Suppress("UNCHECKED_CAST")
class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding

    private val upcomingClickListener by lazy {
        object : upcomingSItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, null, null, it, null, null, null,
                    null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf(), upcomingClickListener)

    private val inTheatersItemListener by lazy {
        object : inTheatersItemClickListenerS {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, it, null, null, null, null, null,
                    null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var inTheatersAdapter =
        InTheatersMiniListAdapter(arrayListOf(), inTheatersItemListener)

    private val popularClickListener by lazy {
        object : popularSItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, it,
                    null, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var popularAdapter =
        PopularListAdapter(arrayListOf(), popularClickListener)

    private val topRatedClickListener by lazy {
        object : topRatedSItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null,
                    null, it, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var topRatedAdapter =
        TopRatedListAdapter(arrayListOf(), topRatedClickListener)

    private val trendingClickListener by lazy {
        object : trendingSItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, null, it, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var trendingMoviesAdapter =
        TrendingMoviesListAdapter(arrayListOf(), trendingClickListener)

    private var  TAG = "MoviesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, MovieViewModelFactory(MoviesApiService.getService()))
            .get(MoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.moviesViewModel = viewModel

        binding.apply {
            upcomingListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingAdapter
            }
            inTheatersMiniListRecycler.apply {
                layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
                adapter = inTheatersAdapter
            }
            popularListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = popularAdapter
            }
            topRatedListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = topRatedAdapter
            }
            trendingMoviesRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = trendingMoviesAdapter
            }
        }

        viewModel.apply {

            getInTheater().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    inTheatersAdapter.updateInTheatreList(credits.results as MutableList<Movies.MoviesResult>)

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

            getUpcoming().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    upcomingAdapter.updateUpcomingList(credits.results as MutableList<Movies.MoviesResult>)
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

            getPopular().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    popularAdapter.updatePopularList(credits.results as MutableList<Movies.MoviesResult>)
                                }
                            }
                        }

                        Status.LOADING -> {
                        }

                        Status.ERROR -> {}
                    }
                }
            })

            getTopRated().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    topRatedAdapter.updateTopRatedList(credits.results as MutableList<Movies.MoviesResult>)
                                }
                            }
                        }

                        Status.LOADING -> {
                        }

                        Status.ERROR -> {}
                    }
                }
            })

            getTrending().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    trendingMoviesAdapter.updateTrendingMoviesList(credits.results as MutableList<Movies.MoviesResult>)
                                }
                            }
                        }

                        Status.LOADING -> {
                        }

                        Status.ERROR -> {}
                    }
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            inTheatersSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_moviesFragment_to_inTheatersFragment)
            }
            upcomingSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_moviesFragment_to_upcomingFragment)
            }
            popularSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_moviesFragment_to_popularFragment)
            }
            topRatedSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_moviesFragment_to_topRatedMoviesFragment)
            }
            trendingSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_moviesFragment_to_trendingMoviesFragment)
            }
        }
    }

}