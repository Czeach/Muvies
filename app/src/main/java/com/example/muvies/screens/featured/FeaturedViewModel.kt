package com.example.muvies.screens.featured

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.BuildConfig
import com.example.muvies.model.UpcomingMovies
import com.example.muvies.network.MoviesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import java.lang.Exception

class FeaturedViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private val _upcoming = MutableLiveData<ArrayList<UpcomingMovies>>()

    val upcoming: LiveData<ArrayList<UpcomingMovies>>
    get() = _upcoming

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getUpcomingList()
    }

    private fun getUpcomingList() {
        coroutineScope.launch {
            val upcomingList = MoviesApi.retrofitService.getUpcomingMovies(BuildConfig.API_KEY, "en-US", 1).await()
            try {
                _upcoming.value = upcomingList
                Log.v("Upcoming", "Network call successful")
            }catch (t: Throwable) {
                _response.value = "Failure: " + t.message
                Log.v("Upcoming", "Network call unsuccessful")
            }
        }
    }
}
