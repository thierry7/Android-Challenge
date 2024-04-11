package com.podium.technicalchallenge

import com.google.gson.Gson
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.network.ApiClient
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import org.json.JSONObject

class Repo {

    suspend fun getMovies(): List<MovieEntity> {
        val paramObject = JSONObject()
        paramObject.put("query", Queries.getMoviesQuery())

        val response = ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java).query(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return data.data.movies
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}
