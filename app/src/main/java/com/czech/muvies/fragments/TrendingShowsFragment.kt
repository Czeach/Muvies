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
import com.czech.muvies.databinding.TrendingShowsFragmentBinding
import com.czech.muvies.models.TvShows
import com.czech.muvies.pagedAdapters.TrendingShowsMainAdapter
import com.czech.muvies.pagedAdapters.trendingTvItemClickListener
import com.czech.muvies.viewModels.TrendingShowsViewModel
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class TrendingShowsFragment : Fragment() {

    private lateinit var viewModel: TrendingShowsViewModel
    private lateinit var binding: TrendingShowsFragmentBinding

    private val trendingClickListener by lazy {
        object : trendingTvItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TrendingShowsFragmentDirections.actionTrendingShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, it, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val trendingAdapter = TrendingShowsMainAdapter(trendingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TrendingShowsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TrendingShowsViewModel::class.java)
        binding.trendingShowsVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trendingShowsMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = trendingAdapter

            loadSkeleton(R.layout.paged_list) {

                color(R.color.colorSkeleton)
                shimmer(true)
            }
        }

        viewModel.getTrendingShowsList().observe(viewLifecycleOwner, Observer {

            Handler().postDelayed({

                binding.trendingShowsMainList.hideSkeleton()

            }, 2000)

            trendingAdapter.submitList(it)
        })
    }

}