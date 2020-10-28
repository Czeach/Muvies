package com.czech.muvies.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class MoviesTabViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getPersonMovies(personId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getPersonMovies(personId, BuildConfig.API_KEY, LANGUAGE)))
        } catch (e: IOException) {
            emit(Resource.error(data = null, message = e.message ?: "Error getting cast details"))
        }
    }
}

class MoviesTabViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesTabViewModel::class.java)) {
            return MoviesTabViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}