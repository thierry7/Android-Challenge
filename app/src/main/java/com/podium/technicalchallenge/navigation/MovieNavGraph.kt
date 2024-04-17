package com.podium.technicalchallenge.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.podium.technicalchallenge.ui.myScreens.GenreScreen
import com.podium.technicalchallenge.ui.myScreens.MovieDetailScreen

@Composable
fun MovieNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.MainScreen.route,
    navActions: MovieNavigationActions = remember(navController) {
        MovieNavigationActions(navController)
    }

) {

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
                },
                onGenreClicked = {
                    genre -> navActions.navigateToMovieListPerGenre(genre)
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

        composable(
            route = "${Screen.GenreScreen.route}/{genre}",
            arguments = listOf(
                navArgument(name = "genre") { type = NavType.StringType; nullable = false }
            )
        ) { entry ->
            GenreScreen(
                genreName = entry.arguments?.getString("genre")!!
            )
        }
    }

}
