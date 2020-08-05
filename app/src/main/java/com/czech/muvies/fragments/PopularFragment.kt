package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.databinding.PopularFragmentBinding
import com.czech.muvies.pagedAdapters.PopularMainAdapter
import com.czech.muvies.viewModels.PopularViewModel

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popularMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = popularAdapter
        }

        viewModel.getPopularList().observe(viewLifecycleOwner, Observer {
            popularAdapter.submitList(it)
        })
    }

}