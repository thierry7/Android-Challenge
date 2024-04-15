package com.podium.technicalchallenge

import androidx.navigation.NavHostController


sealed class Screen(val route: String){
    object MainScreen: Screen( "main_scree" )
    object DetailsScreen: Screen( "details_screen" )
    object GenreScreen: Screen( "genre_screen" )

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
class MovieNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(Screen.MainScreen.route){
            launchSingleTop = true
        }
    }
    fun navigateToMovieDetail(id: Int) {
        navController.navigate(Screen.DetailsScreen.withArgs(id.toString()))
    }

    fun navigateToMovieListPerGenre(genre: String) {
        navController.navigate(Screen.GenreScreen.withArgs(genre))
    }


}
