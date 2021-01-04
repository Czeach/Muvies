package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.CastMoviesTabAdapter
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.MoviesTabViewModel
import com.czech.muvies.viewModels.MoviesTabViewModelFactory
import kotlinx.android.synthetic.main.movies_tab_fragment.*

class MoviesTabFragment : Fragment() {

    private lateinit var viewModel: MoviesTabViewModel

    private var moviesTabAdapter = CastMoviesTabAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, MoviesTabViewModelFactory(MoviesApiService.getService()))
            .get(MoviesTabViewModel::class.java)
        return inflater.inflate(R.layout.movies_tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviePerson = arguments?.getInt("moviePerson")

        person_movies_tab.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = moviesTabAdapter
        }

//        if (moviePerson != null) {
//            viewModel.getPersonMovies(moviePerson).observe(viewLifecycleOwner, Observer {
//                it.let {resource ->
//                   when(resource.status) {
//                       Status.SUCCESS -> {
//                           resource.data.let {personMovies ->
//                               if (personMovies != null) {
//                                   moviesTabAdapter.updateList(personMovies.cast as List<PersonMovies.Cast>)
//                               }
//                           }
//                       }
//                       Status.LOADING -> {
//
//                       }
//                       Status.ERROR -> {
//
//                       }
//                   }
//                }
//            })
//        }
    }
}