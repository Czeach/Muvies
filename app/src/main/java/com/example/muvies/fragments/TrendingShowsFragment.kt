package com.example.muvies.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.R
import com.example.muvies.databinding.TrendingShowsFragmentBinding
import com.example.muvies.pagedAdapters.TrendingShowsMainAdapter
import com.example.muvies.viewModels.TrendingShowsViewModel

class TrendingShowsFragment : Fragment() {

    private lateinit var viewModel: TrendingShowsViewModel
    private lateinit var binding: TrendingShowsFragmentBinding

    private val trendingAdapter = TrendingShowsMainAdapter()

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
        }

        viewModel.getTrendingShowsList().observe(viewLifecycleOwner, Observer {
            trendingAdapter.submitList(it)
        })
    }

}