package com.example.muvies.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PopularTV(
    val page: Int = 0,
    val results: List<PopularTVResult> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
): Parcelable

@Parcelize
data class PopularTVResult(
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("first_air_date")
    val firstAirDate: String = "",
    @SerializedName("genre_ids")
    val genreIds: List<Int> = listOf(),
    val id: Int = 0,
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: List<String> = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("original_name")
    val originalName: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int = 0
): Parcelable