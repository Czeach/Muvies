package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.TopRatedMoviesDataSourceFactory
import com.example.muvies.dataSources.TrendingMoviesDataSourceFactory
import com.example.muvies.models.TopRatedResult
import com.example.muvies.models.TrendingMoviesResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class TrendingMoviesViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val trendingList: LiveData<PagedList<TrendingMoviesResult>>
    private val pageSize = 1000
    private val trendingMoviesDataSourceFactory: TrendingMoviesDataSourceFactory

    init {
        trendingMoviesDataSourceFactory = TrendingMoviesDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        trendingList = LivePagedListBuilder(trendingMoviesDataSourceFactory, config).build()
    }

    fun getTrendingMoviesList(): LiveData<PagedList<TrendingMoviesResult>> = trendingList
}