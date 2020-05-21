package com.example.muvies.screens.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.Result
import com.example.muvies.model.UpcomingMovies
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

    private var _upcomingLiveData = MutableLiveData<MutableList<Result>>()

    val upcomingLiveData: LiveData<MutableList<Result>>
    get() = _upcomingLiveData

    init {
        getUpcomingList()
    }

    private fun getUpcomingList() {
        coroutineScope.launch {
            val upcoming = repository.getUpcomingMovies()
            try {
//                upcomingLiveData.postValue(upcoming)
                _upcomingLiveData.value =upcoming
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }
}
