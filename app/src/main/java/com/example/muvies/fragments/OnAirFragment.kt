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
import com.example.muvies.databinding.OnAirFragmentBinding
import com.example.muvies.pagedAdapters.OnAirMainAdapter
import com.example.muvies.viewModels.OnAirViewModel

class OnAirFragment : Fragment() {

    private lateinit var viewModel: OnAirViewModel
    private lateinit var binding: OnAirFragmentBinding

    private val onAirAdapter = OnAirMainAdapter()

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
        }

        viewModel.getOnAirList().observe(viewLifecycleOwner, Observer {
            onAirAdapter.submitList(it)
        })
    }

}