package com.example.muvies.screens.tvShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.AiringTodayTvResult
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TvShowsViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: MoviesRepository = MoviesRepository(MoviesApi.retrofitService)

    init {
        getAiringTodayList()
    }

    private var _airingTodayLiveData = MutableLiveData<MutableList<AiringTodayTvResult>>()

    val airingTodayLiveData: LiveData<MutableList<AiringTodayTvResult>>
        get() = _airingTodayLiveData

    private fun getAiringTodayList() {
        coroutineScope.launch {
            val airingToday = repository.getAiringTodayTV()
            try {
                _airingTodayLiveData.value = airingToday
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }
}