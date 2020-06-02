package com.example.muvies.screens.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.R
import com.example.muvies.adapters.InTheatersListAdapter
import com.example.muvies.adapters.PopularListAdapter
import com.example.muvies.adapters.TopRatedListAdapter
import com.example.muvies.adapters.UpcomingListAdapter
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
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = upcomingAdapter
            }
            inTheatersListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = inTheatersAdapter
            }
            popularListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = popularAdapter
            }

            topRatedListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = topRatedAdapter
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
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}