package com.czech.muvies.room.movies

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM fav_movies")
    fun getFavMovies(): Flow<List<MoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MoviesEntity)

    @Delete
    suspend fun deleteMovie(movie: MoviesEntity)

}