package com.czech.muvies.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.czech.muvies.BuildConfig
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.*
import java.io.IOException

class SearchMoviesViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getSearch(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(apiService.searchMovie(BuildConfig.API_KEY, query)))
        } catch (e: IOException) {
            Resource.error(data = null, message = e.message ?: "Error movie")
        }
    }
}

class SearchViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMoviesViewModel::class.java)) {
            return SearchMoviesViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
