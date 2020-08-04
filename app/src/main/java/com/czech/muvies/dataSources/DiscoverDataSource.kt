package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.DiscoverResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DiscoverDataSource(private var apiService: MoviesApiService,
                         coroutineContext: CoroutineContext): PageKeyedDataSource<Int, DiscoverResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DiscoverResult>
    ) {
        scope.launch {
            try {
                val response = apiService.getPagedDiscoverList(BuildConfig.API_KEY, LANGUAGE, "vote_average.desc", 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch discover list! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DiscoverResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedDiscoverList(BuildConfig.API_KEY, LANGUAGE, "vote_average.desc", params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch discover list! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DiscoverResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class DiscoverDataSourceFactory(private var apiService: MoviesApiService,
                                coroutineContext: CoroutineContext): DataSource.Factory<Int, DiscoverResult>() {

    val discoverDataSourceLiveData = MutableLiveData<DiscoverDataSource>()

    override fun create(): DataSource<Int, DiscoverResult> {
        val discoverDataSource = DiscoverDataSource(apiService, Dispatchers.IO)
        discoverDataSourceLiveData.postValue(discoverDataSource)
        return discoverDataSource
    }

}