package com.podium.technicalchallenge.network.queries

object Queries {
    fun getMoviesQuery() =
"""query Query { movies { id title overview releaseDate posterPath voteAverage genres}}"""
}

