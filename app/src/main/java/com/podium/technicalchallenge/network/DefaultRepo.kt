package com.podium.technicalchallenge.network

import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.local.MovieDao
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import com.podium.technicalchallenge.toLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class DefaultRepo @Inject constructor(
    private val retrofit: Retrofit,
    private val movieDao: MovieDao
): MovieRepo {

   var movieList : List<Movie> = emptyList()
    init{
        runBlocking {
            movieList = getMovieList().body()?.data?.movies!!
            insertMovies(movieList)
        }

    }

    override suspend fun getMovieList(): Response<MovieResponse> {
        val requestBody =
            "{\"query\":\"${Queries.getMoviesQuery()}\"}"
                .toRequestBody(
                    "application/json"
                        .toMediaTypeOrNull()
                )
        return retrofit
            .create(GraphQLService::class.java)
            .queryListMovies(requestBody)
    }

    override suspend fun getAllMovies(): List<LocalMovie> {
       return withContext(Dispatchers.IO){
           movieDao.getAllMovies()
       }
    }

    override suspend fun getMovieById(id: Int): LocalMovie {

        return withContext(Dispatchers.IO){
            println("Movie LiveData id $id : ${movieDao.getMovieById(id)}")
            movieDao.getMovieById(id)

        }

    }

    override suspend fun getGenres(): List<String> {
        return withContext(Dispatchers.IO){
            movieDao.getGenres()
        }
    }

    override suspend fun getBestFiveMoviesByRating(): List<LocalMovie> {
        return withContext(Dispatchers.IO){
            movieDao.getBestFiveMoviesByRating()
        }
    }

    override suspend fun getMoviesByGenre(genre: String): List<LocalMovie> {
        return withContext(Dispatchers.IO){
            movieDao.getMoviesByGenre(genre)
        }
    }


    override suspend fun insertMovies(movies: List<Movie>) {
         movieDao.upSertAll(movies.toLocal())

    }


}


