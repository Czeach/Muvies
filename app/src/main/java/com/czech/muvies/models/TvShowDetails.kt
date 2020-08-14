package com.czech.muvies.models


import com.google.gson.annotations.SerializedName

data class TvShowDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("created_by")
    val createdBy: List<CreatedBy?>? = null,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int?>? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    val genres: List<Genre?>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    @SerializedName("in_production")
    val inProduction: Boolean? = null,
    val languages: List<String?>? = null,
    @SerializedName("last_air_date")
    val lastAirDate: String? = null,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    val name: String? = null,
    val networks: List<Network?>? = null,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir? = null,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,
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
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,
    val seasons: List<Season?>? = null,
    val status: String? = null,
    val type: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) {
    data class CreatedBy(
        @SerializedName("credit_id")
        val creditId: String? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val name: String? = null,
        @SerializedName("profile_path")
        val profilePath: Any? = null
    )

    data class Genre(
        val id: Int? = null,
        val name: String? = null
    )

    data class LastEpisodeToAir(
        @SerializedName("air_date")
        val airDate: String? = null,
        @SerializedName("episode_number")
        val episodeNumber: Int? = null,
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
        val stillPath: Any? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )

    data class Network(
        val id: Int? = null,
        @SerializedName("logo_path")
        val logoPath: String? = null,
        val name: String? = null,
        @SerializedName("origin_country")
        val originCountry: String? = null
    )

    data class NextEpisodeToAir(
        @SerializedName("air_date")
        val airDate: String? = null,
        @SerializedName("episode_number")
        val episodeNumber: Int? = null,
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
        val stillPath: Any? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )

    data class ProductionCompany(
        val id: Int? = null,
        @SerializedName("logo_path")
        val logoPath: String? = null,
        val name: String? = null,
        @SerializedName("origin_country")
        val originCountry: String? = null
    )

    data class Season(
        @SerializedName("air_date")
        val airDate: String? = null,
        @SerializedName("episode_count")
        val episodeCount: Int? = null,
        val id: Int? = null,
        val name: String? = null,
        val overview: String? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("season_number")
        val seasonNumber: Int? = null
    )
}