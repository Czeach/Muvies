package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.InTheatersDataSourceFactory
import com.czech.muvies.models.InTheatersResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class InTheatersViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private var inTheatersList: LiveData<PagedList<InTheatersResult>>
    private val pageSize = 1000
    private val inTheatersDataSourceFactory: InTheatersDataSourceFactory

    init {
        inTheatersDataSourceFactory = InTheatersDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        inTheatersList = LivePagedListBuilder<Int, InTheatersResult>(inTheatersDataSourceFactory, config).build()
    }

    fun getInTheatersList(): LiveData<PagedList<InTheatersResult>> = inTheatersList

}