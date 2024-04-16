package com.podium.technicalchallenge.network.queries

object Queries {
    fun getMoviesQuery() =
"""query Query { movies { id title releaseDate posterPath voteAverage genres}}"""
    fun getMovieQuery(id: Int) =
        """query GetMovieById { movie(id: $id) { id originalLanguage originalTitle overview popularity posterPath releaseDate voteAverage voteCount title budget runtime genres cast { name character } director { id name }}}"""
    fun getGenresQuery() =
        """query GetAllGenres { genres }"""
}

