package com.example.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.MainActivity
import com.example.muvies.adapters.*

import com.example.muvies.databinding.FeaturedFragmentBinding
import com.example.muvies.viewModels.FeaturedViewModel

class FeaturedFragment : Fragment() {

    private lateinit var viewModel: FeaturedViewModel

    private var discoverAdapter =
        DiscoverListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        (requireActivity() as MainActivity).title = "Muvies"

        val binding = FeaturedFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FeaturedViewModel::class.java)
        binding.lifecycleOwner = this
        binding.featuredViewModel = viewModel

        binding.apply {
            discoverListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = discoverAdapter
            }
        }

        viewModel.apply {
            discoverLiveData.observe(viewLifecycleOwner, Observer {
                discoverAdapter.updateDiscoverList(it)
            })
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).title = "Muvies"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
