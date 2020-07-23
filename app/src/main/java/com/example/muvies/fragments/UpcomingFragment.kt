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
import com.example.muvies.databinding.InTheatersFragmentBinding
import com.example.muvies.databinding.UpcomingFragmentBinding
import com.example.muvies.pagedAdapters.UpcomingMainAdapter
import com.example.muvies.viewModels.UpcomingViewModel

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel
    private lateinit var binding: UpcomingFragmentBinding

    private val upcomingAdapter = UpcomingMainAdapter()

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