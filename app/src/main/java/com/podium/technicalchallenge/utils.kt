package com.podium.technicalchallenge

import androidx.lifecycle.LiveData
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.podium.technicalchallenge.entity.Cast
import com.podium.technicalchallenge.entity.Director
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.entity.Movie


class GenresConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}

class CastListConverter {
    @TypeConverter
    fun fromString(value: String): List<Cast> {
        val listType = object : TypeToken<List<Cast>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Cast>): String {
        return Gson().toJson(list)
    }
}
class DirectorConverter {
    @TypeConverter
    fun fromJson(json: String): Director {
        return Gson().fromJson(json, Director::class.java)
    }

    @TypeConverter
    fun toJson(director: Director): String {
        return Gson().toJson(director)
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
    genres = genres
)
fun LocalMovie.toNetwork() = Movie(
    id = id,
    overview = overview,
    posterPath = posterPath ,
    releaseDate = releaseDate ,
    voteAverage = voteAverage,
    title = title,
    runtime= runtime,
    genres = genres
)
@JvmName("localToExternal")
fun List<LocalMovie>.toNetworkMovies(): List<Movie> {
    return map { it.toNetwork() }
}

@JvmName("ExternalToLocal")
fun List<Movie>.toLocal() = map(Movie::toLocal)

fun LiveData<LocalMovie?>.toLiveMovie(): LiveData<Movie> {
    return object : LiveData<Movie>() {
        private var initialized = false

        override fun onActive() {
            super.onActive()
            if (!initialized) {
                initialized = true
                observeForever { localMovie ->
                    value = localMovie // Convert LocalMovie to Movie
                }
            }
        }
    }
}
