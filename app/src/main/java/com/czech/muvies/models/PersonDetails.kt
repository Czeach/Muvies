package com.czech.muvies.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class PersonDetails(
    val adult: Boolean? = null,
    @SerializedName("also_known_as")
    val alsoKnownAs: List<String?>? = null,
    val biography: String? = null,
    val birthday: String? = null,
    val deathday: @RawValue Any? = null,
    val gender: Int? = null,
    val homepage: @RawValue Any? = null,
    val id: Int? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("known_for_department")
    val knownForDepartment: String? = null,
    val name: String? = null,
    @SerializedName("place_of_birth")
    val placeOfBirth: String? = null,
    val popularity: Double? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
): Parcelable