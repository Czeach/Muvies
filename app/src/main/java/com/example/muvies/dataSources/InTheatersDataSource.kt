package com.example.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.muvies.BuildConfig
import com.example.muvies.LANGUAGE
import com.example.muvies.models.InTheatersResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class InTheatersDataSource(private var apiService: MoviesApiService,
                           coroutineContext: CoroutineContext): PageKeyedDataSource<Int, InTheatersResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, InTheatersResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedInTheatersList(BuildConfig.API_KEY, LANGUAGE, 1)
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, InTheatersResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedInTheatersList(BuildConfig.API_KEY, LANGUAGE, params.key)
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, InTheatersResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class InTheatersDataSourceFactory(private val apiService: MoviesApiService,
    coroutineContext: CoroutineContext): DataSource.Factory<Int, InTheatersResult>() {

    val inTheatersDataSourceLiveData = MutableLiveData<InTheatersDataSource>()

    override fun create(): DataSource<Int, InTheatersResult> {
        val inTheatersDataSource = InTheatersDataSource(apiService, Dispatchers.IO)
        inTheatersDataSourceLiveData.postValue(inTheatersDataSource)
        return inTheatersDataSource
    }

}