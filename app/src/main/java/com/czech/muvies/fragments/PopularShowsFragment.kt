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
import com.czech.muvies.databinding.PopularShowsFragmentBinding
import com.czech.muvies.models.TvShowsResult
import com.czech.muvies.pagedAdapters.PopularShowsMainAdapter
import com.czech.muvies.pagedAdapters.popularTvItemClickListener
import com.czech.muvies.viewModels.PopularShowsViewModel

class PopularShowsFragment : Fragment() {

    private lateinit var viewModel: PopularShowsViewModel
    private lateinit var binding: PopularShowsFragmentBinding

    private val popularClickListener by lazy {
        object : popularTvItemClickListener {
            override fun invoke(it: TvShowsResult) {
                val args = PopularShowsFragmentDirections.actionPopularShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, it, null,
                    null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val popularShowsAdapter = PopularShowsMainAdapter(popularClickListener)

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