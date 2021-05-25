package com.czech.muvies.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.PagedShowsAdapter
import com.czech.muvies.adapters.pagedShowClickListener
import com.czech.muvies.databinding.PagedShowsFragmentBinding
import com.czech.muvies.models.TvShows
import com.czech.muvies.pagedAdapters.AiringTodayMainAdapter
import com.czech.muvies.viewModels.PagedShowsViewModel

class PagedShowsFragment : Fragment() {

    private lateinit var binding: PagedShowsFragmentBinding
    private lateinit var viewModel: PagedShowsViewModel

    private val airingTodayClickListener by lazy {
        object : pagedShowClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = PagedShowsFragmentDirections.actionPagedShowsFragmentToTvShowsDetailsFragment(
                    it, null, null, null, null, null, null,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val airingTodayAdapter = PagedShowsAdapter(airingTodayClickListener)

    private val onAirClickListener by lazy {
        object : pagedShowClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = PagedShowsFragmentDirections.actionPagedShowsFragmentToTvShowsDetailsFragment(
                    null, null, it, null, null, null, null,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val onAirAdapter = PagedShowsAdapter(onAirClickListener)

    private val popularClickListener by lazy {
        object : pagedShowClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = PagedShowsFragmentDirections.actionPagedShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, it, null,
                    null, null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val popularAdapter = PagedShowsAdapter(popularClickListener)

    private val topRatedClickListener by lazy {
        object : pagedShowClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = PagedShowsFragmentDirections.actionPagedShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    it, null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val topRatedAdapter = PagedShowsAdapter(topRatedClickListener)

    private val trendingClickListener by lazy {
        object : pagedShowClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = PagedShowsFragmentDirections.actionPagedShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, it, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val trendingAdapter = PagedShowsAdapter(trendingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = PagedShowsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(PagedShowsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.pagedShowsViewModel = viewModel

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val airingTodayArgs = PagedShowsFragmentArgs.fromBundle(requireArguments()).airingTodayArgs
        val onAirArgs = PagedShowsFragmentArgs.fromBundle(requireArguments()).onAirArgs
        val popularArgs = PagedShowsFragmentArgs.fromBundle(requireArguments()).popularArgs
        val topRatedArgs = PagedShowsFragmentArgs.fromBundle(requireArguments()).topRatedArgs
        val trendingArgs = PagedShowsFragmentArgs.fromBundle(requireArguments()).trendingArgs

        if (airingTodayArgs != null) {

            binding.apply {

                pagedTitle.text = "Shows Airing Today"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = airingTodayAdapter
                }
            }

            viewModel.getAiringTodayList().observe(viewLifecycleOwner, Observer {
                airingTodayAdapter.submitList(it)
            })
        }

        if (onAirArgs != null) {

            binding.apply {

                pagedTitle.text = "Shows On Air"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = onAirAdapter
                }
            }

            viewModel.getOnAirList().observe(viewLifecycleOwner, Observer {
                onAirAdapter.submitList(it)
            })

        }

        if (popularArgs != null) {

            binding.apply {

                pagedTitle.text = "Popular Shows"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = popularAdapter
                }
            }

            viewModel.getPopularShowsList().observe(viewLifecycleOwner, Observer {
                popularAdapter.submitList(it)
            })

        }

        if (topRatedArgs != null) {

            binding.apply {

                pagedTitle.text = "Top Rated Shows"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = topRatedAdapter
                }
            }

            viewModel.getTopRatedShowsList().observe(viewLifecycleOwner, Observer {
                topRatedAdapter.submitList(it)
            })

        }

        if (trendingArgs != null) {

            binding.apply {

                pagedTitle.text = "Trending Shows"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = trendingAdapter
                }
            }

            viewModel.getTrendingShowsList().observe(viewLifecycleOwner, Observer {
                trendingAdapter.submitList(it)
            })

        }
    }

}