package com.podium.technicalchallenge.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
@Serializable
data class MovieResponse(
    val data: MovieData
)
@Serializable
data class MovieData(
    val movies: List<Movie>,
    val movie: Movie
)
@Serializable
data class Movie(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val title: String,
    val runtime: Int,
    val genres: List<String>,
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

@Serializable
data class GenresResponse(
    val data: GenresData
)

@Serializable
data class GenresData(
    val genres: List<String>
)
@Entity(
    tableName = "movie"
)
data class LocalMovie(
   @PrimaryKey val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val title: String,
    val runtime: Int,
    val genres: List<String>,
    val director: Director
)


