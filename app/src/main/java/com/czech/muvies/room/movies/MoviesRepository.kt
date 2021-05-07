package com.czech.muvies.room.movies

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val moviesDao: MoviesDao) {

    val allMovies: Flow<List<MoviesEntity>> = moviesDao.getFavMovies()

    @WorkerThread
    suspend fun insert(movie: MoviesEntity) {
        moviesDao.insertMovie(movie)
    }
}