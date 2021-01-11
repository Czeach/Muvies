package com.czech.muvies.database

import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import com.czech.muvies.R
import com.czech.muvies.models.MovieDetails
import com.czech.muvies.models.TvShowDetails
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Utilities {

    var movieGenres = HashMap<Int, String>()

    /**
     * This function takes the required fields of a [MovieDetails] and puts them into
     * a [ContentValues] object to use the [ContentProvider] to insert a
     * movie into the underlying Database ([DbHelper])
     *
     * @param movie a single MovieItem
     * @return a [ContentValues] object containing the required information to put the movie into the db
     */
    fun getContentValueForMovies(movie: MovieDetails, context: Context): ContentValues {

        return ContentValues().apply {
            put(Contract.MovieEntry.ID, movie.id)
            put(Contract.MovieEntry.TITLE, movie.title)
            put(Contract.MovieEntry.POSTER_PATH, movie.posterPath)
            put(Contract.MovieEntry.DESCRIPTION, movie.overview)
            val genreList = movie.genres
            var genreString: String? = ""
            for (i in genreList?.indices!!) {
                if (i != 0) {
                    genreString += ","
                }
                genreString += genreList[i]?.name
            }
            put(Contract.MovieEntry.GENRES, genreString)
            put(Contract.MovieEntry.RELEASE_DATE, movie.releaseDate)
            put(Contract.MovieEntry.VOTE_AVERAGE, movie.voteAverage)
        }
    }

    fun getContentValueForShows(show: TvShowDetails, context: Context): ContentValues {

        return ContentValues().apply {
            put(Contract.ShowEntry.ID, show.id)
            put(Contract.ShowEntry.TITLE, show.name)
            put(Contract.ShowEntry.POSTER_PATH, show.posterPath)
            put(Contract.ShowEntry.DESCRIPTION, show.overview)
            val genreList = show.genres
            var genreString: String? = ""
            for (i in genreList?.indices!!) {
                if (i != 0) {
                    genreString += ","
                }
                genreString += genreList[i]?.name
            }
            put(Contract.ShowEntry.GENRES, genreString)
            put(Contract.ShowEntry.FIRST_AIR_DATE, show.firstAirDate)
            put(Contract.ShowEntry.VOTE_AVERAGE, show.voteAverage)
        }
    }

    /**
    *Converts a date returned by the API into a different format.
     *
     *@param sourceDate a string representing a date in this format: 2015-12-15
     * @return returns a date in this format: December 15, 2015
    */

    fun convertDate(sourceDate: String?): String? {
        val sourceFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
        if (sourceDate == null || sourceDate == "") return null
        var date: Date? = null
        date = try {
            sourceFormat.parse(sourceDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e(ContentValues.TAG, "Error formatting the date")
            return null
        }
        return targetFormat.format(date)
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    private fun getDateDiff(date1: Date?, date2: Date, timeUnit: TimeUnit): Long {
        val diffInMillies = date2.time - date1!!.time
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }

    /**
     * Get the difference between a date and the current point in time in days
     * @param sourceDate the source date
     * @return the difference in days
     */

    fun computeDifferenceInDays(sourceDate: String?): Long {
        val sourceFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        if (sourceDate == null || sourceDate == "") return Long.MAX_VALUE
        var date: Date? = null
        date = try {
            sourceFormat.parse(sourceDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e(ContentValues.TAG, "Error formatting the date")
            return Long.MAX_VALUE
        }
        return getDateDiff(date, Date(System.currentTimeMillis()), TimeUnit.DAYS)
    }

    /**
     * Converts a date returned by the API into a different format.
     *
     * @param sourceDate a string representing a date in this format: 2015-12-15
     * @return a string representing a date in this format: 2015
     */
    fun convertDateToYear(sourceDate: String?): String? {
        val sourceFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val targetFormat: DateFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
        if (sourceDate == null || sourceDate == "") return null
        var date: Date? = null
        try {
            date = sourceFormat.parse(sourceDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e(ContentValues.TAG, "Error formatting the date")
        }
        return targetFormat.format(date)
    }

    /**
     * Checks if user is connected to a network to download data
     * @return true if user is connected to a network
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 180).toInt()
    }

    fun calculateNoOfColumnsShow(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 220).toInt()
    }

    fun buildMovieUri(movieId: Int): Uri {
        return Uri.withAppendedPath(Contract.MovieEntry.CONTENT_URI, movieId.toString())
    }

    fun buildShowUri(showId: Int): Uri {
        return Uri.withAppendedPath(Contract.ShowEntry.CONTENT_URI, showId.toString())
    }

    fun extractMovieGenres(genres: List<Int>?, mContext: Context): String? {
        movieGenres = HashMap()
        movieGenres[28] = "Action"
        movieGenres[12] = "Adventure"
        movieGenres[16] = "Animation"
        movieGenres[35] = "Comedy"
        movieGenres[80] = "Crime"
        movieGenres[99] = "Documentary"
        movieGenres[18] = "Drama"
        movieGenres[10751] = "Family"
        movieGenres[14] = "Fantasy"
        movieGenres[36] = "History"
        movieGenres[27] = "Horror"
        movieGenres[10402] = "Music"
        movieGenres[9648] = "Mystery"
        movieGenres[10749] = "Romance"
        movieGenres[878] = "Science Fiction"
        movieGenres[10770] = "TV Movie"
        movieGenres[53] = "Thriller"
        val separator = ", "
        if (genres == null || genres.isEmpty()) return null
        val result: MutableList<String?> = ArrayList()
        for (id in genres) {
            if (movieGenres.containsKey(id)) {
                result.add(movieGenres[id])
            } else {
                Log.d(ContentValues.TAG, "extractGenres: $id")
            }
        }
        val resBuilder = StringBuilder()
        for (item in result) {
            resBuilder.append(item)
            resBuilder.append(separator)
        }
        val list = resBuilder.toString()
        return if (list.isEmpty()) mContext.getString(R.string.genre_not_available)
        else list.substring(0, list.length - separator.length)
    }

//    fun getKnownFor(knownForList: List<KnownFor>?): String? {
//        if (knownForList == null) return null
//        var result: String? = ""
//        val iterSize = if (knownForList.size < 5) knownForList.size else 5
//        for (i in 0 until iterSize) {
//            if (knownForList[i].title == null) continue
//            result += knownForList[i].title
//            if (i != iterSize - 1) {
//                result += ", "
//            }
//        }
//        return result
//    }
}