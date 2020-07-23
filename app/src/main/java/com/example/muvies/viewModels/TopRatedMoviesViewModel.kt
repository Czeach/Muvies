package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.PopularDataSourceFactory
import com.example.muvies.dataSources.TopRatedMoviesDataSourceFactory
import com.example.muvies.models.PopularResult
import com.example.muvies.models.TopRatedResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class TopRatedMoviesViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val topRatedList: LiveData<PagedList<TopRatedResult>>
    private val pageSize = 1000
    private val topRatedMoviesDataSourceFactory: TopRatedMoviesDataSourceFactory

    init {
        topRatedMoviesDataSourceFactory = TopRatedMoviesDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        topRatedList = LivePagedListBuilder(topRatedMoviesDataSourceFactory, config).build()
    }

    fun getTopRatedList(): LiveData<PagedList<TopRatedResult>> = topRatedList
}