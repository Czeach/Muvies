package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.SimilarTvShows
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SimilarTvShowsDataSource(private var apiService: MoviesApiService, coroutineContext: CoroutineContext, private var id: Int):
        PageKeyedDataSource<Int, SimilarTvShows.SimilarTvShowsResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SimilarTvShows.SimilarTvShowsResult>) {
        scope.launch {
            try {
                val response = apiService.getSimilarTvShows(id, BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        response.body()!!.results?.let {
                            callback.onResult(it,null, 1)
                        }
                    }
                }
            } catch (e : Exception){
                Log.d("SimilarTvShowDataSource", "Failed to fetch similar tv shows!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>,
        callback: LoadCallback<Int, SimilarTvShows.SimilarTvShowsResult>) {
        scope.launch {
            try {
                val response = apiService.getSimilarTvShows(id, BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        response.body()!!.results?.let {
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            } catch (e : Exception){
                Log.d("SimilarTvShowDataSource", "Failed to fetch similar tv shows!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>,
        callback: LoadCallback<Int, SimilarTvShows.SimilarTvShowsResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class SimilarTvShowsDataSourceFactory(private val apiService: MoviesApiService, coroutineContext: CoroutineContext, private var id: Int):
        DataSource.Factory<Int, SimilarTvShows.SimilarTvShowsResult>() {

    val similarTvShowsDataSourceLiveData = MutableLiveData<SimilarTvShowsDataSource>()

    override fun create(): DataSource<Int, SimilarTvShows.SimilarTvShowsResult> {
        val similarTvShowsDataSource = SimilarTvShowsDataSource(apiService, Dispatchers.IO, id)
        similarTvShowsDataSourceLiveData.postValue(similarTvShowsDataSource)
        return similarTvShowsDataSource
    }

}