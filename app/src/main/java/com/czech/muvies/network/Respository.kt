package com.czech.muvies.network

import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.Movies
import com.czech.muvies.models.TvShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviesRespository(private val apiService: MoviesApiService) {

    fun getInTheaters(): Flow<Movies> {
        return flow { emit(apiService.getInTheatersMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getUpcoming(): Flow<Movies> {
        return flow { emit(apiService.getUpcomingMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getPopular(): Flow<Movies> {
        return flow { emit(apiService.getPopularMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getTopRated(): Flow<Movies> {
        return flow { emit(apiService.getTopRatedMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getTrending(): Flow<Movies> {
        return flow { emit(apiService.getTrendingMoviesAsync(BuildConfig.API_KEY)) }
    }
}

class ShowsRepository(private val apiService: MoviesApiService) {

    fun getAiringToday(): Flow<TvShows> {
        return flow { emit(apiService.getAiringTodayTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getOnAir(): Flow<TvShows> {
        return flow { emit(apiService.getOnAirTvAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getPopular(): Flow<TvShows> {
        return flow { emit(apiService.getPopularTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getTopRated(): Flow<TvShows> {
        return flow { emit(apiService.getTopRatedTVAsync(BuildConfig.API_KEY, LANGUAGE, 1)) }
    }

    fun getTrending(): Flow<TvShows> {
        return flow { emit(apiService.getTrendingTVAsync(BuildConfig.API_KEY)) }
    }
}