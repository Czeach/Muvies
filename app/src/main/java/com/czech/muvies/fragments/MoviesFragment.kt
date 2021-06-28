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
import com.czech.muvies.adapters.*
import com.czech.muvies.databinding.MoviesFragmentBinding
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.network.MoviesRespository
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.MovieViewModelFactory
import com.czech.muvies.viewModels.MoviesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("UNCHECKED_CAST")
class MoviesFragment : Fragment() {

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding


    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf()){
            val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                null, null, null, it, null, null, null,
                null, null, null, null, null, null)
            findNavController().navigate(args)
        }

    private val inTheatersItemListener by lazy {
        object : inTheatersItemClickListenerS {
            override fun invoke(it: Movies.MoviesResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, it, null, null, null, null, null,
                    null, null, null, null, null, null)
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
                    null, null, null, null, null, null, null)
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
                    null, it, null, null, null, null, null)
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
                    null, null, null, it, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private var trendingMoviesAdapter =
        TrendingMoviesListAdapter(arrayListOf(), trendingClickListener)

    private var  TAG = "MoviesFragment"

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this,
            MovieViewModelFactory(MoviesApiService.getService(), MoviesRespository(MoviesApiService.getService())))
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

            searchMovies.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToSearchMoviesFragment()
                findNavController().navigate(args)
            }
        }

        viewModel.moviesResponse.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    binding.apply {
                        moviesScroll.visibility = View.GONE
                        loadingAnimHolder.visibility = View.VISIBLE
                    }
                }

                Status.SUCCESS -> {

                    val inTheatersList = mutableListOf<Movies.MoviesResult>()
                    val upcomingList = mutableListOf<Movies.MoviesResult>()
                    val popularList = mutableListOf<Movies.MoviesResult>()
                    val topRatedList = mutableListOf<Movies.MoviesResult>()
                    val trendingList = mutableListOf<Movies.MoviesResult>()

                    resource.data.let { movies ->
                        movies?.forEachIndexed { index, it ->
                            when (it.movieCategory) {

                                Movies.MoviesResult.MovieCategory.IN_THEATER -> {
                                    movies[index].let {
                                        inTheatersList.add(it)
                                        inTheatersAdapter.updateInTheatreList(inTheatersList)
                                    }
                                }

                                Movies.MoviesResult.MovieCategory.UPCOMING -> {
                                    movies[index].let {
                                        upcomingList.add(it)
                                        upcomingAdapter.updateUpcomingList(upcomingList)
                                    }
                                }

                                Movies.MoviesResult.MovieCategory.POPULAR -> {
                                    movies[index].let {
                                        popularList.add(it)
                                        popularAdapter.updatePopularList(popularList)
                                    }
                                }

                                Movies.MoviesResult.MovieCategory.TOP_RATED -> {
                                    movies[index].let {
                                        topRatedList.add(it)
                                        topRatedAdapter.updateTopRatedList(topRatedList)
                                    }
                                }

                                Movies.MoviesResult.MovieCategory.TRENDING -> {
                                    movies[index].let {
                                        trendingList.add(it)
                                        trendingMoviesAdapter.updateTrendingMoviesList(trendingList)
                                    }
                                }
                            }
                        }
                    }

                    binding.apply {
                        moviesScroll.visibility = View.VISIBLE
                        loadingAnimHolder.visibility = View.GONE
                    }
                }

                Status.ERROR -> {

                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            inTheatersSeeAll.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToPagedMoviesFragment(
                    inTheatresTitle.toString(), null, null, null, null
                )
                findNavController().navigate(args)
            }
            upcomingSeeAll.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToPagedMoviesFragment(
                    null, upcomingTitle.toString(), null, null, null
                )
                findNavController().navigate(args)
            }
            popularSeeAll.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToPagedMoviesFragment(
                    null, null, popularTitle.toString(), null, null
                )
                findNavController().navigate(args)
            }
            topRatedSeeAll.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToPagedMoviesFragment(
                    null, null, null, topRatedTitle.toString(), null
                )
                findNavController().navigate(args)
            }
            trendingSeeAll.setOnClickListener {
                val args = MoviesFragmentDirections.actionMoviesFragmentToPagedMoviesFragment(
                    null, null, null, null, trendingMoviesTitle.toString()
                )
                findNavController().navigate(args)
            }
        }
    }

}