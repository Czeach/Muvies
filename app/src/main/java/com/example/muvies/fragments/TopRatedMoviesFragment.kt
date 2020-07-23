package com.example.muvies.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.muvies.R
import com.example.muvies.viewModels.TopRatedMoviesViewModel

class TopRatedMoviesFragment : Fragment() {

    companion object {
        fun newInstance() = TopRatedMoviesFragment()
    }

    private lateinit var viewModel: TopRatedMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(TopRatedMoviesViewModel::class.java)

        return inflater.inflate(R.layout.top_rated_movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}