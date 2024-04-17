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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
//import com.podium.technicalchallenge.Home.GenreDisplayScreen
import com.podium.technicalchallenge.viewModel.HomeViewmodel
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.ui.CustomizedTheme


@Composable
fun HomeScreen(
    onMovieClicked : (Movie) -> Unit,
    onGenreClicked: (String)-> Unit,
    viewmodel: HomeViewmodel = hiltViewModel(),
) {

    val uiState by viewmodel.getAllMovies().observeAsState()
    if(!uiState.isNullOrEmpty())
    {
        CustomizedTheme{
            Column(Modifier.fillMaxSize()){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .weight(0.5f)) {
                    Column(Modifier
                        .fillMaxWidth()
                        .background(brush = Brush.horizontalGradient(
                            listOf(
                                Color.Gray,
                                Color.Transparent)))
                    ){
                        Text(text = "Movies: Top 5", modifier = Modifier
                            .padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 38.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(modifier = Modifier
                            .fillMaxWidth()){
                            Scaffold(
                                content = { padding ->
                                    val topMovies = viewmodel.getFiveBestMovies().observeAsState()
                                    LazyRow {
                                        topMovies.value?.size?.let {
                                            items(it){ index ->
                                                MovieItem(movie = topMovies.value!![index],
                                                    onMovieClicked, 10, 210, padding)
                                            }
                                        }
                                    }
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
                    Text(text = "Browse By Genre", modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold)

                //GenreDisplayScreen( viewmodel = viewmodel, onGenreClicked = onGenreClicked)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).weight(0.4f)
                    .background( brush = Brush.horizontalGradient(
                            listOf(
                                Color.Gray,
                                Color.Transparent
                            )
                        )
                    )
                ) {
                    Column {
                        Text(text = "Browse By ALL", modifier = Modifier
                            .padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 38.sp,)
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth()){
                            Scaffold(
                                content = { padding ->
                                    val topMovies = viewmodel.getAllMovies().observeAsState()
                                    LazyRow {
                                        topMovies?.value?.size?.let {
                                            items(it){ index ->

                                                MovieItem(movie = topMovies.value!![index],
                                                    onMovieClicked, 6, 158, padding)
                                            }
                                        }
                                    }
                                }
                            )
                        }
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
fun MovieItem(movie: Movie,
              onMovieClicked: (Movie) -> Unit,
              padding: Int,
              width: Int,
              paddingValue: PaddingValues
) {

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
}
