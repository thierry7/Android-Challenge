package com.podium.technicalchallenge.network

import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class DefaultRepo @Inject constructor(
    private val retrofit: Retrofit
): MovieRepo {

        override suspend fun getMovieList(): Response<MovieResponse> {

            val requestBody =
                "{\"query\":\"${Queries.getMoviesQuery()}\"}".toRequestBody("application/json".toMediaTypeOrNull())

            val response = retrofit.create(GraphQLService::class.java).queryListMovies(requestBody)

            return response

        }




}



//        val requestBody =
//            "{\"query\":\"${Queries.getMoviesQuery()}\"}".toRequestBody("application/json".toMediaTypeOrNull())