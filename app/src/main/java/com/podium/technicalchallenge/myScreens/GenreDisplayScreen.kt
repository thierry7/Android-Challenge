package com.podium.technicalchallenge.myScreens

//
//@Composable
//fun GenreDisplayScreen(
//    viewmodel: HomeViewmodel,
//    onGenreClicked: (String)->Unit
//
//){
//    viewmodel.fetchGenres()
//    val uiStateGenre by viewmodel.uiStateGenre.collectAsState()
//
//    when (uiStateGenre) {
//        is UiState.Loading -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//        is UiState.Success -> {
//
//            val genre = (uiStateGenre as UiState.Success<List<String>>).data
//            LazyRow {
//                items(genre.size){index ->
//                    Text(
//                        text = genre[index],
//                        modifier = Modifier.clickable {
//                            onGenreClicked(genre[index])
//
//                        }
//                    )
//                }
//            }
//        }
//        is UiState.Error -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(20.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("Error: ${(uiStateGenre as UiState.Error).message}", style = MaterialTheme.typography.bodyLarge)
//            }
//        }
//    }
//
//
//    LazyRow {
//
//
//    }
//
//}