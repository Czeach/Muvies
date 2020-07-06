package com.example.muvies.screens.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.adapters.*
import com.example.muvies.databinding.MoviesFragmentBinding

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel

    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf())

    private var inTheatersAdapter =
        InTheatersListAdapter(arrayListOf())

    private var popularAdapter =
        PopularListAdapter(arrayListOf())

    private var topRatedAdapter =
        TopRatedListAdapter(arrayListOf())

    private var trendingMoviesAdapter =
        TrendingMoviesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = MoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.moviesViewModel = viewModel

        binding.apply {
            upcomingListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingAdapter
            }
            inTheatersListRecycler.apply {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}