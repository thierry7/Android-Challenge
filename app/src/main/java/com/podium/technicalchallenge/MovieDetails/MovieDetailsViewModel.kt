package com.podium.technicalchallenge.MovieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.DefaultRepo
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: DefaultRepo,
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Movie>>(UiState.Loading)
    val uiState: StateFlow<UiState<Movie>> = _uiState

    fun fetchMoviDetails(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = UiState.Loading
            repo.getMovieStream(id)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect { groupedUsers ->
                    _uiState.value = UiState.Success(groupedUsers)
                }
        }
    }

}



