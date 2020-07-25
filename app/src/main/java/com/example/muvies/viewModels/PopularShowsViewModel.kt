package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.OnAirDataSourceFactory
import com.example.muvies.dataSources.PopularShowsDataSourceFactory
import com.example.muvies.models.OnAirTVResult
import com.example.muvies.models.PopularTVResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PopularShowsViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val popularShowsList: LiveData<PagedList<PopularTVResult>>
    private val pageSize = 1000
    private val popularShowsDataSourceFactory: PopularShowsDataSourceFactory

    init {
        popularShowsDataSourceFactory = PopularShowsDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        popularShowsList = LivePagedListBuilder(popularShowsDataSourceFactory, config).build()
    }

    fun getPopularShowsList(): LiveData<PagedList<PopularTVResult>> = popularShowsList
}