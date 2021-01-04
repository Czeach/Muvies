package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.CastMoviesTabAdapter
import com.czech.muvies.adapters.CastTvShowsTabAdapter
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.TvShowDetailsViewModelFactory
import com.czech.muvies.viewModels.TvShowsTabViewModel
import com.czech.muvies.viewModels.TvShowsTabViewModelFactory
import kotlinx.android.synthetic.main.movies_tab_fragment.*
import kotlinx.android.synthetic.main.tv_shows_tab_fragment.*

class TvShowsTabFragment : Fragment() {

    private lateinit var viewModel: TvShowsTabViewModel

    private var tvShowsTabAdapter = CastTvShowsTabAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, TvShowsTabViewModelFactory(MoviesApiService.getService()))
            .get(TvShowsTabViewModel::class.java)

        return inflater.inflate(R.layout.tv_shows_tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personTvShows = arguments?.getInt("showPerson")

        person_tv_shows_tab.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = tvShowsTabAdapter
        }

//        if (personTvShows != null) {
//            viewModel.getPersonTvShows(personTvShows).observe(viewLifecycleOwner, Observer {
//                it.let { resource ->
//                    when(resource.status) {
//                        Status.SUCCESS -> {
//                            resource.data.let {personTvShows ->
//                                if (personTvShows != null) {
//                                    tvShowsTabAdapter.updateList(personTvShows.cast as List<PersonTvShows.Cast>)
//                                }
//                            }
//                        }
//                        Status.LOADING -> {
//
//                        }
//                        Status.ERROR -> {
//                            Toast.makeText(requireContext(), "No Tv Shows", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            })
//        } else {
//            Toast.makeText(requireContext(), "No Tv Shows", Toast.LENGTH_LONG).show()
//        }
    }
}