package com.podium.technicalchallenge.network
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepo {

    suspend fun getMovieList(): Response<MovieResponse>





}