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
import com.czech.muvies.databinding.AiringTodayFragmentBinding
import com.czech.muvies.models.AiringTodayTvResult
import com.czech.muvies.pagedAdapters.AiringTodayMainAdapter
import com.czech.muvies.pagedAdapters.airingTodayItemClickListener
import com.czech.muvies.viewModels.AiringTodayViewModel

class AiringTodayFragment : Fragment() {

    private lateinit var viewModel: AiringTodayViewModel
    private lateinit var binding: AiringTodayFragmentBinding

    private val airingTodayClickListener by lazy {
        object : airingTodayItemClickListener {
            override fun invoke(it: AiringTodayTvResult) {
                val args = AiringTodayFragmentDirections.actionAiringTodayFragmentToTvShowsDetailsFragment(
                    it, null, null, null, null, null,
                    null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val airingTodayAdapter = AiringTodayMainAdapter(airingTodayClickListener)

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