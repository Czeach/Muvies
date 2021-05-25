package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.*
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PagedMoviesViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val pageSize = 1000

    private var inTheatersList: LiveData<PagedList<Movies.MoviesResult>>
    private val inTheatersDataSourceFactory: InTheatersDataSourceFactory

    init {
        inTheatersDataSourceFactory = InTheatersDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        inTheatersList = LivePagedListBuilder<Int, Movies.MoviesResult>(inTheatersDataSourceFactory, config).build()
    }

    fun getInTheatersList(): LiveData<PagedList<Movies.MoviesResult>> = inTheatersList


    private var upcomingList: LiveData<PagedList<Movies.MoviesResult>>
    private val upcomingDataSourceFactory: UpcomingDataSourceFactory

    init {
        upcomingDataSourceFactory = UpcomingDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        upcomingList = LivePagedListBuilder(upcomingDataSourceFactory, config).build()
    }

    fun getUpcomingList(): LiveData<PagedList<Movies.MoviesResult>> = upcomingList


    private val popularList: LiveData<PagedList<Movies.MoviesResult>>
    private val popularDataSourceFactory: PopularDataSourceFactory

    init {
        popularDataSourceFactory = PopularDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        popularList = LivePagedListBuilder(popularDataSourceFactory, config).build()
    }

    fun getPopularList(): LiveData<PagedList<Movies.MoviesResult>> = popularList



    private val topRatedList: LiveData<PagedList<Movies.MoviesResult>>
    private val topRatedMoviesDataSourceFactory: TopRatedMoviesDataSourceFactory

    init {
        topRatedMoviesDataSourceFactory = TopRatedMoviesDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        topRatedList = LivePagedListBuilder(topRatedMoviesDataSourceFactory, config).build()
    }

    fun getTopRatedList(): LiveData<PagedList<Movies.MoviesResult>> = topRatedList


    private val trendingList: LiveData<PagedList<Movies.MoviesResult>>
    private val trendingMoviesDataSourceFactory: TrendingMoviesDataSourceFactory

    init {
        trendingMoviesDataSourceFactory = TrendingMoviesDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        trendingList = LivePagedListBuilder(trendingMoviesDataSourceFactory, config).build()
    }

    fun getTrendingMoviesList(): LiveData<PagedList<Movies.MoviesResult>> = trendingList

}