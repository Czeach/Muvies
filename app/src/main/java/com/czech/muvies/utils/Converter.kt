package com.czech.muvies.utils

import android.content.ContentValues
import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Converter {

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

    fun convertTime(sourceTime: Int): String? {

        val hrs = (sourceTime / 60)
        val mins = sourceTime - (hrs * 60)

        return when (hrs) {
            1 -> {
                ("${hrs}hr ${mins}mins")
            }
            0 -> {
                ("${mins}mins")
            }
            else -> {
                ("${hrs}hrs ${mins}mins")
            }
        }
    }
}