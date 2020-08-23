package com.czech.muvies.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SeasonDetailsViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getSeasonDetails(show_id: Int, season_num: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getSeasonDetails(show_id, season_num, BuildConfig.API_KEY, LANGUAGE)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting season details"))
        }
    }
}

class SeasonDetailsViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeasonDetailsViewModel::class.java)) {
            return SeasonDetailsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}