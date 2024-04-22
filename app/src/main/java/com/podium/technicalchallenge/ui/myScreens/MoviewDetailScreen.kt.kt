package com.podium.technicalchallenge.ui.myScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.podium.technicalchallenge.R
import com.podium.technicalchallenge.entity.LocalMovie
import com.podium.technicalchallenge.viewModel.DefaultViewmodel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailScreen(
    movieId: Long,
    viewModel: DefaultViewmodel = hiltViewModel()
) {

    viewModel.getClickedMovie(movieId.toInt())
    val movie by viewModel.movie.observeAsState()
    if(movie != null){

        Scaffold { padding ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter

            ){
                BackgroundPoster(movie = movie)
                ForeGroundPoster(movie = movie)

                Column(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                        .align(Alignment.BottomCenter),
                    verticalArrangement = Arrangement.spacedBy(20.dp)

                ){

                    Text(
                        text = movie!!.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)
                            .basicMarquee(),
                        maxLines = 1,
                        fontSize = 38.sp,
                        color = Color.White,
                        lineHeight = 40.sp,
                        textAlign = TextAlign.Center,

                        )
                    Rating(movie = movie!!, modifier = Modifier)
                    TextBuilderSummary(icon = Icons.Filled.Info, title ="Summary", bodyText = movie!!)
                    TextBuilderGenres(icon = Icons.Filled.Person, title ="Genre", bodyText = movie!!)
                    TextBuilderDirector(icon = Icons.Filled.Person, title ="Director", bodyText = movie!!)



                }
            }

        }

    }
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator()
        }
    }

}

@Composable
fun TextBuilderDirector(icon: ImageVector, title: String, bodyText: LocalMovie) {
    Row{ Icon(
        imageVector = icon,
        contentDescription = "person",
        tint = Color.White
    )
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    Text(text = bodyText.director.name, color = Color.White)
}

@Composable
fun TextBuilderGenres(icon: ImageVector, title: String, bodyText: LocalMovie) {
    Row{ Icon(
            imageVector = icon,
            contentDescription = "person",
            tint = Color.White
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    Text(text = bodyText.genres.toString(), color = Color.White)
}

@Composable
fun TextBuilderSummary(icon: ImageVector, title: String, bodyText: LocalMovie){

    var textFieldValue by remember { mutableStateOf(bodyText.overview) }

    Row{
        Icon(
            imageVector = icon,
            contentDescription = "person",
            tint = Color.White
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(start = 10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )

    }

    Text(text = bodyText.overview, color = Color.White, maxLines = 3)
}


@Composable
fun Rating(movie: LocalMovie, modifier: Modifier.Companion) {

    Row(modifier= modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

        Icon(Icons.Filled.Star,
            contentDescription = null, tint = Color.White )
        Text( text = movie.voteAverage.toString(), modifier = modifier.padding(start = 6.dp), color = Color.White)

        Spacer(modifier = modifier.width(25.dp))

        Icon(Icons.Filled.Face,
            contentDescription = null, tint = Color.White )
        Text( text = movie.runtime.toString(), modifier = modifier.padding(start = 6.dp), color = Color.White)

        Spacer(modifier = modifier.width(25.dp))

        Icon(Icons.Filled.DateRange,
            contentDescription = null, tint = Color.White )
        Text( text = movie.releaseDate, modifier = modifier.padding(start = 6.dp), color = Color.White)

    }

}

@Composable
fun BackgroundPoster(movie: LocalMovie?){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ){
        AsyncImage(
            model = movie!!.posterPath,
            contentDescription = movie.title,
            error = painterResource(id =  R.drawable.ic_home_black_24dp),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.6f)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.DarkGray,
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            )
    }
}

@Composable
fun ForeGroundPoster(movie: LocalMovie?){
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .padding(top = 80.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.TopCenter
    ){
        AsyncImage(
            model = movie!!.posterPath,
            contentDescription = movie.title,
            modifier = Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(0.6f))
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .width(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color(0xb91a1b1b)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

//@Composable
//fun ExpandableTextField(
//    initialText: String,
//    maxLength: Int = 100,
//    modifier: Modifier = Modifier,
//    onTextChanged: (String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var text by remember { mutableStateOf(initialText) }
//
//    Column(modifier = modifier) {
//        TextField(
//            value = text,
//            onValueChange = {
//                if (it.length <= maxLength) {
//                    text = it
//                    onTextChanged(it)
//                }
//            },
//            modifier = Modifier.fillMaxWidth().background(Color.Transparent),
//            maxLines = if (expanded) Int.MAX_VALUE else 2,
//            readOnly = !expanded,
//            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
//        )
//        if (text.length > maxLength) {
//            if(expanded){
//                Text(
//                    text = "...see_less",
//                    color = Color.White,
//                    modifier = Modifier.align(Alignment.End).clickable { expanded = false }
//                )
//            }else{
//                Text(
//                    text = "...see_more",
//                    color = Color.White,
//                    modifier = Modifier.align(Alignment.End).clickable { expanded = true }
//                )
//            }
//
//        }
//    }
//}
