package com.example.muvies.network

import com.example.muvies.BuildConfig
import com.example.muvies.model.Result
import com.example.muvies.model.UpcomingMovies

class MoviesRepository(private val apiService: MoviesApiService): BaseRepository() {
    suspend fun getUpcomingMovies(): MutableList<Result> {
        val upcomingMoviesResponse = safeCall(
            call = {apiService.getUpcomingMovies(BuildConfig.API_KEY, "en-US", 1).await()},
            error = "Error fetching upcoming movies"
        )
        return upcomingMoviesResponse?.results!!.toMutableList()
    }
}