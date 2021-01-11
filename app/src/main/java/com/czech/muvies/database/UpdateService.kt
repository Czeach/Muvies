package com.czech.muvies.database

import android.app.IntentService
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class UpdateService: IntentService(TAG) {

    companion object {
        private const val TAG = "UpdateService"

        // Intent actions
        const val ACTION_INSERT_MOVIE = "$TAG.INSERT_MOVIE"
        const val ACTION_INSERT_SHOW = "$TAG.INSERT_SHOW"
        const val ACTION_DELETE = "$TAG.DELETE"
        const val EXTRA_VALUES = "$TAG.ContentValues"

        fun insertMovie(context: Context, values: ContentValues?) {
            val intent = Intent(context, UpdateService::class.java)
            intent.action = ACTION_INSERT_MOVIE
            intent.putExtra(EXTRA_VALUES, values)
            context.startService(intent)
        }

        fun insertShow(context: Context, values: ContentValues?) {
            val intent = Intent(context, UpdateService::class.java)
            intent.action = ACTION_INSERT_SHOW
            intent.putExtra(EXTRA_VALUES, values)
            context.startService(intent)
        }

        fun deleteItem(context: Context, uri: Uri?) {
            val intent = Intent(context, UpdateService::class.java)
            intent.action = ACTION_DELETE
            intent.data = uri
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent!!.action) {
            ACTION_INSERT_MOVIE -> {
                val values = intent.getParcelableExtra<ContentValues>(EXTRA_VALUES)
                performInsertMovie(values)
            }
            ACTION_INSERT_SHOW -> {
                val values = intent.getParcelableExtra<ContentValues>(EXTRA_VALUES)
                performInsertShow(values)
            }
            ACTION_DELETE -> {
                intent.data?.let { performDelete(it) }
            }
        }
    }

    fun performInsertMovie(values: ContentValues?) {
        if (contentResolver.insert(Contract.MovieEntry.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted movie")
        } else {
            Log.d(TAG, "Error inserting movie")
        }
    }

    fun performInsertShow(values: ContentValues?) {
        if (contentResolver.insert(Contract.ShowEntry.CONTENT_URI, values) != null) {
            Log.d(TAG, "Inserted show")
        } else {
            Log.d(TAG, "Error inserting show")
        }
    }

    private fun performDelete(uri: Uri) {
        val count = contentResolver.delete(uri, null, null)
        Log.d(TAG, "Deleted $count movies/shows")
    }
}