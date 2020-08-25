package com.czech.muvies.dataSources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.models.SimilarMovies
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SimilarMoviesDataSource (private var apiService: MoviesApiService, coroutineContext: CoroutineContext, private var id: Int):
    PageKeyedDataSource<Int, SimilarMovies.SimilarMoviesResult>() {

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)
//    private var id: Int = 0

    override fun loadInitial(params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SimilarMovies.SimilarMoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getSimilarMovies(id, BuildConfig.API_KEY, LANGUAGE, 1)
                when {
                    response.isSuccessful -> {
                        response.body()!!.results?.let {
                            callback.onResult(it, null, 2)
                        }
                    }
                }
            } catch (e : Exception){
                Log.d("SimilarMoviesDataSource", "Failed to fetch similar movies!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>,
        callback: LoadCallback<Int, SimilarMovies.SimilarMoviesResult>) {
        scope.launch {
            try {
                val response = apiService.getSimilarMovies(id, BuildConfig.API_KEY, LANGUAGE, params.key)
                when {
                    response.isSuccessful -> {
                        response.body()!!.results?.let {
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            } catch (e : Exception){
                Log.d("SimilarMoviesDataSource", "Failed to fetch similar movies!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>,
        callback: LoadCallback<Int, SimilarMovies.SimilarMoviesResult>) {
    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}

class SimilarMoviesDataSourceFactory(private val apiService: MoviesApiService,
                                     coroutineContext: CoroutineContext, private var id: Int): DataSource.Factory<Int, SimilarMovies.SimilarMoviesResult>() {

    val similarMoviesDataSourceLiveData = MutableLiveData<SimilarMoviesDataSource>()

    override fun create(): DataSource<Int, SimilarMovies.SimilarMoviesResult> {
        val similarMoviesDataSource = SimilarMoviesDataSource(apiService, Dispatchers.IO, id)
        similarMoviesDataSourceLiveData.postValue(similarMoviesDataSource)
        return similarMoviesDataSource
    }

}
