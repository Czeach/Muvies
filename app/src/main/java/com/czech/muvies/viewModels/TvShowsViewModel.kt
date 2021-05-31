package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.network.ShowsRepository
import com.czech.muvies.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class TvShowsViewModel(
    private val apiService: MoviesApiService,
    private val repository: ShowsRepository) : ViewModel() {

    private val _showsResponse = MutableLiveData<Resource<List<TvShows.TvShowsResult>>>()
    val showsResponse: LiveData<Resource<List<TvShows.TvShowsResult>>>
        get() = _showsResponse

    init {
        getAllShows()
    }

    private fun getAllShows() = viewModelScope.launch {
        val airingTodayResponse = repository.getAiringToday()
        val onAirResponse = repository.getOnAir()
        val popularResponse = repository.getPopular()
        val topRatedResponse = repository.getTopRated()
        val trendingResponse = repository.getTrending()
        combine(
            airingTodayResponse,
            onAirResponse,
            popularResponse,
            topRatedResponse,
            trendingResponse
        ) { airingToday, onAir, popular, topRated, trending ->

            airingToday.results.map {
                it.showCategory = TvShows.TvShowsResult.TvShowCategory.AIRING_TODAY
            }

            onAir.results.map {
                it.showCategory = TvShows.TvShowsResult.TvShowCategory.ON_AIR
            }

            popular.results.map {
                it.showCategory = TvShows.TvShowsResult.TvShowCategory.POPULAR
            }

            topRated.results.map {
                it.showCategory = TvShows.TvShowsResult.TvShowCategory.TOP_RATED
            }

            trending.results.map {
                it.showCategory = TvShows.TvShowsResult.TvShowCategory.TRENDING
            }

            listOf(
                airingToday.results,
                onAir.results,
                popular.results,
                topRated.results,
                trending.results
            ).flatten()
        }.onStart { _showsResponse.postValue(Resource.loading(data = null)) }
            .catch { _showsResponse.postValue(Resource.error(data = null, "Something went wrong")) }
            .flowOn(Dispatchers.IO)
            .collect {
                _showsResponse.postValue(Resource.success(it))
            }
    }

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

class TvShowsViewModelFactory(
    private val apiService: MoviesApiService,
    private val repository: ShowsRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvShowsViewModel::class.java)) {
            return TvShowsViewModel(apiService, repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}