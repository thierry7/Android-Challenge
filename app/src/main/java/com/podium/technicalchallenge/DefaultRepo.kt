package com.podium.technicalchallenge

import com.podium.technicalchallenge.entity.Movies
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class DefaultRepo @Inject constructor(
    private val retrofit: Retrofit
) : MovieRepo {



    override suspend fun getMovieStream() : Flow<List<Movies>> {
        val requestBody =
            "{\"query\":\"${Queries.getMoviesQuery()}\"}".toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            val response = retrofit
                .create(GraphQLService::class.java)
                .query(requestBody)

            if (response.isSuccessful) {
                val movieResponse = response.body()
                if (movieResponse != null) {
                    emit(movieResponse.data.movies)
                } else {
                    // Handle null response body
                    emit(emptyList())
                }
            } else {
                // Handle unsuccessful response
                throw IllegalStateException("Failed to fetch movies: ${response.code()}")
            }
        }.catch { e ->
            // Handle exceptions during flow emission
            emit(emptyList())
            // Log or handle the exception as needed
            println("Exception occurred: $e")
        }
    }
    override suspend fun getMovieStream(movieId: Int): Flow<Movies> {
        val requestBody =
            "{\"query\":\"${Queries.getMovieQuery(movieId)}\"}".toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            val response = retrofit
                .create(GraphQLService::class.java)
                .queryMovie(requestBody)

            println("===>${response.body()}")

            if(response.isSuccessful){
                response.body()?.let { emit(it.data.movie) }
            }

        }

    }

    override suspend fun getMovie(movieName: String): Movies {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesByGenre(genreName: String): List<Movies> {
        TODO("Not yet implemented")
    }
}


