package com.example.muvies.screens.featured

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.MainActivity
import com.example.muvies.adapters.InTheatersListAdapter

import com.example.muvies.adapters.PopularListAdapter
import com.example.muvies.adapters.TopRatedListAdapter
import com.example.muvies.adapters.UpcomingListAdapter
import com.example.muvies.databinding.FeaturedFragmentBinding

class FeaturedFragment : Fragment() {

    companion object {
        fun newInstance() = FeaturedFragment()
    }

    private lateinit var viewModel: FeaturedViewModel

    private var inTheatersAdapter =
        InTheatersListAdapter(arrayListOf())

    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf())

    private var popularAdapter =
        PopularListAdapter(arrayListOf())

    private var topRatedAdapter =
        TopRatedListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        (requireActivity() as MainActivity).title = "Muvies"

        val binding = FeaturedFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FeaturedViewModel::class.java)
        binding.lifecycleOwner = this
        binding.featuredViewModel = viewModel

        binding.apply {
            inTheatersListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = inTheatersAdapter
            }
            popularListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = popularAdapter
            }
            upcomingListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = upcomingAdapter
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

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).title = "Muvies"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(FeaturedViewModel::class.java)

    }

}
