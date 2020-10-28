package com.czech.muvies.models


import com.google.gson.annotations.SerializedName

data class PersonMovies(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
) {
    data class Cast(
        val adult: Boolean? = null,
        @SerializedName("backdrop_path")
        val backdropPath: Any? = null,
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
    )

    data class Crew(
        val adult: Boolean? = null,
        @SerializedName("backdrop_path")
        val backdropPath: Any? = null,
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
        val posterPath: Any? = null,
        @SerializedName("release_date")
        val releaseDate: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )
}