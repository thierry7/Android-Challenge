package com.podium.technicalchallenge.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.network.MovieRepo
import com.podium.technicalchallenge.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: MovieRepo
): ViewModel() {

    private val _movieList: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    private val movieList: LiveData<List<Movie>> get() = _movieList
    private val _bestMoviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData(emptyList())
    private val bestMoviesLiveData: LiveData<List<Movie>> get() = _bestMoviesLiveData




    init {
        viewModelScope.launch {
            val response = repo.getMovieList()
            if (response.isSuccessful) {
                val movies = response.body()?.data?.movies
                _bestMoviesLiveData.value = movies?.sortedByDescending { it.voteAverage }?.take(5) ?: emptyList()
                _movieList.value = movies ?: emptyList()
            } else {
                println("Error: ${response.message()}")
            }
        }
    }

    fun getFiveBestMovies(): LiveData<List<Movie>> {
        return bestMoviesLiveData
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieList
    }

    fun getClickedMovie(id: Int): LiveData<Movie?> {
        return movieList.map {
            it.find{
                it.id == id.toInt()
            }
        }
    }
}



data class ScreenState(
    val movies: List<Movie>? = emptyList(),
    val loading: Boolean? = false
)