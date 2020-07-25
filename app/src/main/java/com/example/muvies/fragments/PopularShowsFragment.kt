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
import com.example.muvies.databinding.PopularShowsFragmentBinding
import com.example.muvies.pagedAdapters.PopularShowsMainAdapter
import com.example.muvies.viewModels.PopularShowsViewModel

class PopularShowsFragment : Fragment() {

    private lateinit var viewModel: PopularShowsViewModel
    private lateinit var binding: PopularShowsFragmentBinding

    private val popularShowsAdapter = PopularShowsMainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PopularShowsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(PopularShowsViewModel::class.java)
        binding.popularShowsVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popularShowsMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = popularShowsAdapter
        }

        viewModel.getPopularShowsList().observe(viewLifecycleOwner, Observer {
            popularShowsAdapter.submitList(it)
        })
    }

}