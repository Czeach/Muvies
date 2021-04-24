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

//    @GET("movie/upcoming")
//    fun getUpcomingMoviesAsync(
//        @Query("api_key") apiKey: String,
//        @Query("language") language: String,
//        @Query("page") page: Int
//    ): Deferred<Response<Movies>>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Movies

    @GET("movie/upcoming")
    suspend fun getPagedUpcomingList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/popular")
    suspend fun getPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Movies

    @GET("movie/popular")
    suspend fun getPagedPopularList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Movies

    @GET("movie/top_rated")
    suspend fun getPagedTopRatedMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("movie/now_playing")
    suspend fun getInTheatersMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Movies

    @GET("movie/now_playing")
    suspend fun getPagedInTheatersList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TvShows

    @GET("tv/airing_today")
    suspend fun getPagedAiringTodayList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/on_the_air")
    suspend fun getOnAirTvAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TvShows

    @GET("tv/on_the_air")
    suspend fun getPagedOnAirTvList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/popular")
    suspend fun getPopularTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TvShows

    @GET("tv/popular")
    suspend fun getPagedPopularTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVAsync(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TvShows

    @GET("tv/top_rated")
    suspend fun getPagedTopRatedTVList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<TvShows>

    @GET("trending/movie/day")
    suspend fun getTrendingMoviesAsync(
        @Query("api_key") apiKey: String
    ): Movies

    @GET("trending/movie/day")
    suspend fun getPagedTrendingMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("trending/tv/day")
    suspend fun getTrendingTVAsync(
        @Query("api_key") apiKey: String
    ): TvShows

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

    @GET("tv/{id}/season/{season}")
    suspend fun getSeasonDetails(
        @Path("id") tvShowsId: Int,
        @Path("season") seasonNum: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): SeasonDetails

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<SimilarMovies>

    @GET("tv/{id}/similar")
    suspend fun getSimilarTvShows(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<SimilarTvShows>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieCredits

    @GET("person/{id}")
    suspend fun getCastDetails(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): PersonDetails

    @GET("tv/{id}/credits")
    suspend fun getShowCredits(
        @Path("id") showId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): TvShowCredits

    @GET("person/{id}/movie_credits")
    suspend fun getPersonMovies(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): PersonMovies

    @GET("person/{id}/tv_credits")
    suspend fun getPersonTvShows(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): PersonTvShows
}

object MoviesApi {
    val retrofitService: MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}
