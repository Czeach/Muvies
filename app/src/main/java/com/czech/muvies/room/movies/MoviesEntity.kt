package com.czech.muvies.room.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movies")
data class MoviesEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val title: String?,
    val overview: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val originalLanguage: String?
)