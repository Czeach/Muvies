package com.example.muvies.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingMovies(
    val results: List<Result?>? = null,
    val page: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    val dates: Dates? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null
): Parcelable {
    @Parcelize
    data class Result(
        val popularity: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null,
        val video: Boolean? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        val id: Int? = null,
        val adult: Boolean? = null,
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        @SerializedName("original_language")
        val originalLanguage: String? = null,
        @SerializedName("original_title")
        val originalTitle: String? = null,
        @SerializedName("genre_ids")
        val genreIds: List<Int?>? = null,
        val title: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        val overview: String? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null
    ): Parcelable

    @Parcelize
    data class Dates(
        val maximum: String? = null,
        val minimum: String? = null
    ): Parcelable
}