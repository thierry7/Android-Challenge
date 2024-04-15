package com.podium.technicalchallenge.network.queries

object Queries {
    fun getMoviesQuery() =
"""query Query { movies { id title releaseDate posterPath genres}}"""


    fun getMovieQuery(id: Int) =
        """query GetMovieById { movie(id: $id) { id originalLanguage originalTitle overview popularity posterPath releaseDate voteAverage voteCount title budget runtime genres cast { name character } director { id name }}}"""
}

//"Accept-Encoding: gzip, deflate, br", "Content-Type: application/json",  "Connection: keep-alive' -H 'DNT: 1"

//curl 'https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql' -H 'Accept-Encoding: gzip, deflate, br' -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Connection: keep-alive' -H 'DNT: 1' -H 'Origin: https://podium-fe-challenge-2021.netlify.app' --data-binary '{"query":"# Write your query or mutation here\nquery myquery{\n  \n movies {id title releaseDate posterPath genres\n  }\n}\n"}' --compressed