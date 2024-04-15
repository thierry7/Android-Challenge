package com.podium.technicalchallenge.MovieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.DefaultRepo
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movies
import com.podium.technicalchallenge.util.Async
import com.podium.technicalchallenge.util.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskDetailUiState(
    val movie: Movies? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: DefaultRepo,
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Movies>>(UiState.Loading)
    val uiState: StateFlow<UiState<Movies>> = _uiState

    fun fetchUsers(id: Int) {
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



