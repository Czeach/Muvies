package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.AiringTodayDataSourceFactory
import com.example.muvies.dataSources.OnAirDataSourceFactory
import com.example.muvies.models.AiringTodayTvResult
import com.example.muvies.models.OnAirTVResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class OnAirViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val onAirList: LiveData<PagedList<OnAirTVResult>>
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

    fun getOnAirList(): LiveData<PagedList<OnAirTVResult>> = onAirList
}