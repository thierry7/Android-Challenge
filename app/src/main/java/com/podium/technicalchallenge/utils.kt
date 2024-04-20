package com.podium.technicalchallenge

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.podium.technicalchallenge.entity.Cast
import com.podium.technicalchallenge.entity.Director
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.entity.Movie

class Converters {
    @TypeConverter
    fun fromCastToString(cast: Cast): String {
        // Convert Cast object to JSON string
        return Gson().toJson(cast)
    }

    @TypeConverter
    fun fromStringToCast(json: String): Cast {
        // Convert JSON string to Cast object
        return Gson().fromJson(json, Cast::class.java)
    }

    @TypeConverter
    fun fromDirectorToString(director: Director): String {
        // Convert Director object to JSON string
        return Gson().toJson(director)
    }

    @TypeConverter
    fun fromStringToDirector(json: String): Director {
        // Convert JSON string to Director object
        return Gson().fromJson(json, Director::class.java)
    }
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}


fun Movie.toLocal() = LocalMovie(

    id = id,
    overview = overview,
    posterPath = posterPath ,
    releaseDate = releaseDate ,
    voteAverage = voteAverage,
    title = title,
    runtime= runtime,
    genres = genres,
    director = director
)

@JvmName("ExternalToLocal")
fun List<Movie>.toLocal() = map(Movie::toLocal)

