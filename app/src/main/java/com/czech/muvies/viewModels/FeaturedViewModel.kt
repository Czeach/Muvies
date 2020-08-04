package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.DiscoverDataSourceFactory
import com.czech.muvies.models.*
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class FeaturedViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private var discoverList: LiveData<PagedList<DiscoverResult>>
    private val pageSize = 1000
    private val discoverDataSourceFactory: DiscoverDataSourceFactory

    init {
        discoverDataSourceFactory = DiscoverDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        discoverList = LivePagedListBuilder(discoverDataSourceFactory, config).build()
    }

    fun getDiscoverList(): LiveData<PagedList<DiscoverResult>> = discoverList
}
