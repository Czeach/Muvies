package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PersonMovies(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
): Parcelable {

    @Parcelize
    data class Cast(
        val adult: Boolean? = null,
        @SerializedName("backdrop_path")
        val backdropPath: @RawValue Any? = null,
        val character: String? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        @SerializedName("genre_ids")
        val genreIds: List<Int?>? = null,
        val id: Int? = null,
        @SerializedName("original_language")
        val originalLanguage: String? = null,
        @SerializedName("original_title")
        val originalTitle: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    ): Parcelable

    @Parcelize
    data class Crew(
        val adult: Boolean? = null,
        @SerializedName("backdrop_path")
        val backdropPath: @RawValue Any? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        val department: String? = null,
        @SerializedName("genre_ids")
        val genreIds: List<Int?>? = null,
        val id: Int? = null,
        val job: String? = null,
        @SerializedName("original_language")
        val originalLanguage: String? = null,
        @SerializedName("original_title")
        val originalTitle: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        @SerializedName("poster_path")
        val posterPath: @RawValue Any? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    ): Parcelable
}