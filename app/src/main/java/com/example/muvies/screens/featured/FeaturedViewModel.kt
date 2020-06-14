package com.example.muvies.screens.featured

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.*
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class FeaturedViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: MoviesRepository = MoviesRepository(MoviesApi.retrofitService)

    init {
        getDiscover()
    }

    private var _discoverLiveData = MutableLiveData<MutableList<DiscoverResult>>()

    val discoverLiveData: LiveData<MutableList<DiscoverResult>>
        get() = _discoverLiveData

    private fun getDiscover() {
        coroutineScope.launch {
            val discover = repository.getDiscover()
            try {
                _discoverLiveData.value = discover
            } catch (e: Exception) {
                _response.value = "Failure " + e.message
            }
        }
    }
}
