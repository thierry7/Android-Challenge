package com.podium.technicalchallenge.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.entity.Movie
@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAllMovies(): List<LocalMovie>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Int): LocalMovie

    @Query("WITH RECURSIVE split_genre AS (\n" +
            "  SELECT \n" +
            "    SUBSTR(genres, 1, INSTR(genres || ',', ',') - 1) AS genre,\n" +
            "    SUBSTR(genres, INSTR(genres || ',', ',') + 1) AS remaining\n" +
            "  FROM movie\n" +
            "  UNION ALL\n" +
            "  SELECT\n" +
            "    SUBSTR(remaining, 1, INSTR(remaining || ',', ',') - 1) AS genre,\n" +
            "    SUBSTR(remaining, INSTR(remaining || ',', ',') + 1) AS remaining\n" +
            "  FROM split_genre\n" +
            "  WHERE remaining != ''\n" +
            ")\n" +
            "SELECT DISTINCT TRIM(genre) AS genre\n" +
            "FROM split_genre\n" +
            "WHERE genre != ''")
    suspend fun getGenres(): List<String>

    @Query("SELECT * FROM movie ORDER BY voteAverage DESC LIMIT 5")
    suspend fun getBestFiveMoviesByRating(): List<LocalMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<LocalMovie>)

    @Query("SELECT * FROM movie WHERE INSTR(',' || genres || ',', :genre) > 0")
    suspend fun getMoviesByGenre(genre: String): List<LocalMovie>
    @Upsert
    suspend fun upSertAll(movies: List<LocalMovie>)
}