package com.podium.technicalchallenge.Home

import android.service.autofill.OnClickAction
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.podium.technicalchallenge.UiState

@Composable
fun GenreDisplayScreen(
    viewmodel: HomeViewmodel,
    onGenreClicked: (String)->Unit

){
    viewmodel.fetchGenres()
    val uiStateGenre by viewmodel.uiStateGenre.collectAsState()

    when (uiStateGenre) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {

            val genre = (uiStateGenre as UiState.Success<List<String>>).data
            LazyRow {
                items(genre.size){index ->
                    Text(
                        text = genre[index],
                        modifier = Modifier.clickable {
                            onGenreClicked(genre[index])

                        }
                    )
                }
            }
        }
        is UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${(uiStateGenre as UiState.Error).message}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }


    LazyRow {


    }

}