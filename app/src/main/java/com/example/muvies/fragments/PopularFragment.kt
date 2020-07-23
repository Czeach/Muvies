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
import com.example.muvies.databinding.PopularFragmentBinding
import com.example.muvies.pagedAdapters.PopularMainAdapter
import com.example.muvies.viewModels.PopularViewModel

class PopularFragment : Fragment() {


    private lateinit var viewModel: PopularViewModel
    private lateinit var binding: PopularFragmentBinding

    private val popularAdapter = PopularMainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PopularFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        binding.popularVieModel = viewModel

        binding.popularMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = popularAdapter
        }

        viewModel.getPopularList().observe(viewLifecycleOwner, Observer {
            popularAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}