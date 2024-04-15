package com.podium.technicalchallenge.entity

import kotlinx.serialization.Serializable
@Serializable
data class MovieResponse(
    val data: MovieData
)
@Serializable
data class MovieData(

    val movies: List<Movies>,
    val movie: Movies
)
@Serializable
data class Movies(
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val title: String,
    val budget: Int,
    val runtime: Int,
    val genres: List<String>,
    val cast: List<Cast>,
    val director: Director
)
@Serializable
data class Cast(
    val name: String,
    val character: String
)
@Serializable
data class Director(
    val id: Int,
    val name: String
)