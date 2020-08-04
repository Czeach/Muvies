package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.UpcomingDataSourceFactory
import com.czech.muvies.models.UpcomingResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class UpcomingViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private var upcomingList: LiveData<PagedList<UpcomingResult>>
    private val pageSize = 1000
    private val upcomingDataSourceFactory: UpcomingDataSourceFactory

    init {
        upcomingDataSourceFactory = UpcomingDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        upcomingList = LivePagedListBuilder(upcomingDataSourceFactory, config).build()
    }

    fun getUpcomingList(): LiveData<PagedList<UpcomingResult>> = upcomingList
}