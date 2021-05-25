package com.czech.muvies.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.SearchShowsAdapter
import com.czech.muvies.adapters.searchShowsClickListener
import com.czech.muvies.databinding.SearchShowsFragmentBinding
import com.czech.muvies.models.SearchShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.SearchMoviesViewModel
import com.czech.muvies.viewModels.SearchShowsViewModel
import com.czech.muvies.viewModels.SearchShowsViewModelFactory
import com.czech.muvies.viewModels.SearchViewModelFactory
import kotlinx.android.synthetic.main.search_shows_fragment.*
import kotlinx.coroutines.*

class SearchShowsFragment : Fragment() {

    private lateinit var binding: SearchShowsFragmentBinding
    private lateinit var viewModel: SearchShowsViewModel

    private val searchClickListener by lazy {
        object : searchShowsClickListener {
            override fun invoke(it: SearchShows.Result) {
                val args = SearchShowsFragmentDirections.actionSearchShowsFragment2ToTvShowsDetailsFragment(
                    null, null, null, null, null, null,
                    null, null, null, null, null, null,
                    it)
                findNavController().navigate(args)
            }

        }
    }
    private var searchShowsAdapter = SearchShowsAdapter(arrayListOf(), searchClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchShowsFragmentBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, SearchShowsViewModelFactory(MoviesApiService.getService()))
            .get(SearchShowsViewModel::class.java)

        binding.lifecycleOwner = this
        binding.searchShowsViewModel = viewModel

        binding.showSearchResult.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = searchShowsAdapter
        }

//        searchShowsAdapter.updateSearchList(emptyList())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_shows_et.setOnQueryTextListener(
            object  : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {

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

                                searchShowsAdapter.updateSearchList(emptyList())

                            } else {
                                viewModel.getSearch(query).observe(viewLifecycleOwner, Observer {
                                    it?.let { resource ->
                                        when (resource.status) {

                                            Status.SUCCESS -> {
                                                resource.data.let { credits ->
                                                    if (credits != null) {
                                                        searchShowsAdapter.updateSearchList(credits.results as List<SearchShows.Result>)
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