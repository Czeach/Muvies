package com.czech.muvies.viewModels

import androidx.lifecycle.*
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

class MovieDetailsViewModel(private val apiService: MoviesApiService) : ViewModel() {

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

class MovieDetailsViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}