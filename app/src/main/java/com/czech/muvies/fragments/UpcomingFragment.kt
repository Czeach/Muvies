package com.czech.muvies.fragments

import android.content.Context
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
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.databinding.InTheatersFragmentBinding
import com.czech.muvies.databinding.UpcomingFragmentBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.pagedAdapters.UpcomingMainAdapter
import com.czech.muvies.pagedAdapters.upcomingItemClickListener
import com.czech.muvies.viewModels.UpcomingViewModel
//import koleton.api.hideSkeleton
//import koleton.api.loadSkeleton

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel
    private lateinit var binding: UpcomingFragmentBinding

    private val upcomingClickListener by lazy {
        object : upcomingItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = UpcomingFragmentDirections.actionUpcomingToDetailsFragment(
                    null, null, it, null, null, null, null,
                    null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val upcomingAdapter = UpcomingMainAdapter(upcomingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UpcomingFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)
        binding.upcomingVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            upcomingMainList.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = upcomingAdapter

//                loadSkeleton(R.layout.paged_list) {
//
//                    color(R.color.colorSkeleton)
//                    shimmer(true)
//                }
            }

            viewModel.getUpcomingList().observe(viewLifecycleOwner, Observer {

//                Handler().postDelayed({
//
//                    binding.upcomingMainList.hideSkeleton()
//
//                }, 2000)

                upcomingAdapter.submitList(it)
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
    }

}