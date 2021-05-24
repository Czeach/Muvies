package com.czech.muvies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.czech.muvies.dataSources.*
import com.czech.muvies.models.TvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers

class PagedShowsViewModel : ViewModel() {

    private val apiService = MoviesApiService.getService()
    private val pageSize = 1000


    private val airingTodayList: LiveData<PagedList<TvShows.TvShowsResult>>
    private val airingTodayDataSourceFactory: AiringTodayDataSourceFactory

    init {
        airingTodayDataSourceFactory = AiringTodayDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        airingTodayList = LivePagedListBuilder(airingTodayDataSourceFactory, config).build()
    }

    fun getAiringTodayList(): LiveData<PagedList<TvShows.TvShowsResult>> = airingTodayList


    private val onAirList: LiveData<PagedList<TvShows.TvShowsResult>>
    private val onAirDataSourceFactory: OnAirDataSourceFactory

    init {
        onAirDataSourceFactory = OnAirDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        onAirList = LivePagedListBuilder(onAirDataSourceFactory, config).build()
    }

    fun getOnAirList(): LiveData<PagedList<TvShows.TvShowsResult>> = onAirList


    private val popularShowsList: LiveData<PagedList<TvShows.TvShowsResult>>
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


    private val topRatedShowsList: LiveData<PagedList<TvShows.TvShowsResult>>
    private val topRatedShowsDataSourceFactory: TopRatedShowsDataSourceFactory

    init {
        topRatedShowsDataSourceFactory = TopRatedShowsDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        topRatedShowsList = LivePagedListBuilder(topRatedShowsDataSourceFactory, config).build()
    }

    fun getTopRatedShowsList(): LiveData<PagedList<TvShows.TvShowsResult>> = topRatedShowsList


    private val trendingShowsList: LiveData<PagedList<TvShows.TvShowsResult>>
    private val trendingShowsDataSourceFactory: TrendingShowsDataSourceFactory

    init {
        trendingShowsDataSourceFactory = TrendingShowsDataSourceFactory(apiService, Dispatchers.IO)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(false)
            .build()
        trendingShowsList = LivePagedListBuilder(trendingShowsDataSourceFactory, config).build()
    }

    fun getTrendingShowsList(): LiveData<PagedList<TvShows.TvShowsResult>> = trendingShowsList
}