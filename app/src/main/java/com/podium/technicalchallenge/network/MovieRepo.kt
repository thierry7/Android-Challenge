package com.podium.technicalchallenge.network
import androidx.lifecycle.LiveData
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepo {
    suspend fun getMovieList(): Response<MovieResponse>

    suspend fun getAllMovies(): List<LocalMovie>
    suspend fun getMovieById(id: Int): LocalMovie
    suspend fun getGenres(): List<String>
    suspend fun getBestFiveMoviesByRating(): List<LocalMovie>
    suspend fun getMoviesByGenre(genre: String): List<LocalMovie>
    suspend fun insertMovies(movies: List<Movie>)

}