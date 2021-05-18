package com.czech.muvies.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czech.muvies.R
import com.czech.muvies.viewModels.SearchShowsViewModel

class SearchShowsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchShowsFragment()
    }

    private lateinit var viewModel: SearchShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_shows_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchShowsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}