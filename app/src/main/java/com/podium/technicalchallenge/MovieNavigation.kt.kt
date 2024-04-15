package com.podium.technicalchallenge

import androidx.navigation.NavHostController
import com.podium.technicalchallenge.MovieScreens.GENRE_SCREEN
import com.podium.technicalchallenge.MovieScreens.HOME_MOVIE_SCREEN
import com.podium.technicalchallenge.MovieScreens.MOVIE_DETAIL_SCREEN
import com.podium.technicalchallenge.TodoDestinationsArgs.GENRE_NAME_ARG
import com.podium.technicalchallenge.TodoDestinationsArgs.MOVIE_ID_ARG


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
object TodoDestinationsArgs {
    const val MOVIE_ID_ARG = "id"
    const val GENRE_NAME_ARG = "genre"
}
private object MovieScreens {
    const val HOME_MOVIE_SCREEN = "home"
    const val MOVIE_DETAIL_SCREEN = "details"
    const val GENRE_SCREEN = "genre"
}

object MovieDestinations {
    const val MOVIE_ROUTE = HOME_MOVIE_SCREEN
    const val MOVIE_DETAIL_ROUTE = "$MOVIE_DETAIL_SCREEN/{$MOVIE_ID_ARG}"
    const val GENRE_DETAIL_ROUTE = "$GENRE_SCREEN/{$GENRE_NAME_ARG}"
}
class MovieNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(HOME_MOVIE_SCREEN){
            launchSingleTop = true
        }
    }
    fun navigateToMovieDetail(id: Int) {
        navController.navigate(Screen.DetailsScreen.withArgs(id.toString()))
    }

    fun navigateToMovieListPerGenre(genre: String) {
        navController.navigate("$GENRE_SCREEN/$genre")
    }


}
