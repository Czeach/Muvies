package com.czech.muvies.fragments

import android.os.Bundle
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
import com.czech.muvies.network.ShowsRepository
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.TvShowsViewModel
import com.czech.muvies.viewModels.TvShowsViewModelFactory

class TvShowsFragment : Fragment() {

    private lateinit var viewModel: TvShowsViewModel
    private lateinit var binding: TvShowsFragmentBinding

    private val airingTodayClickListener by lazy {
        object : airingTodaySItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowsDetailsFragment(
                    null, it, null, null, null, null,
                    null, null, null, null, null, null, null)
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
                    null, null, null, null, null, null, null)
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
                    null, null, null, null, null, null, null)
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
                    null, it, null, null, null, null, null)
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
                    null, null, null, it, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private var trendingTvAdapter =
        TrendingTvListAdapter(arrayListOf(), trendingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvShowsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this,
            TvShowsViewModelFactory(MoviesApiService.getService(), ShowsRepository(MoviesApiService.getService())))
            .get(TvShowsViewModel::class.java)
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

            searchShows.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToSearchShowsFragment2()
                findNavController().navigate(args)
            }
        }

//        viewModel.apply {
//
//            getAiringToday().observe(viewLifecycleOwner, Observer {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                            resource.data.let { credits ->
//                                if (credits != null) {
//
//                                    airingTodayAdapter.updateAiringTodayList(credits.results as MutableList<TvShows.TvShowsResult>)
//                                }
//                            }
//                        }
//
//                        Status.LOADING -> {
//                        }
//
//                        Status.ERROR -> {}
//                    }
//                }
//            })
//
//            getOnAir().observe(viewLifecycleOwner, Observer {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                            resource.data.let { credits ->
//                                if (credits != null) {
//
//                                    onAirAdapter.updateOnAirList(credits.results as MutableList<TvShows.TvShowsResult>)
//                                }
//                            }
//                        }
//
//                        Status.LOADING -> {
//                        }
//
//                        Status.ERROR -> {}
//                    }
//                }
//            })
//
//            getPopular().observe(viewLifecycleOwner, Observer {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                            resource.data.let { credits ->
//                                if (credits != null) {
//
//                                    popularTvAdapter.updatePopularTvList(credits.results as MutableList<TvShows.TvShowsResult>)
//                                }
//                            }
//                        }
//
//                        Status.LOADING -> {
//                        }
//
//                        Status.ERROR -> {}
//                    }
//                }
//            })
//
//            getTopRated().observe(viewLifecycleOwner, Observer {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                            resource.data.let {credits ->
//                                if (credits != null) {
//
//                                    topRatedTvAdapter.updateTopRatedTvList(credits.results as MutableList<TvShows.TvShowsResult>)
//                                }
//                            }
//                        }
//
//                        Status.LOADING -> {
//                        }
//
//                        Status.ERROR -> {}
//                    }
//                }
//            })
//
//            getTrending().observe(viewLifecycleOwner, Observer {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//
//                            resource.data.let { credits ->
//                                if (credits != null) {
//
//                                    trendingTvAdapter.updateTrendingTvList(credits.results as MutableList<TvShows.TvShowsResult>)
//                                }
//                            }
//                        }
//
//                        Status.LOADING -> {
//                        }
//
//                        Status.ERROR -> {}
//                    }
//                }
//            })
//        }

        viewModel.showsResponse.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {

                Status.LOADING -> {
                    binding.apply {
                        showsScroll.visibility = View.GONE
                        loadingAnimHolder.visibility = View.VISIBLE
                    }
                }

                Status.SUCCESS -> {

                    val airingTodayList = mutableListOf<TvShows.TvShowsResult>()
                    val onAirList = mutableListOf<TvShows.TvShowsResult>()
                    val popularList = mutableListOf<TvShows.TvShowsResult>()
                    val topRatedList = mutableListOf<TvShows.TvShowsResult>()
                    val trendingList = mutableListOf<TvShows.TvShowsResult>()

                    resource.data.let { shows ->
                        shows?.forEachIndexed { index, it ->
                            when (it.showCategory) {

                                TvShows.TvShowsResult.TvShowCategory.AIRING_TODAY -> {
                                    shows[index].let {
                                        airingTodayList.add(it)
                                        airingTodayAdapter.updateAiringTodayList(airingTodayList)
                                    }
                                }

                                TvShows.TvShowsResult.TvShowCategory.ON_AIR -> {
                                    shows[index].let {
                                        onAirList.add(it)
                                        onAirAdapter.updateOnAirList(onAirList)
                                    }
                                }

                                TvShows.TvShowsResult.TvShowCategory.POPULAR -> {
                                    shows[index].let {
                                        popularList.add(it)
                                        popularTvAdapter.updatePopularTvList(popularList)
                                    }
                                }

                                TvShows.TvShowsResult.TvShowCategory.TOP_RATED -> {
                                    shows[index].let {
                                        topRatedList.add(it)
                                        topRatedTvAdapter.updateTopRatedTvList(topRatedList)
                                    }
                                }

                                TvShows.TvShowsResult.TvShowCategory.TRENDING -> {
                                    shows[index].let {
                                        trendingList.add(it)
                                        trendingTvAdapter.updateTrendingTvList(trendingList)
                                    }
                                }
                            }
                        }
                    }

                    binding.apply {
                        showsScroll.visibility = View.VISIBLE
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
            airingTodaySeeAll.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToPagedShowsFragment(
                    airingTodayTitle.toString(), null, null, null, null
                )
                findNavController().navigate(args)
            }
            onAirSeeAll.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToPagedShowsFragment(
                    null, onAirTitle.toString(), null, null, null
                )
                findNavController().navigate(args)
            }
            popularSeeAll.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToPagedShowsFragment(
                    null, null, popularTvTitle.toString(), null, null
                )
                findNavController().navigate(args)
            }
            topRatedSeeAll.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToPagedShowsFragment(
                    null, null, null, topRatedTvTitle.toString(), null
                )
                findNavController().navigate(args)
            }
            trendingSeeAll.setOnClickListener {
                val args = TvShowsFragmentDirections.actionTvShowsFragmentToPagedShowsFragment(
                    null, null, null, null, trendingTvTitle.toString()
                )
                findNavController().navigate(args)
            }
        }
    }

}