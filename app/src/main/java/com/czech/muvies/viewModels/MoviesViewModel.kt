package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.pagedAdapters.topRatedItemClickListener
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getInTheater() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getInTheatersMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting In Theater movies"))
        }
    }

    fun getUpcoming() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getUpcomingMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Upcoming movies"))
        }
    }

    fun getPopular() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getPopularMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Popular movies"))
        }
    }

    fun getTopRated() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getTopRatedMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Top Rated movies"))
        }
    }

    fun getTrending() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getTrendingMoviesAsync(BuildConfig.API_KEY)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Trending movies"))
        }
    }

    fun getAllMovies() = liveData(Dispatchers.IO) {

        val trending = emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = apiService.getTrendingMoviesAsync(BuildConfig.API_KEY)))
        } catch (e:Exception) {
            emit(Resource.error(data = null, message = e.message?: "Error getting Trending movies"))
        }
    }



//    private val _movieResponse = MutableLiveData<Resource<List<Movies.MoviesResult>>>()
//    val movieResponse: LiveData<Resource<List<Movies.MoviesResult>>> get() = _movieResponse
//
//    fun getAllMovies() = viewModelScope.launch {
//
//        val topRated = liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = apiService.getTopRatedMoviesAsync(BuildConfig.API_KEY,
//                    LANGUAGE,
//                    1)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null,
//                    message = e.message ?: "Error getting Top Rated movies"))
//            }
//        }
//
//        val trending = liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = apiService.getTrendingMoviesAsync(BuildConfig.API_KEY)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null,
//                    message = e.message ?: "Error getting Trending movies"))
//            }
//        }
//        combine(
//            topRated, trending
//        )
//    }

}

class MovieViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}