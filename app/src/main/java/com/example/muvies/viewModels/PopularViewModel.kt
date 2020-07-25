package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.PopularDataSourceFactory
import com.example.muvies.models.PopularResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PopularViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val popularList: LiveData<PagedList<PopularResult>>
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

    fun getPopularList(): LiveData<PagedList<PopularResult>> = popularList
}