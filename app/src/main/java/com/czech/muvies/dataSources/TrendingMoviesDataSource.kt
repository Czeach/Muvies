package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.models.TrendingMoviesResult
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TrendingMoviesDataSource(private val apiService: MoviesApiService,
                               coroutineContext: CoroutineContext): PageKeyedDataSource<Int, TrendingMoviesResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TrendingMoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedTrendingMoviesList(BuildConfig.API_KEY, 1)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, null, 2)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch trending movies! cause: ${e.message}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TrendingMoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getPagedTrendingMoviesList(BuildConfig.API_KEY, params.key)
                when {
                    response.isSuccessful -> {
                        callback.onResult(response.body()!!.results, params.key + 1)
                    }
                }
            } catch (e : Exception){
                Log.d("MoviesDataSource", "Failed to fetch trending movies! cause: ${e.message}")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TrendingMoviesResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }

}

class TrendingMoviesDataSourceFactory(private val apiService: MoviesApiService, coroutineContext: CoroutineContext):
    DataSource.Factory<Int, TrendingMoviesResult>() {

    val trendingMoviesDataSourceLiveData = MutableLiveData<TrendingMoviesDataSource>()

    override fun create(): DataSource<Int, TrendingMoviesResult> {
        val trendingMoviesDataSource = TrendingMoviesDataSource(apiService, Dispatchers.IO)
        trendingMoviesDataSourceLiveData.postValue(trendingMoviesDataSource)
        return trendingMoviesDataSource
    }

}