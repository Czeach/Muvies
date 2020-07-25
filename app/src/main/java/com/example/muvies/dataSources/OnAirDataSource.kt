package com.example.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.muvies.BuildConfig
import com.example.muvies.LANGUAGE
import com.example.muvies.models.OnAirTVResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class OnAirDataSource(private val apiService: MoviesApiService,
                      coroutineContext: CoroutineContext): PageKeyedDataSource<Int, OnAirTVResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, OnAirTVResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedOnAirTvList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch on air tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, OnAirTVResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedOnAirTvList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch on air tv shows! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, OnAirTVResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class OnAirDataSourceFactory(private val apiService: MoviesApiService,
                             coroutineContext: CoroutineContext): DataSource.Factory<Int, OnAirTVResult>() {

    val onAirDataSourceLiveData = MutableLiveData<OnAirDataSource>()

    override fun create(): DataSource<Int, OnAirTVResult> {
        val onAirDataSource = OnAirDataSource(apiService, Dispatchers.IO)
        onAirDataSourceLiveData.postValue(onAirDataSource)
        return onAirDataSource
    }

}