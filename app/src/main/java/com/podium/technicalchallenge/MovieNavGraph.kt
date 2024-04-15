package com.podium.technicalchallenge

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.podium.technicalchallenge.Genre.GenreScreen
import com.podium.technicalchallenge.MovieDetails.MovieDetailScreen
import com.podium.technicalchallenge.TodoDestinationsArgs.GENRE_NAME_ARG
import kotlinx.coroutines.CoroutineScope

@Composable
fun MovieNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = Screen.MainScreen.route,
    navActions: MovieNavigationActions = remember(navController) {
        MovieNavigationActions(navController)
    }

) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ){

        composable(
            route= Screen.MainScreen.route) {
            HomeScreen(
                onMovieClicked = {
                    movie -> navActions.navigateToMovieDetail(movie.id)
                }
            )
        }
        composable(
            route= Screen.DetailsScreen.route + "/{id}",
            arguments = listOf(
                navArgument(name="id") { type = NavType.LongType; nullable = false }
            )
            ) { entry ->
            MovieDetailScreen(
                movieId = entry.arguments?.getLong("id")!!
            )
        }

        composable(Screen.GenreScreen.route,
            arguments = listOf(
                navArgument(GENRE_NAME_ARG) { type = NavType.StringType; nullable = false }
            )
        ) { entry ->
            GenreScreen(
                genreName = entry.arguments?.getString(GENRE_NAME_ARG)!!
            )
        }
    }

}
