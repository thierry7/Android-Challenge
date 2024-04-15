package com.podium.technicalchallenge.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.DefaultRepo
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val repo: DefaultRepo
): ViewModel() {


    private val _uiState = MutableStateFlow<UiState<List<Movies>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movies>>> = _uiState

    fun fetchUsers() {
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

}