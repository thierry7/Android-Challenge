package com.podium.technicalchallenge
import com.podium.technicalchallenge.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepo {

    suspend fun getMovieStream(): Flow<List<Movie>>

    suspend fun getMovieStream(movieId: Int): Flow<Movie>


    suspend fun getGenres(): Flow<List<String>>


    suspend fun getMoviesByGenre(genreName: String): List<Movie>



}