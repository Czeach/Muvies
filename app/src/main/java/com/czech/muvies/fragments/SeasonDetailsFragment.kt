package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.R
import com.czech.muvies.databinding.SeasonDetailsFragmentBinding
import com.czech.muvies.viewModels.SeasonDetailsViewModel

class SeasonDetailsFragment : Fragment() {

    private lateinit var viewModel: SeasonDetailsViewModel
    private lateinit var binding: SeasonDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SeasonDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(SeasonDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.seasonDetailsViewModel = viewModel

        return inflater.inflate(R.layout.season_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}