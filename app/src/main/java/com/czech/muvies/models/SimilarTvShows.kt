package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SimilarTvShows(
    val page: Int? = null,
    val results: List<SimilarTvShowsResult?>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
): Parcelable {

    @Parcelize
    data class SimilarTvShowsResult(
        @SerializedName("backdrop_path")
        val backdropPath: String? = null,
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
    ):Parcelable
}