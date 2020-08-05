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

    private var _upcomingLiveData = MutableLiveData<MutableList<UpcomingResult>>()

    val upcomingLiveData: LiveData<MutableList<UpcomingResult>>
        get() = _upcomingLiveData

    init {
        getUpcomingList()
        getPopularList()
        getTopRatedList()
        getInTheatersList()
        getTrendingMovies()
    }

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

    private var _popularLiveData = MutableLiveData<MutableList<PopularResult>>()

    val popularLiveData: LiveData<MutableList<PopularResult>>
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

    private var _topRatedLiveData = MutableLiveData<MutableList<TopRatedResult>>()

    val topRatedLiveData: LiveData<MutableList<TopRatedResult>>
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

    private var _inTheatersLiveData = MutableLiveData<MutableList<InTheatersResult>>()

    val inTheatersLiveData: LiveData<MutableList<InTheatersResult>>
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

    private var _trendingMoviesLiveData = MutableLiveData<MutableList<TrendingMoviesResult>>()

    val trendingMoviesLiveData: LiveData<MutableList<TrendingMoviesResult>>
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