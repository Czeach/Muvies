package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.OnAirDataSourceFactory
import com.czech.muvies.models.TvShowsResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class OnAirViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val onAirList: LiveData<PagedList<TvShowsResult>>
    private val pageSize = 1000
    private val onAirDataSourceFactory: OnAirDataSourceFactory

    init {
        onAirDataSourceFactory = OnAirDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        onAirList = LivePagedListBuilder(onAirDataSourceFactory, config).build()
    }

    fun getOnAirList(): LiveData<PagedList<TvShowsResult>> = onAirList
}