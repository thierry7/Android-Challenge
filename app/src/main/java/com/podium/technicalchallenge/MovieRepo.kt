package com.podium.technicalchallenge
import com.podium.technicalchallenge.entity.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepo {

    suspend fun getMovieStream(): Flow<List<Movies>>

    suspend fun getMovieStream(movieId: Int): Flow<Movies>


    suspend fun getMovie(movieName: String): Movies


    suspend fun getMoviesByGenre(genreName: String): List<Movies>



}