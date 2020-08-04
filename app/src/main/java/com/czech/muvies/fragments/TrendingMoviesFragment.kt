package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.databinding.TrendingMoviesFragmentBinding
import com.czech.muvies.pagedAdapters.TrendingMoviesMainAdapter
import com.czech.muvies.viewModels.TrendingMoviesViewModel

class TrendingMoviesFragment : Fragment() {

    private lateinit var viewModel: TrendingMoviesViewModel
    private lateinit var binding: TrendingMoviesFragmentBinding

    private val trendingAdapter = TrendingMoviesMainAdapter()

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
        }

        viewModel.getTrendingMoviesList().observe(viewLifecycleOwner, Observer {
            trendingAdapter.submitList(it)
        })
    }

}