package com.example.muvies.network

import com.example.muvies.BuildConfig
import com.example.muvies.model.InTheatresResult
import com.example.muvies.model.PopularResult
import com.example.muvies.model.TopRatedResult
import com.example.muvies.model.UpcomingResult

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

    suspend fun getInTheatresMovies(): MutableList<InTheatresResult> {
        val inTheatresResponse = safeCall(
            call = {apiService.getInTheatresMoviesAsync(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching in theatres movies"
        )

        return  inTheatresResponse?.results!!.toMutableList()
    }
}