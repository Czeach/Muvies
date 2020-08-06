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
import com.czech.muvies.databinding.InTheatersFragmentBinding
import com.czech.muvies.databinding.UpcomingFragmentBinding
import com.czech.muvies.models.UpcomingResult
import com.czech.muvies.pagedAdapters.UpcomingMainAdapter
import com.czech.muvies.pagedAdapters.upcomingItemClickListener
import com.czech.muvies.viewModels.UpcomingViewModel

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel
    private lateinit var binding: UpcomingFragmentBinding

    private val upcomingClickListener by lazy {
        object : upcomingItemClickListener {
            override fun invoke(it: UpcomingResult) {
                val args = UpcomingFragmentDirections.actionUpcomingToDetailsFragment(
                    null, null, it, null
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
            }

            viewModel.getUpcomingList().observe(viewLifecycleOwner, Observer {
                upcomingAdapter.submitList(it)
            })
        }
    }

}