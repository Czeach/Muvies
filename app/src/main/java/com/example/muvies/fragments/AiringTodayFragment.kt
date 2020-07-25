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
import com.example.muvies.databinding.AiringTodayFragmentBinding
import com.example.muvies.pagedAdapters.AiringTodayMainAdapter
import com.example.muvies.viewModels.AiringTodayViewModel

class AiringTodayFragment : Fragment() {

    private lateinit var viewModel: AiringTodayViewModel
    private lateinit var binding: AiringTodayFragmentBinding

    private val airingTodayAdapter = AiringTodayMainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AiringTodayFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(AiringTodayViewModel::class.java)
        binding.airingTodayVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.airingTodayMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = airingTodayAdapter
        }

        viewModel.getAiringTodayList().observe(viewLifecycleOwner, Observer {
            airingTodayAdapter.submitList(it)
        })
    }

}