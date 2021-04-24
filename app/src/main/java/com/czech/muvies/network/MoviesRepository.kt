package com.czech.muvies.network

import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.*
import com.czech.muvies.utils.BaseRepository

class MoviesRepository(private val apiService: MoviesApiService): BaseRepository() {

//    suspend fun getAiringTodayTV(): MutableList<TvShows.TvShowsResult> {
//        val airingTodayTvResponse = safeCall(
//            call = {apiService.getAiringTodayTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
//            error = "Error fetching airing today TV shows"
//        )
//
//        return  airingTodayTvResponse?.results!!.toMutableList()
//    }

//    suspend fun getOnAirTV(): MutableList<TvShows.TvShowsResult> {
//        val onAirTvResponse = safeCall(
//            call = {apiService.getOnAirTvAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
//            error = "Error fetching on air TV shows"
//        )
//
//        return onAirTvResponse?.results!!.toMutableList()
//    }

//    suspend fun getPopularTv(): MutableList<TvShows.TvShowsResult> {
//        val popularTvResponse = safeCall(
//            call = {apiService.getPopularTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
//            error = "Error fetching popular TV shows"
//        )
//
//        return popularTvResponse?.results!!.toMutableList()
//    }

//    suspend fun getTopRatedTv(): MutableList<TvShows.TvShowsResult>? {
//        val topRatedTvResponse = safeCall(
//            call = {apiService.getTopRatedTVAsync(BuildConfig.API_KEY, LANGUAGE, 1).await()},
//            error = "Error fetching top rated TV shows"
//        )
//
//        return topRatedTvResponse?.results!!.toMutableList()
//    }

//    suspend fun getTrendingTv(): MutableList<TvShows.TvShowsResult>? {
//        val trendingTv = safeCall(
//            call = {apiService.getTrendingTVAsync(BuildConfig.API_KEY).await()},
//            error = "Error fetching trending tv shows list"
//        )
//
//        return trendingTv?.results!!.toMutableList()
//    }
}