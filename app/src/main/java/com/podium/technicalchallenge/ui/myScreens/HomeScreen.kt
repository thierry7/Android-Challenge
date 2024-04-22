import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.ui.CustomizedTheme
import com.podium.technicalchallenge.viewModel.DefaultViewmodel

@Composable
fun HomeScreen(
    onMovieClicked : (LocalMovie) -> Unit,
    onGenreClicked: (String)-> Unit,
    viewmodel: DefaultViewmodel = hiltViewModel(),
) {
    viewmodel.getBestFiveMoviesByRating()
    val topMovies = viewmodel.bestMoviesLiveData .observeAsState()
    if(!topMovies.value.isNullOrEmpty())
    {
        CustomizedTheme{
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Transparent)
                    .weight(0.5f)) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color.Gray,
                                        Color.Transparent
                                    )
                                )
                            )
                    ){
                        Text(text = "Movies: Top 5", modifier = Modifier
                            .padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Scaffold(
                            content = { padding ->
                                LazyRow {
                                    topMovies.value?.size?.let {
                                        items(it){ index ->
                                            MovieItem(movie = topMovies.value!![index],
                                                onMovieClicked, 10, 240, padding)
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .fillMaxHeight()
                        .weight(0.15f)
                ) {
                    Column {
                        viewmodel.getGenres()
                        val genres = viewmodel.genreList.observeAsState()

                        Text(text = "Browse By Genre", modifier = Modifier
                            .padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 25.sp,)
                        Spacer(modifier = Modifier
                            .height(8.dp)
                            .background(Color.Green))
                        Scaffold (
                            content = {padding ->
                                LazyRow {
                                    genres.value?.size?.let {
                                        items(it){ index ->
                                            GenreItem( genre = genres.value!![index],
                                                onGenreClicked = onGenreClicked, padding)
                                        }
                                    }

                                }


                            }

                        )

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(0.35f)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.Gray,
                                Color.Transparent
                            )
                        )
                    )
                ) {
                    val movies = viewmodel.movieList.observeAsState()
                    Column {
                        Text(text = "Browse By ALL", modifier = Modifier
                            .padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 25.sp,)
                        Spacer(modifier = Modifier.height(8.dp))
                            Scaffold(
                                content = { padding ->
                                    LazyRow {
                                        movies.value?.size?.let {
                                            items(it){ index ->

                                                MovieItem(movie = movies.value!![index],
                                                    onMovieClicked, 6, 155, padding)
                                            }
                                        }
                                    }
                                }
                            )
                    }
                }
            }
        }

    }else{
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: LocalMovie,
              onMovieClicked: (LocalMovie) -> Unit,
              padding: Int,
              width: Int,
              paddingValue: PaddingValues
) {
    val myGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xffb226e1),
            Color(0xfffc6601),
            Color(0xff5995ee),
            Color(0xff303535)
        ),
        start = Offset(Float.POSITIVE_INFINITY, 0f),
        end = Offset(0f, Float.POSITIVE_INFINITY)

    )

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(padding.dp)
            .clickable {
                onMovieClicked(movie)
            },
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(myGradient),
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
                    .width(width.dp)
                    .background(Color.LightGray.copy(.7f))
                    .padding(6.dp),

            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    style = androidx.compose.ui.text.TextStyle(
                        shadow = Shadow(
                            color = Color(0xfffc6603),
                            offset = Offset(1f, 1f),
                            blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.align(Alignment.End)) {
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
}
@Composable
fun GenreItem(genre: String, onGenreClicked: (String) -> Unit, padding : PaddingValues) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Transparent), contentAlignment = Alignment.Center ){
        Button(
            modifier = Modifier
                .fillMaxHeight().padding(8.dp)
                .background(brush = Brush.horizontalGradient(
                    listOf(
                        Color.Gray,
                        Color.Transparent
                    )
                ))
                .width(120.dp),
            shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
            enabled = true,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 30.dp
            ),
            onClick = { onGenreClicked(genre) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7B8AE7),
            ),

        ){
            Text(text = genre, color= Color.Black, style = TextStyle(
                shadow = Shadow(
                    color = Color(0xfffc6603),
                    offset = Offset(1f, 1f),
                    blurRadius = 3f
                ),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            ))
        }
    }
}
