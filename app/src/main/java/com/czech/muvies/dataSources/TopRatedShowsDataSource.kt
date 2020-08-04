package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.TopRatedTVResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TopRatedShowsDataSource(private val apiService: MoviesApiService,
                              coroutineContext: CoroutineContext): PageKeyedDataSource<Int, TopRatedTVResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TopRatedTVResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedTopRatedTVList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch top rated tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TopRatedTVResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedTopRatedTVList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch top rated tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TopRatedTVResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}

class TopRatedShowsDataSourceFactory(private val apiService: MoviesApiService,
                                     coroutineContext: CoroutineContext): DataSource.Factory<Int, TopRatedTVResult>() {

    val topRatedShowsDataSourceLiveData = MutableLiveData<TopRatedShowsDataSource>()

    override fun create(): DataSource<Int, TopRatedTVResult> {
        val topRatedShowsDataSource = TopRatedShowsDataSource(apiService, Dispatchers.IO)
        topRatedShowsDataSourceLiveData.postValue(topRatedShowsDataSource)
        return topRatedShowsDataSource
    }

}