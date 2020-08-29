package com.czech.muvies.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.bumptech.glide.load.engine.Resource
import com.czech.muvies.BuildConfig
import com.czech.muvies.LANGUAGE
import com.czech.muvies.network.MoviesApiService
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import java.lang.Exception

class CastDetailsViewModel(private val apiService: MoviesApiService) : ViewModel() {

    fun getCastDetails(personId: Int) = liveData(Dispatchers.IO) {
        emit(com.czech.muvies.utils.Resource.loading(data = null))
        try {
            emit(com.czech.muvies.utils.Resource.success(data = apiService.getCastDetails(personId, BuildConfig.API_KEY, LANGUAGE)))
        } catch (e: IOException) {
            emit(com.czech.muvies.utils.Resource.error(data = null, message = e.message ?: "Error getting cast details"))
        }
    }
}

class CastDetailsViewModelFactory(private val apiService: MoviesApiService): ViewModelProvider.Factory {

    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CastDetailsViewModel::class.java)) {
            return CastDetailsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}