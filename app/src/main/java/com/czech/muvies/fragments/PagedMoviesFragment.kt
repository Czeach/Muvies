package com.czech.muvies.fragments

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.adapters.PagedMoviesAdapter
import com.czech.muvies.adapters.pagedMovieClickListener
import com.czech.muvies.databinding.PagedMoviesFragmentBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.viewModels.PagedMoviesViewModel

class PagedMoviesFragment : Fragment() {

    private lateinit var binding: PagedMoviesFragmentBinding
    private lateinit var viewModel: PagedMoviesViewModel

    private val inTheatersClickListener by lazy {
        object : pagedMovieClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = PagedMoviesFragmentDirections.actionPagedMoviesFragmentToDetailsFragment(
                    it, null, null, null, null, null, null,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val inTheatersAdapter = PagedMoviesAdapter(inTheatersClickListener)

    private val upcomingClickListener by lazy {
        object : pagedMovieClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = PagedMoviesFragmentDirections.actionPagedMoviesFragmentToDetailsFragment(
                    null, null, it, null, null, null, null,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val upcomingAdapter = PagedMoviesAdapter(upcomingClickListener)

    private val popularClickListener by lazy {
        object : pagedMovieClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = PagedMoviesFragmentDirections.actionPagedMoviesFragmentToDetailsFragment(
                    null, null, null, null, it, null, null,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val popularAdapter = PagedMoviesAdapter(popularClickListener)

    private val topRatedClickListener by lazy {
        object : pagedMovieClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = PagedMoviesFragmentDirections.actionPagedMoviesFragmentToDetailsFragment(
                    it, null, null, null, null, null, it,
                    null, null, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val topRatedAdapter = PagedMoviesAdapter(topRatedClickListener)

    private val trendingClickListener by lazy {
        object : pagedMovieClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = PagedMoviesFragmentDirections.actionPagedMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null, null,
                    null, it, null, null, null, null)
                findNavController().navigate(args)
            }

        }
    }
    private val trendingAdapter = PagedMoviesAdapter(trendingClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = PagedMoviesFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(PagedMoviesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.pagedMoviesViewModel = viewModel

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inTheaterArgs = PagedMoviesFragmentArgs.fromBundle(requireArguments()).inTheaterArgs
        val upcomingArgs = PagedMoviesFragmentArgs.fromBundle(requireArguments()).upcomingArgs
        val popularArgs = PagedMoviesFragmentArgs.fromBundle(requireArguments()).popularArgs
        val topRatedArgs = PagedMoviesFragmentArgs.fromBundle(requireArguments()).topRatedArgs
        val trendingArgs = PagedMoviesFragmentArgs.fromBundle(requireArguments()).trendingArgs

        if (inTheaterArgs != null) {

            binding.apply {

                pagedTitle.text = "Movies In Theaters"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = inTheatersAdapter
                }

                viewModel.getInTheatersList().observe(viewLifecycleOwner, Observer {
                    inTheatersAdapter.submitList(it)
                })
            }
        }

        if (upcomingArgs != null) {

            binding.apply {

                pagedTitle.text = "Upcoming Movies"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = upcomingAdapter
                }

                viewModel.getUpcomingList().observe(viewLifecycleOwner, Observer {
                    upcomingAdapter.submitList(it)
                })
            }
        }

        if (popularArgs != null) {

            binding.apply {

                pagedTitle.text = "Popular Movies"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = popularAdapter
                }

                viewModel.getPopularList().observe(viewLifecycleOwner, Observer {
                    popularAdapter.submitList(it)
                })
            }
        }

        if (topRatedArgs != null) {
            binding.apply {

                pagedTitle.text = "Top Rated Movies"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = topRatedAdapter
                }

                viewModel.getTopRatedList().observe(viewLifecycleOwner, Observer {
                    topRatedAdapter.submitList(it)
                })
            }

        }

        if (trendingArgs != null) {
            binding.apply {

                pagedTitle.text = "Trending Movies"

                pagedList.apply {
                    layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    adapter = trendingAdapter
                }

                viewModel.getTrendingMoviesList().observe(viewLifecycleOwner, Observer {
                    trendingAdapter.submitList(it)
                })
            }

        }
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