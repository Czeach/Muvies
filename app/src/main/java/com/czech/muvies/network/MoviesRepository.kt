package com.czech.muvies.network

import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.*
import com.czech.muvies.utils.BaseRepository

class MoviesRepository(private val apiService: MoviesApiService): BaseRepository() {

    suspend fun getUpcomingMovies(): MutableList<Movies.MoviesResult>? {
        val upcomingMoviesResponse = safeCall(
            call = {apiService.getUpcomingMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching upcoming movies"
        )
        return upcomingMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getPopularMovies(): MutableList<Movies.MoviesResult> {
        val popularMoviesResponse = safeCall(
            call = {apiService.getPopularMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching popular movies"
        )
        return popularMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getTopRatedMovies(): MutableList<Movies.MoviesResult> {
        val topRatedMoviesResponse = safeCall(
            call = {apiService.getTopRatedMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching top rated movies"
        )
        return topRatedMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getInTheatersMovies(): MutableList<Movies.MoviesResult> {
        val inTheatresMoviesResponse = safeCall(
            call = {apiService.getInTheatersMoviesAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching in theatres movies"
        )

        return  inTheatresMoviesResponse?.results!!.toMutableList()
    }

    suspend fun getAiringTodayTV(): MutableList<TvShows.TvShowsResult> {
        val airingTodayTvResponse = safeCall(
            call = {apiService.getAiringTodayTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching airing today TV shows"
        )

        return  airingTodayTvResponse?.results!!.toMutableList()
    }

    suspend fun getOnAirTV(): MutableList<TvShows.TvShowsResult> {
        val onAirTvResponse = safeCall(
            call = {apiService.getOnAirTvAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching on air TV shows"
        )

        return onAirTvResponse?.results!!.toMutableList()
    }

    suspend fun getPopularTv(): MutableList<TvShows.TvShowsResult> {
        val popularTvResponse = safeCall(
            call = {apiService.getPopularTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching popular TV shows"
        )

        return popularTvResponse?.results!!.toMutableList()
    }

    suspend fun getTopRatedTv(): MutableList<TvShows.TvShowsResult>? {
        val topRatedTvResponse = safeCall(
            call = {apiService.getTopRatedTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
            error = "Error fetching top rated TV shows"
        )

        return topRatedTvResponse?.results!!.toMutableList()
    }

    suspend fun getTrendingMovies(): MutableList<Movies.MoviesResult>? {
        val trendingMovies = safeCall(
            call = {apiService.getTrendingMoviesAsync(BuildConfig.API_KEY).await()},
            error = "Error fetching trending movies list"
        )

        return trendingMovies?.results!!.toMutableList()
    }

    suspend fun getTrendingTv(): MutableList<TvShows.TvShowsResult>? {
        val trendingTv = safeCall(
            call = {apiService.getTrendingTVAsync(BuildConfig.API_KEY).await()},
            error = "Error fetching trending tv shows list"
        )

        return trendingTv?.results!!.toMutableList()
    }
}