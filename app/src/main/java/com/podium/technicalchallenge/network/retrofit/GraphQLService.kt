package com.podium.technicalchallenge.network.retrofit

import com.podium.technicalchallenge.entity.GenresResponse
import com.podium.technicalchallenge.entity.MovieResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {
    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun queryListMovies(@Body body: RequestBody): Response<MovieResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun queryMovie(@Body body: RequestBody): Response<MovieResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun queryGenres(@Body body: RequestBody): Response<GenresResponse>



}
