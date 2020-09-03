package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.R
import com.czech.muvies.viewModels.TvShowsTabViewModel

class TvShowsTabFragment : Fragment() {

    private lateinit var viewModel: TvShowsTabViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TvShowsTabViewModel::class.java)

        return inflater.inflate(R.layout.tv_shows_tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}