package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCredits(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
): Parcelable {

    @Parcelize
    data class Cast(
        @SerializedName("cast_id")
        val castId: Int? = null,
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
        val profilePath: String? = null
    ): Parcelable
}