package com.example.muvies.network

import com.example.muvies.model.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL).build()

interface MoviesApiService {
    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<UpcomingMovies>>

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<PopularMovies>>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<TopRatedMovies>>

    @GET("movie/now_playing")
    fun getInTheatresMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Deferred<Response<InTheatresMovies>>
}

object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}
