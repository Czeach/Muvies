package com.czech.muvies.database

import android.net.Uri
import android.provider.BaseColumns

object Contract {

    // Content Authority and Base URI to query data
    const val CONTENT_AUTHORITY = "com.czech.muvies"
    private val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")
    const val PATH_FAVORITE_MOVIES = "fav_movies"
    const val PATH_FAVORITE_SHOWS = "fav_shows"

    object MovieEntry: BaseColumns {

        // The content uri is the base uri with the path appended
        // This will be used to query the favorite movies
        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build()
        const val TABLE_NAME = "fav_movies"

        /*
        We create columns to hold the following information for a movie:

        - ID (int)
        - Title (String)
        - PosterPath (String)
        - Description (String)
        - Genres (List)
        - Release Date (String)
        - Vote Average (Double)

        */

        const val ID = "movieID"
        const val TITLE = "movieTitle"
        const val POSTER_PATH = "moviePosterPath"
        const val DESCRIPTION = "movieDescription"
        const val GENRES = "movieGenres"
        const val RELEASE_DATE = "movieReleaseDate"
        const val VOTE_AVERAGE = "movieVoteAverage"
    }

    object ShowEntry: BaseColumns {

        val CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_SHOWS).build()
        const val TABLE_NAME = "fav_shows"

        const val ID = "showID"
        const val TITLE = "showTitle"
        const val POSTER_PATH = "showPosterPath"
        const val DESCRIPTION = "showDescription"
        const val GENRES = "showGenres"
        const val FIRST_AIR_DATE = "showReleaseDate"
        const val VOTE_AVERAGE = "showVoteAverage"
    }
}