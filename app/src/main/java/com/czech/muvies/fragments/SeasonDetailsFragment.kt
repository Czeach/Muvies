package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.R
import com.czech.muvies.viewModels.SeasonDetailsViewModel

class SeasonDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SeasonDetailsFragment()
    }

    private lateinit var viewModel: SeasonDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(SeasonDetailsViewModel::class.java)

        return inflater.inflate(R.layout.season_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}