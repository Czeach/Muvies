package com.example.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.muvies.dataSources.AiringTodayDataSourceFactory
import com.example.muvies.dataSources.TrendingMoviesDataSourceFactory
import com.example.muvies.models.AiringTodayTvResult
import com.example.muvies.models.TrendingMoviesResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class AiringTodayViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val airingTodayList: LiveData<PagedList<AiringTodayTvResult>>
    private val pageSize = 1000
    private val airingTodayDataSourceFactory: AiringTodayDataSourceFactory

    init {
        airingTodayDataSourceFactory = AiringTodayDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        airingTodayList = LivePagedListBuilder(airingTodayDataSourceFactory, config).build()
    }

    fun getAiringTodayList(): LiveData<PagedList<AiringTodayTvResult>> = airingTodayList
}