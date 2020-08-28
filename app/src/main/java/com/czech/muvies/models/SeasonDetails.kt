package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SeasonDetails(
    @SerializedName("air_date")
    val airDate: String? = null,
    val episodes: List<Episode?>? = null,
    @SerializedName("_id")
    val _id: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val overview: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("season_number")
    val seasonNumber: Int? = null
): Parcelable {

    @Parcelize
    data class Episode(
        @SerializedName("air_date")
        val airDate: String? = null,
        val crew: List<Crew?>? = null,
        @SerializedName("episode_number")
        val episodeNumber: Int? = null,
        @SerializedName("guest_stars")
        val guestStars: List<GuestStar?>? = null,
        val id: Int? = null,
        val name: String? = null,
        val overview: String? = null,
        @SerializedName("production_code")
        val productionCode: String? = null,
        @SerializedName("season_number")
        val seasonNumber: Int? = null,
        @SerializedName("show_id")
        val showId: Int? = null,
        @SerializedName("still_path")
        val stillPath: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    ): Parcelable {

        @Parcelize
        data class Crew(
            @SerializedName("credit_id")
            val creditId: String? = null,
            val department: String? = null,
            val gender: Int? = null,
            val id: Int? = null,
            val job: String? = null,
            val name: String? = null,
            @SerializedName("profile_path")
            val profilePath: @RawValue Any? = null
        ): Parcelable

        @Parcelize
        data class GuestStar(
            val character: String? = null,
            @SerializedName("credit_id")
            val creditId: String? = null,
            val gender: Int? = null,
            val id: Int? = null,
            val name: String? = null,
            val order: Int? = null,
            @SerializedName("profile_path")
            val profilePath: String? = null
        ): Parcelable
    }
}