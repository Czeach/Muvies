package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.BuildConfig
import com.example.muvies.dataSources.InTheatersDataSource
import com.example.muvies.dataSources.InTheatersDataSourceFactory
import com.example.muvies.models.InTheatersResult
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesApiService
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

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