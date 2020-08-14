package com.czech.muvies.network

import com.czech.muvies.models.Movies
import com.czech.muvies.models.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
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
    ): Deferred<Response<Movies>>

    @GET("movie/upcoming")
    suspend fun getPagedUpcomingList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<Movies>>

    @GET("movie/popular")
    suspend fun getPagedPopularList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<Movies>>

    @GET("movie/top_rated")
    suspend fun getPagedTopRatedMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/now_playing")
    fun getInTheatersMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<Movies>>

    @GET("movie/now_playing")
    suspend fun getPagedInTheatersList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("tv/airing_today")
    fun getAiringTodayTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TvShows>>

    @GET("tv/airing_today")
    suspend fun getPagedAiringTodayList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/on_the_air")
    fun getOnAirTvAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TvShows>>

    @GET("tv/on_the_air")
    suspend fun getPagedOnAirTvList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/popular")
    fun getPopularTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TvShows>>

    @GET("tv/popular")
    suspend fun getPagedPopularTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/top_rated")
    fun getTopRatedTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TvShows>>

    @GET("tv/top_rated")
    suspend fun getPagedTopRatedTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("trending/movie/day")
    fun getTrendingMoviesAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<Movies>>

    @GET("trending/movie/day")
    suspend fun getPagedTrendingMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("trending/tv/day")
    fun getTrendingTVAsync(
        @Query("api_key") apiKey: String
    ): Deferred<Response<TvShows>>

    @GET("trending/tv/day")
    suspend fun getPagedTrendingTVList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): MovieDetails

    @GET("tv/{id}")
    suspend fun getTvShowsDetails(
        @Path("id") tvShowsId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TvShowDetails
}

object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}
