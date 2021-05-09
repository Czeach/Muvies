package com.czech.muvies.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.*
import com.czech.muvies.databinding.TvShowsFragmentBinding
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.MovieViewModelFactory
import com.czech.muvies.viewModels.MoviesViewModel
import com.czech.muvies.viewModels.TvShowsViewModel
import com.czech.muvies.viewModels.TvShowsViewModelFactory
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class TvShowsFragment : Fragment() {

    private lateinit var viewModel: TvShowsViewModel
    private lateinit var binding: TvShowsFragmentBinding

    private val airingTodayClickListener by lazy {
        object : airingTodaySItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, it, null, null, null, null,
                    null, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var airingTodayAdapter =
        AiringTodayListAdapter(arrayListOf(), airingTodayClickListener)

    private val onAirClickListener by lazy {
        object : onAirSItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, it, null, null,
                    null, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var onAirAdapter =
        OnAirListAdapter(arrayListOf(), onAirClickListener)

    private val popularTvClickListener by lazy {
        object : popularTvSItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, it,
                    null, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var popularTvAdapter =
        PopularTvListAdapter(arrayListOf(), popularTvClickListener)

    private val topRatedTvClickListener by lazy { 
        object : topRatedTvSItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    null, it, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var topRatedTvAdapter =
        TopRatedTvListAdapter(arrayListOf(), topRatedTvClickListener)

    private val trendingClickListener by lazy {
        object : trendingTvSItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, null, it, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private var trendingTvAdapter =
        TrendingTvListAdapter(arrayListOf(), trendingClickListener)

    private fun airingTodaySkeleton() {

        binding.airingTodayListRecycler.loadSkeleton(R.layout.airing_today_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun onAirSkeleton() {

        binding.onAirListRecycler.loadSkeleton(R.layout.on_air_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun popularSkeleton() {

        binding.popularTvListRecycler.loadSkeleton(R.layout.popular_tv_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun topRatedSkeleton() {

        binding.topRatedTvListRecycler.loadSkeleton(R.layout.top_rated_tv_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    private fun trendingSkeleton() {

        binding.trendingTvRecycler.loadSkeleton(R.layout.trending_tv_list) {

            color(R.color.colorSkeleton)
            shimmer(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvShowsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, TvShowsViewModelFactory(MoviesApiService.getService()))
            .get(TvShowsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.tvShowsViewModel = viewModel

        airingTodaySkeleton()
        onAirSkeleton()
        popularSkeleton()
        topRatedSkeleton()
        trendingSkeleton()

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

            getAiringToday().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    Handler().postDelayed({

                                        binding.airingTodayListRecycler.hideSkeleton()

                                    }, 2000)

                                    airingTodayAdapter.updateAiringTodayList(credits.results as MutableList<TvShows.TvShowsResult>)
                                }
                            }
                        }

                        Status.LOADING -> {

                            airingTodaySkeleton()
                        }

                        Status.ERROR -> {}
                    }
                }
            })

            getOnAir().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    Handler().postDelayed({

                                        binding.onAirListRecycler.hideSkeleton()

                                    }, 2000)

                                    onAirAdapter.updateOnAirList(credits.results as MutableList<TvShows.TvShowsResult>)
                                }
                            }
                        }

                        Status.LOADING -> {

                            onAirSkeleton()
                        }

                        Status.ERROR -> {}
                    }
                }
            })

            getPopular().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let { credits ->
                                if (credits != null) {

                                    Handler().postDelayed({

                                        binding.popularTvListRecycler.hideSkeleton()

                                    }, 2000)

                                    popularTvAdapter.updatePopularTvList(credits.results as MutableList<TvShows.TvShowsResult>)
                                }
                            }
                        }

                        Status.LOADING -> {

                            popularSkeleton()
                        }

                        Status.ERROR -> {}
                    }
                }
            })

            getTopRated().observe(viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data.let {credits ->
                                if (credits != null) {

                                    Handler().postDelayed({

                                        binding.topRatedTvListRecycler.hideSkeleton()

                                    }, 2000)

                                    topRatedTvAdapter.updateTopRatedTvList(credits.results as MutableList<TvShows.TvShowsResult>)
                                }
                            }
                        }

                        Status.LOADING -> {

                            topRatedSkeleton()
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

                                    Handler().postDelayed({

                                        binding.trendingTvRecycler.hideSkeleton()

                                    }, 2000)

                                    trendingTvAdapter.updateTrendingTvList(credits.results as MutableList<TvShows.TvShowsResult>)
                                }
                            }
                        }

                        Status.LOADING -> {

                            trendingSkeleton()
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
            airingTodaySeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_tvShowsFragment_to_airingTodayFragment)
            }
            onAirSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_tvShowsFragment_to_onAirFragment)
            }
            popularSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_tvShowsFragment_to_popularShowsFragment)
            }
            topRatedSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_tvShowsFragment_to_topRatedShowsFragment)
            }
            trendingSeeAll.setOnClickListener {
                findNavController().navigate(R.id.action_tvShowsFragment_to_trendingShowsFragment)
            }
        }
    }

}