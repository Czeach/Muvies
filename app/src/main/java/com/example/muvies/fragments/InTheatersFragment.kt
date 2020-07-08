package com.example.muvies.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.MainActivity
import com.example.muvies.adapters.InTheatersMiniListAdapter
import com.example.muvies.databinding.InTheatersFragmentBinding
import com.example.muvies.viewModels.InTheatersViewModel

class InTheatersFragment : Fragment() {

    private lateinit var viewModel: InTheatersViewModel

    private var thisAdapter =
        InTheatersMiniListAdapter(arrayListOf())

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
    }

}