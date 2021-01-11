package com.czech.muvies.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

internal class DbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        // name and version of the database
        private const val DATABASE_NAME = "favorites.db"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createMovieTable = "CREATE TABLE ${Contract.MovieEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY" +
                "${Contract.MovieEntry.ID} INTEGER NOT NULL" +
                "${Contract.MovieEntry.TITLE} TEXT NOT NULL" +
                "${Contract.MovieEntry.POSTER_PATH} TEXT NOT NULL" +
                "${Contract.MovieEntry.DESCRIPTION} TEXT NOT NULL" +
                "${Contract.MovieEntry.GENRES} TEXT, " +
                "${Contract.MovieEntry.RELEASE_DATE} TEXT NOT NULL" +
                "${Contract.MovieEntry.VOTE_AVERAGE} REAL NOT NULL)"
        db?.execSQL(createMovieTable)

        val createShowTable = "CREATE TABLE ${Contract.ShowEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY" +
                "${Contract.ShowEntry.ID} INTEGER NOT NULL" +
                "${Contract.ShowEntry.TITLE} TEXT NOT NULL" +
                "${Contract.ShowEntry.POSTER_PATH} TEXT NOT NULL" +
                "${Contract.ShowEntry.DESCRIPTION} TEXT NOT NULL" +
                "${Contract.ShowEntry.GENRES} TEXT, " +
                "${Contract.ShowEntry.FIRST_AIR_DATE} TEXT NOT NULL" +
                "${Contract.ShowEntry.VOTE_AVERAGE} REAL NOT NULL)"
        db?.execSQL(createShowTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS" + Contract.MovieEntry.TABLE_NAME)
        db?.execSQL("DROP TABLE IF EXISTS" + Contract.ShowEntry.TABLE_NAME)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)

        onDowngrade(db, oldVersion, newVersion)
    }
}