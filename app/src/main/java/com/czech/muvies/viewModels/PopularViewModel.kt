package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.PopularDataSourceFactory
import com.czech.muvies.models.MoviesResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PopularViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val popularList: LiveData<PagedList<MoviesResult>>
    private val pageSize = 1000
    private val popularDataSourceFactory: PopularDataSourceFactory

    init {
        popularDataSourceFactory = PopularDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        popularList = LivePagedListBuilder(popularDataSourceFactory, config).build()
    }

    fun getPopularList(): LiveData<PagedList<MoviesResult>> = popularList
}