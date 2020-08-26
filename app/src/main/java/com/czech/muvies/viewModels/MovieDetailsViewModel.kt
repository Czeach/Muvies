package com.czech.muvies.viewModels

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.dataSources.SimilarMoviesDataSourceFactory
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailsViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getMovieDetails(movie_id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getMovieDetails(movie_id, BuildConfig.API_KEY, LANGUAGE)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error getting movie details"))
        }
    }

    private val pageSize = 1000

    val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setEnablePlaceholders(false)
        .build()

    fun getSimilarMovies(movieId: Int): LiveData<PagedList<SimilarMovies.SimilarMoviesResult>> = LivePagedListBuilder(
            SimilarMoviesDataSourceFactory(apiService, Dispatchers.IO, movieId),
            config
        ).build()


}

class MovieDetailsViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}