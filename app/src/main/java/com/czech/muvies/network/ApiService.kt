package com.czech.muvies.network

import com.czech.muvies.models.InTheatersMovies
import com.czech.muvies.models.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL).build()

interface MoviesApiService {

    companion object {
        fun getService(): MoviesApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return  retrofit.create(MoviesApiService::class.java)
        }
    }

    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<UpcomingMovies>>

    @GET("movie/upcoming")
    suspend fun getPagedUpcomingList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<UpcomingMovies>

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<PopularMovies>>

    @GET("movie/popular")
    suspend fun getPagedPopularList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<PopularMovies>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TopRatedMovies>>

    @GET("movie/top_rated")
    suspend fun getPagedTopRatedMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TopRatedMovies>

    @GET("movie/now_playing")
    fun getInTheatersMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<InTheatersMovies>>

    @GET("movie/now_playing")
    suspend fun getPagedInTheatersList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<InTheatersMovies>

    @GET("tv/airing_today")
    fun getAiringTodayTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<AiringTodayTV>>

    @GET("tv/airing_today")
    suspend fun getPagedAiringTodayList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<AiringTodayTV>

    @GET("tv/on_the_air")
    fun getOnAirTvAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<OnAirTV>>

    @GET("tv/on_the_air")
    suspend fun getPagedOnAirTvList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<OnAirTV>

    @GET("tv/popular")
    fun getPopularTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<PopularTV>>

    @GET("tv/popular")
    suspend fun getPagedPopularTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<PopularTV>

    @GET("tv/top_rated")
    fun getTopRatedTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TopRatedTV>>

    @GET("tv/top_rated")
    suspend fun getPagedTopRatedTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TopRatedTV>

    @GET("trending/movie/day")
    fun getTrendingMoviesAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<TrendingMovies>>

    @GET("trending/movie/day")
    suspend fun getPagedTrendingMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TrendingMovies>

    @GET("trending/tv/day")
    fun getTrendingTVAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<TrendingTV>>

    @GET("trending/tv/day")
    suspend fun getPagedTrendingTVList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TrendingTV>
}

object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}
