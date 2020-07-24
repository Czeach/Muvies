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
import com.example.muvies.databinding.TopRatedShowsFragmentBinding
import com.example.muvies.pagedAdapters.TopRatedShowsMainAdapter
import com.example.muvies.viewModels.TopRatedShowsViewModel

class TopRatedShowsFragment : Fragment() {

    private lateinit var viewModel: TopRatedShowsViewModel
    private lateinit var binding: TopRatedShowsFragmentBinding

    private val topRatedAdapter = TopRatedShowsMainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TopRatedShowsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TopRatedShowsViewModel::class.java)
        binding.topRatedShowsVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topRatedShowsMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = topRatedAdapter
        }

        viewModel.getTopRatedShowsList().observe(viewLifecycleOwner, Observer {
            topRatedAdapter.submitList(it)
        })
    }

}