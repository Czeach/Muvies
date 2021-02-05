package com.czech.muvies.viewModels

import androidx.lifecycle.*
import com.czech.muvies.room.movies.MoviesEntity
import com.czech.muvies.room.movies.MoviesRepository
import kotlinx.coroutines.launch

class FavMoviesTabViewModel(private val repository: MoviesRepository): ViewModel() {

    val allMovies: LiveData<List<MoviesEntity>> = repository.allMovies.asLiveData()

    fun insert(movie: MoviesEntity) = viewModelScope.launch {
        repository.insert(movie)
    }

}

class FavMoviesTabViewModelFactory(private val repository: MoviesRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavMoviesTabViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return FavMoviesTabViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}