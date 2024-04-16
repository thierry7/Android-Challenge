package com.podium.technicalchallenge.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.DefaultRepo
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: DefaultRepo
): ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movie>>> = _uiState
    private val _uiStateGenres = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val uiStateGenre: StateFlow<UiState<List<String>>> = _uiStateGenres


    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            repo.getMovieStream()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect { groupedUsers ->
                    _uiState.value = UiState.Success(groupedUsers)
                }
        }
    }



    fun fetchGenres() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiStateGenres.value = UiState.Loading
            repo.getGenres()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiStateGenres.value = UiState.Error(e.toString())
                }
                .collect { groupedUsers ->
                    _uiStateGenres.value = UiState.Success(groupedUsers)
                }
        }
    }

}