package com.example.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.adapters.*
import com.example.muvies.databinding.TvShowsFragmentBinding
import com.example.muvies.viewModels.TvShowsViewModel

class TvShowsFragment : Fragment() {

    companion object {
        fun newInstance() = TvShowsFragment()
    }

    private lateinit var viewModel: TvShowsViewModel

    private var airingTodayAdapter =
        AiringTodayListAdapter(arrayListOf())

    private var onAirAdapter =
        OnAirListAdapter(arrayListOf())

    private var popularTvAdapter =
        PopularTvListAdapter(arrayListOf())

    private var topRatedTvAdapter =
        TopRatedTvListAdapter(arrayListOf())

    private var trendingTvAdapter =
        TrendingTvListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = TvShowsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TvShowsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.tvShowsViewModel = viewModel

        binding.apply {
            airingTodayListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = airingTodayAdapter
            }
            onAirListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = onAirAdapter
            }
            popularTvListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = popularTvAdapter
            }
            topRatedTvListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = topRatedTvAdapter
            }
            trendingTvRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = trendingTvAdapter
            }
        }

        viewModel.apply {
            airingTodayLiveData.observe(viewLifecycleOwner, Observer {
                airingTodayAdapter.updateAiringTodayList(it)
            })
            onAirLiveData.observe(viewLifecycleOwner, Observer {
                onAirAdapter.updateOnAirList(it)
            })
            popularTvLiveData.observe(viewLifecycleOwner, Observer {
                popularTvAdapter.updatePopularTvList(it)
            })
            topRatedTvLiveData.observe(viewLifecycleOwner, Observer {
              topRatedTvAdapter.updateTopRatedTvList(it)
            })
            trendingTvLiveData.observe(viewLifecycleOwner, Observer {
                trendingTvAdapter.updateTrendingTvList(it)
            })
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}