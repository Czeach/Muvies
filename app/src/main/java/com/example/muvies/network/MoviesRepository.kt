package com.example.muvies.network

import com.example.muvies.BuildConfig
import com.example.muvies.model.*

class MoviesRepository(private val apiService: MoviesApiService): BaseRepository() {
    suspend fun getUpcomingMovies(): MutableList<UpcomingResult> {
        val upcomingMoviesResponse = safeCall(
            call = {apiService.getUpcomingMoviesAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching upcoming movies"
        )
        return upcomingMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getPopularMovies(): MutableList<PopularResult> {
        val popularMoviesResponse = safeCall(
            call = {apiService.getPopularMoviesAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching popular movies"
        )
        return popularMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getTopRatedMovies(): MutableList<TopRatedResult> {
        val topRatedMoviesResponse = safeCall(
            call = {apiService.getTopRatedMoviesAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching top rated movies"
        )
        return topRatedMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getInTheatersMovies(): MutableList<InTheatersResult> {
        val inTheatresMoviesResponse = safeCall(
            call = {apiService.getInTheatersMoviesAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching in theatres movies"
        )

        return  inTheatresMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getAiringTodayTV(): MutableList<AiringTodayTvResult> {
        val airingTodayTvResponse = safeCall(
            call = {apiService.getAiringTodayTVAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching airing today TV shows"
        )

        return  airingTodayTvResponse?.results!!.toMutableList()
    }

    suspend fun getOnAirTV(): MutableList<OnAirTVResult> {
        val onAirTvResponse = safeCall(
            call = {apiService.getOnAirTvAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching on air TV shows"
        )

        return onAirTvResponse?.results!!.toMutableList()
    }
}