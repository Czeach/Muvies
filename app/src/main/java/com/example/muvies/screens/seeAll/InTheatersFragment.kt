package com.example.muvies.screens.seeAll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.R
import com.example.muvies.adapters.InTheatersListAdapter
import com.example.muvies.databinding.InTheatersFragmentBinding

class InTheatersFragment : Fragment() {

    private lateinit var viewModel: InTheatersViewModel

    private var thisAdapter =
        InTheatersListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding = InTheatersFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(InTheatersViewModel::class.java)
        binding.lifecycleOwner = this
        binding.inTheatersVieModel = viewModel

        binding.apply {
            inTheatersMainListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = thisAdapter
            }
        }

        viewModel.apply {
            inTheatersLiveData.observe(viewLifecycleOwner, Observer {
                thisAdapter.updateInTheatreList(it)
            })
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}