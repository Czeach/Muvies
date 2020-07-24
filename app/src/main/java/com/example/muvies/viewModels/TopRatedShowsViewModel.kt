package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.PopularShowsDataSourceFactory
import com.example.muvies.dataSources.TopRatedShowsDataSourceFactory
import com.example.muvies.models.PopularTVResult
import com.example.muvies.models.TopRatedTVResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class TopRatedShowsViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val topRatedShowsList: LiveData<PagedList<TopRatedTVResult>>
    private val pageSize = 1000
    private val topRatedShowsDataSourceFactory: TopRatedShowsDataSourceFactory

    init {
        topRatedShowsDataSourceFactory = TopRatedShowsDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        topRatedShowsList = LivePagedListBuilder(topRatedShowsDataSourceFactory, config).build()
    }

    fun getTopRatedShowsList(): LiveData<PagedList<TopRatedTVResult>> = topRatedShowsList
}