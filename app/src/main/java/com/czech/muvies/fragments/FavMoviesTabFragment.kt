package com.czech.muvies.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.adapters.FavMoviesAdapter
import com.czech.muvies.databinding.FragmentFavMoviesTabBinding
import com.czech.muvies.room.movies.MoviesDatabase
import com.czech.muvies.room.movies.MoviesEntity
import com.czech.muvies.room.movies.MoviesRepository
import com.czech.muvies.viewModels.FavMoviesTabViewModel
import com.czech.muvies.viewModels.FavMoviesTabViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FavMoviesTabFragment : Fragment() {

    private lateinit var viewModel: FavMoviesTabViewModel
    private lateinit var binding: FragmentFavMoviesTabBinding

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MoviesDatabase.getDatabase(requireContext(), applicationScope) }
    private val repository by lazy { MoviesRepository(database.moviesDao()) }
    private val favMoviesAdapter = FavMoviesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this, FavMoviesTabViewModelFactory(repository))
            .get(FavMoviesTabViewModel::class.java)
        binding = FragmentFavMoviesTabBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.favMoviesTabViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intentData = Intent()

        intentData.getParcelableExtra<ToFavorites>(MovieDetailsFragment.EXTRA_REPLY).let { data ->

            val favMovies = MoviesEntity(
                data?.movieId,
                data?.movieTitle,
                data?.movieOverview,
                data?.movieBackdrop,
                data?.moviePoster,
                data?.movieDate,
                data?.movieVote,
                data?.movieLang
            )
            viewModel.insert(favMovies)
        }

        binding.favMoviesList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favMoviesAdapter
        }

        viewModel.allMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let { favMoviesAdapter.updateList(it) }
        })

    }

}