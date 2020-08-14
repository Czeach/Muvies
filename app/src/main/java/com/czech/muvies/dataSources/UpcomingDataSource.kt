package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.Movies
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class UpcomingDataSource(private var apiService: MoviesApiService,
                         coroutineContext: CoroutineContext): PageKeyedDataSource<Int, Movies.MoviesResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies.MoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedUpcomingList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch upcoming movies!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies.MoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedUpcomingList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch upcoming movies!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies.MoviesResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class UpcomingDataSourceFactory(private val apiService: MoviesApiService,
                                  coroutineContext: CoroutineContext): DataSource.Factory<Int, Movies.MoviesResult>() {

    val upcomingDataSourceLiveData = MutableLiveData<UpcomingDataSource>()

    override fun create(): DataSource<Int, Movies.MoviesResult> {
        val upcomingDataSource = UpcomingDataSource(apiService, Dispatchers.IO)
        upcomingDataSourceLiveData.postValue(upcomingDataSource)
        return upcomingDataSource
    }

}