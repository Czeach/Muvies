package com.example.muvies.network

import com.example.muvies.BuildConfig
import com.example.muvies.model.PopularResult
import com.example.muvies.model.UpcomingResult

class MoviesRepository(private val apiService: MoviesApiService): BaseRepository() {
    suspend fun getUpcomingMovies(): MutableList<UpcomingResult> {
        val upcomingMoviesResponse = safeCall(
            call = {apiService.getUpcomingMovies(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching upcoming movies"
        )
        return upcomingMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getPopulatMovies(): MutableList<PopularResult> {
        val popularMoviesResponse = safeCall(
            call = {apiService.getPopularMovies(BuildConfig.API_KEY, "", 1).await()},
            error = "Error fetching popular movies"
        )
        return popularMoviesResponse?.results!!.toMutableList()
    }
}