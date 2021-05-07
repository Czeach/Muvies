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
import com.czech.muvies.databinding.OnAirFragmentBinding
import com.czech.muvies.models.TvShows
import com.czech.muvies.pagedAdapters.OnAirMainAdapter
import com.czech.muvies.pagedAdapters.onAirItemClickListener
import com.czech.muvies.viewModels.OnAirViewModel
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class OnAirFragment : Fragment() {

    private lateinit var viewModel: OnAirViewModel
    private lateinit var binding: OnAirFragmentBinding

    private val onAirClickListener by lazy {
        object : onAirItemClickListener {
            override fun invoke(it: TvShows.TvShowsResult) {
                val args = OnAirFragmentDirections.actionOnAirFragmentToTvShowsDetailsFragment(
                    null, null, it, null, null, null,
                    null, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val onAirAdapter = OnAirMainAdapter(onAirClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = OnAirFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(OnAirViewModel::class.java)
        binding.onAirVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onAirMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = onAirAdapter

            loadSkeleton(R.layout.paged_list) {

                color(R.color.colorSkeleton)
                shimmer(true)
            }
        }

        viewModel.getOnAirList().observe(viewLifecycleOwner, Observer {

            Handler().postDelayed({

                binding.onAirMainList.hideSkeleton()

            }, 2000)

            onAirAdapter.submitList(it)
        })
    }

}