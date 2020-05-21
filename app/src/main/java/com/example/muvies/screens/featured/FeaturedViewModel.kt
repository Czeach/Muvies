package com.example.muvies.screens.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.PopularResult
import com.example.muvies.model.TopRatedResult
import com.example.muvies.model.UpcomingResult
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class FeaturedViewModel : ViewModel() {

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

    val topReatedLiveData: LiveData<MutableList<TopRatedResult>>
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
}
