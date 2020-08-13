package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.Movies
import com.czech.muvies.models.MoviesResult
import com.czech.muvies.network.MoviesApi
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesDetailsViewModel(private val apiService: MoviesApiService) : ViewModel() {

    private val movie = MoviesResult()

    fun getMovieDetails(movie_id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getMovieDetails(movie_id, BuildConfig.API_KEY, LANGUAGE)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error getting movie details"))
        }
    }
}