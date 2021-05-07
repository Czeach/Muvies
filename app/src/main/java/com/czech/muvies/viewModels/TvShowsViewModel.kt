package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class TvShowsViewModel(private val apiService: MoviesApiService) : ViewModel() {

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