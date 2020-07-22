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
import com.example.muvies.pagedAdapters.InTheatersMainListAdapter
import com.example.muvies.viewModels.InTheatersViewModel

class InTheatersFragment : Fragment() {

    private lateinit var viewModel: InTheatersViewModel
    private lateinit var binding: InTheatersFragmentBinding

    private val inTheatersAdapter = InTheatersMainListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = InTheatersFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(InTheatersViewModel::class.java)
        binding.inTheatersVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            inTheatersMainListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                adapter = inTheatersAdapter
            }
        }

        viewModel.getInTheatersList().observe(viewLifecycleOwner, Observer {
            inTheatersAdapter.submitList(it)
        })
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