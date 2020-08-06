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
import com.czech.muvies.models.InTheatersResult
import com.czech.muvies.models.UpcomingResult
import com.czech.muvies.viewModels.MoviesViewModel

class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesFragmentBinding

    private val upcomingClickListener by lazy {
        object : upcomingSItemClickListener {
            override fun invoke(it: UpcomingResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, null, null, it
                )
                findNavController().navigate(args)
            }

        }
    }
    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf(), upcomingClickListener)

    private val inTheatersItemListener by lazy {
        object : inTheatersItemClickListenerS {
            override fun invoke(it: InTheatersResult) {
                val args = MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(
                    null, it, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var inTheatersAdapter =
        InTheatersMiniListAdapter(arrayListOf(), inTheatersItemListener)

    private var popularAdapter =
        PopularListAdapter(arrayListOf())

    private var topRatedAdapter =
        TopRatedListAdapter(arrayListOf())

    private var trendingMoviesAdapter =
        TrendingMoviesListAdapter(arrayListOf())

    private var  TAG = "MoviesFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
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
            upcomingLiveData.observe(viewLifecycleOwner, Observer {
                upcomingAdapter.updateUpcomingList(it)
            })
            popularLiveData.observe(viewLifecycleOwner, Observer {
                popularAdapter.updatePopularList(it)
            })
            topRatedLiveData.observe(viewLifecycleOwner, Observer {
                topRatedAdapter.updateTopRatedList(it)
            })
            inTheatersLiveData.observe(viewLifecycleOwner, Observer {
                inTheatersAdapter.updateInTheatreList(it)
            })
            trendingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                trendingMoviesAdapter.updateTrendingMoviesList(it)
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