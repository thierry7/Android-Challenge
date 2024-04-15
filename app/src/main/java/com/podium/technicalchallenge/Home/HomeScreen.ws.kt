import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.podium.technicalchallenge.Home.HomeViewmodel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.UiState
import com.podium.technicalchallenge.entity.Movies
import com.podium.technicalchallenge.ui.CustomizedTheme


@Composable
fun HomeScreen(
    onMovieClicked : (Movies) -> Unit,
    viewmodel: HomeViewmodel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    viewmodel.fetchUsers()
    val uiState by viewmodel.uiState.collectAsState()
    CustomizedTheme{
        Column(Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(1f)
            ) {
                Column(Modifier.fillMaxWidth()){
                    Text(
                        text = "Movies: Top 5",
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold

                        )

                    Spacer(modifier = Modifier.height(10.dp))

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
                    .weight(1f)
            ) {
                // Content for the first area
                Text(text = "Browse By Genre")

            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Blue)
                    .height(1.dp)
                    .weight(1f)
            ) {
                Text(text = "Browse By ALL")

            }

        }
    }

}

@Composable
fun MovieList(
    uiState: UiState<List<Movies>>,
    padding: PaddingValues,
    onMovieClicked: (Movies) -> Unit
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
fun MovieItem(movie: Movies, onMovieClicked: (Movies) -> Unit) {

    AsyncImage(
        model = movie.posterPath,
        contentDescription = null,
        error = painterResource(id =  R.drawable.ic_home_black_24dp),
        modifier = Modifier.clickable { onMovieClicked(movie) }
            .padding(6.dp)
            .wrapContentHeight()
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}



