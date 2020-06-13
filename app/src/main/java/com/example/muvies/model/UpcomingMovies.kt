package com.example.muvies.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpcomingMovies(
    val results: List<UpcomingResult?>?,
    val page: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    val dates: UpcomingDates?,
    @SerializedName("total_pages")
    val totalPages: Int?
): Parcelable

@Parcelize
data class UpcomingResult(
    val popularity: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    val video: Boolean?,
    @SerializedName("poster_path")
    val posterPath: String?,
    val id: Int?,
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>?,
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    val overview: String?,
    @SerializedName("release_date")
    val releaseDate: String?
): Parcelable

@Parcelize
data class UpcomingDates(
    val maximum: String?,
    val minimum: String?
): Parcelable
