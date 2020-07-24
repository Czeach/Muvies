package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.DiscoverDataSourceFactory
import com.example.muvies.dataSources.InTheatersDataSourceFactory
import com.example.muvies.models.*
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesApiService
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

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
