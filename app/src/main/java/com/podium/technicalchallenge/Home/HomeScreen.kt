import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.podium.technicalchallenge.Home.GenreDisplayScreen
import com.podium.technicalchallenge.Home.HomeViewmodel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.ui.CustomizedTheme


@Composable
fun HomeScreen(
    onMovieClicked : (Movie) -> Unit,
    onGenreClicked: (String)-> Unit,
    viewmodel: HomeViewmodel = hiltViewModel(),
) {
    viewmodel.fetchMovies()
    val uiState by viewmodel.uiState.collectAsState()
    CustomizedTheme{
        Column(Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(0.5f)

            ) {
                Column(Modifier.fillMaxWidth()
                    .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Gray,
                            Color.Transparent
                        )
                    )
                )
                ){
                    Text(
                        text = "Movies: Top 5",
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp,

                        )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Scaffold(
                            content = { padding ->

                                MovieList(uiState, padding, onMovieClicked)
                            }
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .height(1.dp)
                    .weight(0.1f)
            ) {
                // Content for the first area
                Text(
                    text = "Browse By Genre",
                    modifier = Modifier
                        .padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                GenreDisplayScreen( viewmodel = viewmodel, onGenreClicked = onGenreClicked)


            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(0.4f)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.Gray,
                                Color.Transparent
                            )
                        )
                    )
            ) {
                Column {
                    Text(
                        text = "Browse By ALL",
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp,

                        )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Scaffold(
                            content = { padding ->

                                MovieListAll(uiState, padding, onMovieClicked)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieList(
    uiState: UiState<List<Movie>>,
    padding: PaddingValues,
    onMovieClicked: (Movie) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val topMovies = uiState.data.sortedByDescending { it.voteAverage }.take(5)

            LazyRow {
                items(topMovies.size){index ->

                        MovieItem(movie = topMovies[index], onMovieClicked)
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
                Text("Error: ${uiState.message}", style = typography.bodyLarge)
            }
        }
    }
}



@Composable
fun MovieListAll(
    uiState: UiState<List<Movie>>,
    padding: PaddingValues,
    onMovieClicked: (Movie) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val topMovies = uiState.data
            LazyRow {
                items(topMovies.size){index ->

                    MovieItem(movie = topMovies[index], onMovieClicked)
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
                Text("Error: ${uiState.message}", style = typography.bodyLarge)
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: Movie, onMovieClicked: (Movie) -> Unit) {

    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                onMovieClicked(movie)
            },
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize().background(Color.Red),


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
                    .width(190.dp)
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
                Row(Modifier.align(Alignment.Start)) {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = null)
                    Text(
                        text = movie.voteAverage.toString(),
                        modifier = Modifier.fillMaxWidth()
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
