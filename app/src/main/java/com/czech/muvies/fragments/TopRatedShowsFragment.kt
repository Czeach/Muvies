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
import com.czech.muvies.databinding.TopRatedShowsFragmentBinding
import com.czech.muvies.models.TvShows
import com.czech.muvies.pagedAdapters.TopRatedShowsMainAdapter
import com.czech.muvies.pagedAdapters.topRatedTvItemClickListener
import com.czech.muvies.viewModels.TopRatedShowsViewModel
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class TopRatedShowsFragment : Fragment() {

    private lateinit var viewModel: TopRatedShowsViewModel
    private lateinit var binding: TopRatedShowsFragmentBinding

    private val topRatedClickListener by lazy {
        object : topRatedTvItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = TopRatedShowsFragmentDirections.actionTopRatedShowsFragmentToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    it, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val topRatedAdapter = TopRatedShowsMainAdapter(topRatedClickListener)

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

            loadSkeleton(R.layout.paged_list) {

                color(R.color.colorSkeleton)
                shimmer(true)
            }
        }

        viewModel.getTopRatedShowsList().observe(viewLifecycleOwner, Observer {

            Handler().postDelayed({

                binding.topRatedShowsMainList.hideSkeleton()

            }, 2000)

            topRatedAdapter.submitList(it)
        })
    }

}