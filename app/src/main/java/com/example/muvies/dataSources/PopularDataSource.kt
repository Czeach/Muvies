package com.example.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.muvies.BuildConfig
import com.example.muvies.LANGUAGE
import com.example.muvies.models.PopularResult
import com.example.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class PopularDataSource(private val apiService: MoviesApiService,
    coroutineContext: CoroutineContext): PageKeyedDataSource<Int, PopularResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PopularResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedPopularList(BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch popular movies! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PopularResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedPopularList(BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch popular movies! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PopularResult>) {

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class PopularDataSourceFactory(private val apiService: MoviesApiService,
    coroutineContext: CoroutineContext): DataSource.Factory<Int, PopularResult>() {

    val popularDataSourceLiveData = MutableLiveData<PopularDataSource>()

    override fun create(): DataSource<Int, PopularResult> {
        val popularDataSource = PopularDataSource(apiService, Dispatchers.IO)
        popularDataSourceLiveData.postValue(popularDataSource)
        return popularDataSource
    }

}