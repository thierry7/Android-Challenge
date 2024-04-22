package com.podium.technicalchallenge.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.network.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("SuspiciousIndentation")
@HiltViewModel
class DefaultViewmodel @Inject constructor(
    private val repo: MovieRepo
): ViewModel() {

    private val _movieList: MutableLiveData<List<LocalMovie>> = MutableLiveData(emptyList())
    val movieList: LiveData<List<LocalMovie>> get() = _movieList
    private val _bestMoviesLiveData: MutableLiveData<List<LocalMovie>> = MutableLiveData(emptyList())
    val bestMoviesLiveData: LiveData<List<LocalMovie>> get() = _bestMoviesLiveData

    private val _genreList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val genreList: LiveData<List<String>> get() = _genreList

    private val _movie : MutableLiveData<LocalMovie> = MutableLiveData()
    val movie: LiveData<LocalMovie> get() = _movie

    private val _listOfMovieByGenre : MutableLiveData<List<LocalMovie>> = MutableLiveData(emptyList())
    val listOfMovieByGenre: LiveData<List<LocalMovie>> get() = _listOfMovieByGenre

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText



    init {
        getAllMovies()
        viewModelScope.launch {
            repo.loadMovies()

        }
    }
    fun getGenres(){
        viewModelScope.launch {
            _genreList.postValue(repo.getGenres())
        }
    }
    private fun getAllMovies(){
        viewModelScope.launch {
            _movieList.postValue(repo.getAllMovies())
        }
    }
    fun getBestFiveMoviesByRating(){
        viewModelScope.launch {
            _bestMoviesLiveData.postValue(repo.getBestFiveMoviesByRating())
        }
    }
    fun getClickedMovie(id: Int) {

        viewModelScope.launch {
            _movie.postValue(repo.getMovieById(id))

        }
    }
    fun getListMovieByGenre(genre: String){
        viewModelScope.launch {
            _listOfMovieByGenre.postValue(repo.getMoviesByGenre(genre))
        }
    }
    fun setSearchText(text: String) {
        _searchText.value = text
    }


}


