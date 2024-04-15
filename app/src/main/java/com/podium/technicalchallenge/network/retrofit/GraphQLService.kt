package com.podium.technicalchallenge.network.retrofit

import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.entity.Movies
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GraphQLService {
    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun query(@Body body: RequestBody): Response<MovieResponse>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun queryMovie(@Body body: RequestBody): Response<MovieResponse>


}
