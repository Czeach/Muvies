package com.czech.muvies.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.adapters.SearchMoviesAdapter
import com.czech.muvies.adapters.searchMoviesClickListener
import com.czech.muvies.databinding.SearchMoviesFragmentBinding
import com.czech.muvies.models.SearchMovies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.*
import kotlinx.android.synthetic.main.search_movies_fragment.*
import kotlinx.coroutines.*

class SearchMoviesFragment : Fragment() {

    private lateinit var binding: SearchMoviesFragmentBinding
    private lateinit var viewModel: SearchMoviesViewModel

    private val searchClickListener by lazy {
        object : searchMoviesClickListener {
            override fun invoke(it: SearchMovies.Result) {
                val args = SearchMoviesFragmentDirections.actionSearchMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, null, null, null, null, it
                )
                findNavController().navigate(args)
            }

        }
    }
    private var searchAdapter = SearchMoviesAdapter(arrayListOf(), searchClickListener)

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = SearchMoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, SearchViewModelFactory(MoviesApiService.getService()))
            .get(SearchMoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.searchMoviesViewModel = viewModel

        binding.movieSearchResult.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }

//        searchAdapter.updateSearchList(emptyList())

        return binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_movies_et.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {

                private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

                private var searchJob: Job? = null

                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false

                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        newText?.let { query ->
                            delay(300)
                            if (query.isEmpty()) {

                                searchAdapter.updateSearchList(emptyList())

                            } else {
                                viewModel.getSearch(query).observe(viewLifecycleOwner, Observer {
                                    it?.let { resource ->
                                        when (resource.status) {

                                            Status.SUCCESS -> {
                                                resource.data.let { credits ->
                                                    if (credits != null) {
                                                        searchAdapter.updateSearchList(credits.results as List<SearchMovies.Result>)
                                                    }
                                                }
                                            }

                                            Status.LOADING -> {

                                            }

                                            Status.ERROR -> {

                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }

                    return false

                }
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
    }
}