package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = Repo.getInstance().getMovies()
            Log.d("DemoViewModel", "movies=$movies")
        }
    }

}
