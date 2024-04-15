package com.podium.technicalchallenge

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetWorkDataSource {

     @Headers("Content-Type: application/json")
     @POST("graphql")
     suspend fun query(): Response<String>


}
