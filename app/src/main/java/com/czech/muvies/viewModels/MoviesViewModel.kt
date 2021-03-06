package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApi
import com.czech.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: MoviesRepository = MoviesRepository(MoviesApi.retrofitService)

    init {
        getUpcomingList()
        getPopularList()
        getTopRatedList()
        getInTheatersList()
        getTrendingMovies()
    }

    private var _upcomingLiveData = MutableLiveData<MutableList<Movies.MoviesResult>>()

    val upcomingLiveData: LiveData<MutableList<Movies.MoviesResult>>
        get() = _upcomingLiveData

    private fun getUpcomingList() {
        coroutineScope.launch {
            val upcoming = repository.getUpcomingMovies()
            try {
                _upcomingLiveData.value = upcoming
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _popularLiveData = MutableLiveData<MutableList<Movies.MoviesResult>>()

    val popularLiveData: LiveData<MutableList<Movies.MoviesResult>>
        get() = _popularLiveData

    private fun getPopularList() {
        coroutineScope.launch {
            val popular = repository.getPopularMovies()
            try {
                _popularLiveData.value = popular
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _topRatedLiveData = MutableLiveData<MutableList<Movies.MoviesResult>>()

    val topRatedLiveData: LiveData<MutableList<Movies.MoviesResult>>
        get() = _topRatedLiveData

    private fun getTopRatedList() {
        coroutineScope.launch {
            val topRated = repository.getTopRatedMovies()
            try {
                _topRatedLiveData.value = topRated
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _inTheatersLiveData = MutableLiveData<MutableList<Movies.MoviesResult>>()

    val inTheatersLiveData: LiveData<MutableList<Movies.MoviesResult>>
        get() = _inTheatersLiveData

    private fun getInTheatersList() {
        coroutineScope.launch {
            val inTheaters = repository.getInTheatersMovies()
            try {
                _inTheatersLiveData.value = inTheaters
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _trendingMoviesLiveData = MutableLiveData<MutableList<Movies.MoviesResult>>()

    val trendingMoviesLiveData: LiveData<MutableList<Movies.MoviesResult>>
        get() = _trendingMoviesLiveData

    private fun getTrendingMovies() {
        coroutineScope.launch {
            val trending = repository.getTrendingMovies()
            try {
                _trendingMoviesLiveData.value = trending
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }
}