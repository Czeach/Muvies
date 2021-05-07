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
import com.czech.muvies.databinding.TrendingMoviesFragmentBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.pagedAdapters.TrendingMoviesMainAdapter
import com.czech.muvies.pagedAdapters.trendingItemClickListener
import com.czech.muvies.viewModels.TrendingMoviesViewModel
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class TrendingMoviesFragment : Fragment() {

    private lateinit var viewModel: TrendingMoviesViewModel
    private lateinit var binding: TrendingMoviesFragmentBinding

    private val trendingClickListener by lazy {
        object : trendingItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = TrendingMoviesFragmentDirections.actionTrendingMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, it, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val trendingAdapter = TrendingMoviesMainAdapter(trendingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TrendingMoviesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TrendingMoviesViewModel::class.java)
        binding.trendingMoviesVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trendingMoviesMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = trendingAdapter

            loadSkeleton(R.layout.paged_list) {

                color(R.color.colorSkeleton)
                shimmer(true)
            }
        }

        viewModel.getTrendingMoviesList().observe(viewLifecycleOwner, Observer {

            Handler().postDelayed({

                binding.trendingMoviesMainList.hideSkeleton()

            }, 2000)

            trendingAdapter.submitList(it)
        })
    }

}