package com.podium.technicalchallenge.ui.myScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.podium.technicalchallenge.viewModel.HomeViewmodel


@Composable
fun GenreDisplayScreen(
    viewmodel: HomeViewmodel,
    onGenreClicked: (String)->Unit

) {
    val genres = viewmodel.genreList.observeAsState()

        genres.value?.forEach { genre ->
            GenreItem(genre = genre, onGenreClicked = onGenreClicked)
            Spacer(modifier = Modifier.height(8.dp))
        }

}

@Composable
fun GenreItem(genre: String, onGenreClicked: (String) -> Unit) {
    Text(
        text = genre,
        modifier = Modifier
            .clickable { onGenreClicked(genre) }
            .padding(horizontal = 8.dp)
            .background(color = Color.White)
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 12.dp),

        style = TextStyle(color = Color.Black)
    )
}
