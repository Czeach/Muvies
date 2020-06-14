package com.example.muvies.screens.tvShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.*
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
        getOnAirList()
        getPopularTvList()
        getTopRatedTvList()
        getTrendingTvList()
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

    private var _onAirLiveData = MutableLiveData<MutableList<OnAirTVResult>>()

    val onAirLiveData: LiveData<MutableList<OnAirTVResult>>
        get() = _onAirLiveData

    private fun getOnAirList() {
        coroutineScope.launch {
            val onAir = repository.getOnAirTV()
            try {
                _onAirLiveData.value = onAir
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _popularTvLiveData = MutableLiveData<MutableList<PopularTVResult>>()

    val popularTvLiveData: LiveData<MutableList<PopularTVResult>>
        get() = _popularTvLiveData

    private fun getPopularTvList() {
        coroutineScope.launch {
            val popularTv = repository.getPopularTv()
            try {
                _popularTvLiveData.value = popularTv
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _topRatedTvLiveData = MutableLiveData<MutableList<TopRatedTVResult>>()

    val topRatedTvLiveData: LiveData<MutableList<TopRatedTVResult>>
        get() = _topRatedTvLiveData

    private fun getTopRatedTvList() {
        coroutineScope.launch {
            val topRatedTv = repository.getTopRatedTv()
            try {
                _topRatedTvLiveData.value = topRatedTv
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }

    private var _trendingTvLiveData = MutableLiveData<MutableList<TrendingTvResult>>()

    val trendingTvLiveData: LiveData<MutableList<TrendingTvResult>>
        get() = _trendingTvLiveData

    private fun getTrendingTvList() {
        coroutineScope.launch {
            val trendingTv = repository.getTrendingTv()
            try {
                _trendingTvLiveData.value = trendingTv
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }
}