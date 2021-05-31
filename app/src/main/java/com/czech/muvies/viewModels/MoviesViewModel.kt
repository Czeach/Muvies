package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.network.MoviesRespository
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalCoroutinesApi
class MoviesViewModel(
    private val apiService: MoviesApiService,
    private val repository: MoviesRespository) : ViewModel() {

    private val _moviesResponse = MutableLiveData<Resource<List<Movies.MoviesResult>>>()
    val moviesResponse:LiveData<Resource<List<Movies.MoviesResult>>>
        get() = _moviesResponse

    init {
        getMovieLists()
    }

    @ExperimentalCoroutinesApi
    private fun getMovieLists() = viewModelScope.launch {
        val inTheatersResponse = repository.getInTheaters()
        val upcomingResponse = repository.getUpcoming()
        val popularResponse = repository.getPopular()
        val topRatedResponse = repository.getTopRated()
        val trendingResponse = repository.getTrending()
        combine(
            inTheatersResponse,
            upcomingResponse,
            popularResponse,
            topRatedResponse,
            trendingResponse
        ) { inTheaters, upcoming, popular, topRated, trending ->

            inTheaters.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.IN_THEATER
            }

            upcoming.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.UPCOMING
            }

            popular.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.POPULAR
            }

            topRated.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.TOP_RATED
            }

            trending.results.map {
                it.movieCategory = Movies.MoviesResult.MovieCategory.TRENDING
            }

            listOf(
                inTheaters.results,
                upcoming.results,
                popular.results,
                topRated.results,
                trending.results
            ).flatten()
        }.onStart { _moviesResponse.postValue(Resource.loading(data = null)) }
            .catch { _moviesResponse.postValue(Resource.error(data = null, "Something went wrong")) }
            .flowOn(Dispatchers.IO)
            .collect {
                _moviesResponse.postValue(Resource.success(it))
            }

    }
}

class MovieViewModelFactory(
    private val apiService: MoviesApiService,
    private val repository: MoviesRespository): ViewModelProvider.Factory {

    @ExperimentalCoroutinesApi
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(apiService, repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}