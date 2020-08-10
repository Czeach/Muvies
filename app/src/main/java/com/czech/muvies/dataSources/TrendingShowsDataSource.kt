package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.models.TvShowsResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TrendingShowsDataSource(private val apiService: MoviesApiService,
                              coroutineContext: CoroutineContext): PageKeyedDataSource<Int, TvShowsResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvShowsResult>
    ) {
        scope.launch {
            try {
                val response = apiService.getPagedTrendingTVList(BuildConfig.API_KEY, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                        Log.d("MoviesDataSource", "successful!!")
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch trending tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShowsResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedTrendingTVList(BuildConfig.API_KEY, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch trending tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvShowsResult>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}

class TrendingShowsDataSourceFactory(private val apiService: MoviesApiService,
                                     coroutineContext: CoroutineContext): DataSource.Factory<Int, TvShowsResult>() {

    val trendingShowsDataSourceLiveData = MutableLiveData<TrendingShowsDataSource>()

    override fun create(): DataSource<Int, TvShowsResult> {
        val trendingShowsDataSource = TrendingShowsDataSource(apiService, Dispatchers.IO)
        trendingShowsDataSourceLiveData.postValue(trendingShowsDataSource)
        return trendingShowsDataSource
    }

}