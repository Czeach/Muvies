package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.PopularShowsDataSourceFactory
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PopularShowsViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val popularShowsList: LiveData<PagedList<TvShows.TvShowsResult>>
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

    fun getPopularShowsList(): LiveData<PagedList<TvShows.TvShowsResult>> = popularShowsList
}