package com.czech.muvies.database

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.net.Uri
import java.lang.UnsupportedOperationException

class ContentProvider: ContentProvider() {

    private var dbHelper: DbHelper? = null

    override fun onCreate(): Boolean {
        dbHelper = DbHelper(requireContext())

        return true
    }

    /**
     * Query the items in the favorite movies database. This operation will be used in the
     * to display a list of the user's favorite movies. Hence, for now, we just
     * need to return all the items in the table
     * @param uri to specify action
     * @param projection columns to be returned
     * @param selection filter
     * @param selectionArgs filter arguments
     * @param sortOrder not necessary
     * @return Cursor, pointing to the queried result
     */
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor? {
        val db = dbHelper!!.writableDatabase
        val result: Cursor
        result = when (uriMatcher.match(uri)) {
            MOVIES -> {
                val movieId = uri.pathSegments[1]
                db.query(
                    Contract.MovieEntry.TABLE_NAME,
                    projection,
                    Contract.MovieEntry.ID + "=?",
                    arrayOf(movieId),
                    null,
                    null,
                    null
                )
            }
            SHOWS -> {
                val showId = uri.pathSegments[1]
                db.query(
                    Contract.ShowEntry.TABLE_NAME,
                    projection,
                    Contract.ShowEntry.ID + "=?",
                    arrayOf(showId),
                    null,
                    null,
                    null
                )
            }
            else -> throw UnsupportedOperationException("Unsupported operation for: $uri")
        }
        result.setNotificationUri(requireContext().contentResolver, uri)
        return result

    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    /**
     * Used to insert a movie into the database of favorite movies. Since this operation can only
     * be performed on a single movie by clicking the button in
     * [DetailActivity] we only need to implement functionality to insert
     * one item at a time
     *
     * @param uri to specify action
     * @param values key-value pairs for the data to be inserted
     * @return Uri of item inserted
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper!!.writableDatabase
        val returnUri: Uri
        returnUri = when (uriMatcher.match(uri)) {
            MOVIES -> {
                val movieId = db.insert(
                    Contract.MovieEntry.TABLE_NAME,
                    null,
                    values)
                if (movieId > 0) {
                    ContentUris.withAppendedId(Contract.MovieEntry.CONTENT_URI, movieId)
                } else {
                    throw SQLiteException("Failed to insert row into $uri")
                }
            }
            SHOWS -> {
                val showId = db.insert(
                    Contract.ShowEntry.TABLE_NAME,
                    null,
                    values)
                if (showId > 0) {
                    ContentUris.withAppendedId(Contract.ShowEntry.CONTENT_URI, showId)
                } else {
                    throw  SQLiteException("Failed to insert row into $uri")
                }
            }
            else -> throw UnsupportedOperationException("Unsupported Operation for: $uri")
        }
        return returnUri
    }

    /**
     * Used to delete a movie from the favorites when the corresponding button is clicked in
     * [DetailActivity]. Since we will only be deleting one movie
     * at a time
     * @param uri to specify operation
     * @param selection filter
     * @param selectionArgs filter selection
     * @return number of rows deleted
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val noOfRowsDeleted: Int
        val db = dbHelper!!.writableDatabase
        noOfRowsDeleted = when (uriMatcher.match(uri)) {
            MOVIES -> {
                val movieId = uri.pathSegments[1]
                db.delete(
                    Contract.MovieEntry.TABLE_NAME,
                    Contract.MovieEntry.ID + "=?",
                    arrayOf(movieId)
                )
            }
            SHOWS -> {
                val showId = uri.pathSegments[1]
                db.delete(
                    Contract.ShowEntry.TABLE_NAME,
                    Contract.ShowEntry.ID + "=?",
                    arrayOf(showId)
                )
            }
            else -> throw UnsupportedOperationException("Unknown uri: $uri")
        }

        // Notify the resolver of a change and return the number of items deleted
        if (noOfRowsDeleted != 0) {
            // a task was deleted set notification
            try {
                requireContext().contentResolver.notifyChange(uri, null)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
        return noOfRowsDeleted
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return  0
    }

    companion object {
        const val MOVIES = 101
        private const val SHOWS = 671
        private val uriMatcher = buildUriMatcher()

        /**
         * This function builds the URI matcher
         * @return [UriMatcher] which matches Content Uri's to the corresponding tasks
         */
        private fun buildUriMatcher(): UriMatcher {

            // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_FAVORITE_MOVIES, MOVIES)
            uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_FAVORITE_SHOWS, SHOWS)

            return uriMatcher
        }
    }
}