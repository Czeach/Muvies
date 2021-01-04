package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PersonTvShows(
    val cast: List<Cast?>? = null,
    val crew: @RawValue List<Any?>? = null,
    val id: Int? = null
): Parcelable {

    @Parcelize
    data class Cast(
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
        val character: String? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        @SerializedName("episode_count")
        val episodeCount: Int? = null,
        @SerializedName("first_air_date")
        val firstAirDate: String? = null,
        @SerializedName("genre_ids")
        val genreIds: List<Int?>? = null,
        val id: Int? = null,
        val name: String? = null,
        @SerializedName("origin_country")
        val originCountry: List<String?>? = null,
        @SerializedName("original_language")
        val originalLanguage: String? = null,
        @SerializedName("original_name")
        val originalName: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    ): Parcelable
}