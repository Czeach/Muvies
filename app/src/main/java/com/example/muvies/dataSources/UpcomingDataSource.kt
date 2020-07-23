package com.example.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.muvies.BuildConfig
import com.example.muvies.LANGUAGE
import com.example.muvies.models.InTheatersResult
import com.example.muvies.models.UpcomingResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class UpcomingDataSource(private var apiService: MoviesApiService,
                         coroutineContext: CoroutineContext): PageKeyedDataSource<Int, UpcomingResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, UpcomingResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedUpcomingList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch in theaters movies!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UpcomingResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedUpcomingList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch in theaters movies!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UpcomingResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class UpcomingDataSourceFactory(private val apiService: MoviesApiService,
                                  coroutineContext: CoroutineContext): DataSource.Factory<Int, UpcomingResult>() {

    val upcomingDataSourceLiveData = MutableLiveData<UpcomingDataSource>()

    override fun create(): DataSource<Int, UpcomingResult> {
        val upcomingDataSource = UpcomingDataSource(apiService, Dispatchers.IO)
        upcomingDataSourceLiveData.postValue(upcomingDataSource)
        return upcomingDataSource
    }

}