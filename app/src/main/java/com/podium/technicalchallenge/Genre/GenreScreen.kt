package com.podium.technicalchallenge.Genre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.podium.technicalchallenge.Home.HomeViewmodel

@Composable
fun GenreScreen(
    genreName : String,
    viewmodel: HomeViewmodel= hiltViewModel()
) {

    Scaffold (
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            TopBar(genreName)
        },
        content = {
            paddingValues ->
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Transparent),
                content = {


                }
            )

        }
    )


    Text(
        text= genreName
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(genreName: String) {

    TopAppBar(title = { Text(text = genreName) })
}
