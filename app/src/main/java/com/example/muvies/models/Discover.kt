package com.example.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Discover(
    val page: Int = 0,
    val results: List<DiscoverResult> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
): Parcelable

@Parcelize
    data class DiscoverResult(
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    val backdropPath: @RawValue Any = Any(),
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    val id: Int = 0,
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Int = 0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
    ): Parcelable