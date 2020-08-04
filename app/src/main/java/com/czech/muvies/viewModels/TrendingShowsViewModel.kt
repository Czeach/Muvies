package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.TrendingShowsDataSourceFactory
import com.czech.muvies.models.TrendingTvResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class TrendingShowsViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val trendingShowsList: LiveData<PagedList<TrendingTvResult>>
    private val pageSize = 1000
    private val trendingShowsDataSourceFactory: TrendingShowsDataSourceFactory

    init {
        trendingShowsDataSourceFactory = TrendingShowsDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        trendingShowsList = LivePagedListBuilder(trendingShowsDataSourceFactory, config).build()
    }

    fun getTrendingShowsList(): LiveData<PagedList<TrendingTvResult>> = trendingShowsList
}