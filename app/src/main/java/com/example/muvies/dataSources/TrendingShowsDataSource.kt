package com.example.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.muvies.BuildConfig
import com.example.muvies.models.TrendingTvResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TrendingShowsDataSource(private val apiService: MoviesApiService,
                              coroutineContext: CoroutineContext): PageKeyedDataSource<Int, TrendingTvResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TrendingTvResult>
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TrendingTvResult>) {
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
        callback: LoadCallback<Int, TrendingTvResult>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}

class TrendingShowsDataSourceFactory(private val apiService: MoviesApiService,
                                     coroutineContext: CoroutineContext): DataSource.Factory<Int, TrendingTvResult>() {

    val trendingShowsDataSourceLiveData = MutableLiveData<TrendingShowsDataSource>()

    override fun create(): DataSource<Int, TrendingTvResult> {
        val trendingShowsDataSource = TrendingShowsDataSource(apiService, Dispatchers.IO)
        trendingShowsDataSourceLiveData.postValue(trendingShowsDataSource)
        return trendingShowsDataSource
    }

}