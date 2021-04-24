package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApi
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.network.MoviesRepository
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class TvShowsViewModel(private val apiService: MoviesApiService) : ViewModel() {

//    private val _response = MutableLiveData<String>()
//
//    val response: LiveData<String>
//        get() = _response
//
//    private var viewModelJob = Job()
//
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//    private val repository: MoviesRepository = MoviesRepository(MoviesApi.retrofitService)
//
//    init {
//        getAiringTodayList()
//        getOnAirList()
//        getPopularTvList()
//        getTopRatedTvList()
//        getTrendingTvList()
//    }
//
//    private var _airingTodayLiveData = MutableLiveData<MutableList<TvShows.TvShowsResult>>()
//
//    val airingTodayLiveData: LiveData<MutableList<TvShows.TvShowsResult>>
//        get() = _airingTodayLiveData
//
//    private fun getAiringTodayList() {
//        coroutineScope.launch {
//            val airingToday = repository.getAiringTodayTV()
//            try {
//                _airingTodayLiveData.value = airingToday
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//            }
//        }
//    }
//
//    private var _onAirLiveData = MutableLiveData<MutableList<TvShows.TvShowsResult>>()
//
//    val onAirLiveData: LiveData<MutableList<TvShows.TvShowsResult>>
//        get() = _onAirLiveData
//
//    private fun getOnAirList() {
//        coroutineScope.launch {
//            val onAir = repository.getOnAirTV()
//            try {
//                _onAirLiveData.value = onAir
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//            }
//        }
//    }
//
//    private var _popularTvLiveData = MutableLiveData<MutableList<TvShows.TvShowsResult>>()
//
//    val popularTvLiveData: LiveData<MutableList<TvShows.TvShowsResult>>
//        get() = _popularTvLiveData
//
//    private fun getPopularTvList() {
//        coroutineScope.launch {
//            val popularTv = repository.getPopularTv()
//            try {
//                _popularTvLiveData.value = popularTv
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//            }
//        }
//    }
//
//    private var _topRatedTvLiveData = MutableLiveData<MutableList<TvShows.TvShowsResult>>()
//
//    val topRatedTvLiveData: LiveData<MutableList<TvShows.TvShowsResult>>
//        get() = _topRatedTvLiveData
//
//    private fun getTopRatedTvList() {
//        coroutineScope.launch {
//            val topRatedTv = repository.getTopRatedTv()
//            try {
//                _topRatedTvLiveData.value = topRatedTv
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//            }
//        }
//    }
//
//    private var _trendingTvLiveData = MutableLiveData<MutableList<TvShows.TvShowsResult>>()
//
//    val trendingTvLiveData: LiveData<MutableList<TvShows.TvShowsResult>>
//        get() = _trendingTvLiveData
//
//    private fun getTrendingTvList() {
//        coroutineScope.launch {
//            val trendingTv = repository.getTrendingTv()
//            try {
//                _trendingTvLiveData.value = trendingTv
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//            }
//        }
//    }

//    fun getInTheater() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//
//        try {
//            emit(Resource.success(data = apiService.getInTheatersMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message?: "Error getting In Theater movies"))
//        }
//    }

    fun getAiringToday() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getAiringTodayTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Airing Today Tv Shows"))
        }
    }

    fun getOnAir() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getOnAirTvAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting On Air Tv Shows"))
        }
    }

    fun getPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getPopularTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Popular Tv Shows"))
        }
    }

    fun getTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getTopRatedTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Top Rated Tv Shows"))
        }
    }

    fun getTrending() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getTrendingTVAsync(BuildConfig.API_KEY)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Trending Tv Shows"))
        }
    }
}

class TvShowsViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
            return TvShowsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}