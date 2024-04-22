package com.podium.technicalchallenge.ui.myScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.viewModel.DefaultViewmodel

@Composable
fun GenreScreen(
    genreName: String,
    viewmodel: DefaultViewmodel = hiltViewModel()
) {
    viewmodel.getListMovieByGenre(genreName)
    val genres by viewmodel.listOfMovieByGenre.observeAsState()
    val searchText by viewmodel.searchText.observeAsState("")

    Scaffold (
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            TopBar(genreName)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewmodel.setSearchText(it) },
                    placeholder = { Text(text = "Search By Title or Rating") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(
                        imageVector =Icons.Default.Search,
                        contentDescription = "Search",
                        modifier= Modifier.size(18.dp) ) }
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        val filteredGenres = genres?.filter {movie ->
                            movie.title.contains(searchText, ignoreCase = true) ||
                            movie.voteAverage.toString().contains(searchText, ignoreCase = true)
                        } ?: emptyList()
                        items(filteredGenres.size) { index ->
                            MovieItem(movie = filteredGenres[index], padding = paddingValues)
                        }
                    }
                )
            }
        },
        containerColor = Color.Transparent
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(genreName: String) {

    TopAppBar(title = {
        Text(
            text = genreName,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color(0xfffc6603),
                    offset = Offset(1f, 1f),
                    blurRadius = 3f
                )
            )
        ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(.4f)
    ) )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: LocalMovie, padding: PaddingValues) {
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = { showDialog.value = true }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                error = painterResource(id = R.drawable.ic_home_black_24dp),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.LightGray.copy(.7f))
                    .padding(6.dp),
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0xfffc6603),
                            offset = Offset(1f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.align(Alignment.Start)) {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = null)
                    Text(
                        text = movie.voteAverage.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                }
            }
        }
    }

    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = movie.posterPath,
                        contentDescription = movie.title,
                        error = painterResource(id = R.drawable.ic_home_black_24dp),
                        modifier = Modifier
                            .width(350.dp)
                            .height(500.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        )
    }
}
