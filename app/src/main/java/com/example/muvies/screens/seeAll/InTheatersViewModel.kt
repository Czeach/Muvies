package com.example.muvies.screens.seeAll

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muvies.model.InTheatersResult
import com.example.muvies.network.MoviesApi
import com.example.muvies.network.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class InTheatersViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val repository: MoviesRepository = MoviesRepository(MoviesApi.retrofitService)

    init {

    }

    private var _inTheatersLiveData = MutableLiveData<MutableList<InTheatersResult>>()

    val inTheatersLiveData: LiveData<MutableList<InTheatersResult>>
        get() = _inTheatersLiveData
}