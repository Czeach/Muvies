package com.czech.muvies.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.databinding.InTheatersFragmentBinding
import com.czech.muvies.models.InTheatersResult
import com.czech.muvies.pagedAdapters.InTheatersMainListAdapter
import com.czech.muvies.pagedAdapters.inTheatersItemClickListener
import com.czech.muvies.viewModels.InTheatersViewModel

class InTheatersFragment : Fragment() {

    private lateinit var viewModel: InTheatersViewModel
    private lateinit var binding: InTheatersFragmentBinding

    private val inTheatersClickListener by lazy {
        object : inTheatersItemClickListener {
            override fun invoke(it: InTheatersResult) {
                val args = InTheatersFragmentDirections.actionInTheatersFragmentToDetailsFragment(
                    it, null, null, null, null, null, null,
                    null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val inTheatersAdapter = InTheatersMainListAdapter(inTheatersClickListener)

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

}