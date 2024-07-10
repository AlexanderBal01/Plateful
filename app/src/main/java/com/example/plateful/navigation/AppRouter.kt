package com.example.plateful.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.plateful.R

private object Routes {

    // Main Graph Route
    const val MAIN = "main"
    const val HOME = "home"
    const val RANDOMFOOD = "random"
    const val FAVOURITES = "favourites"
    const val FOODDETAIL = "foodDetail/{${ArgParams.FOOD_ID}}"
}

private object ArgParams {
    const val FOOD_ID = "foodId"

    fun toPath(param: String) = "{${param}}"
}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)

sealed class AppScreen {
    data object Main: TopLevelDestination(Routes.MAIN) {
        object Home: TopLevelDestination(
            route = Routes.HOME,
            title = R.string.home,
            selectedIcon = Icons.Filled.Home,
        )

        object RandomFood: TopLevelDestination(
            route = Routes.RANDOMFOOD,
            title = R.string.random,
            selectedIcon = Icons.Filled.Search,
        )

        object Favourites: TopLevelDestination(
            route = Routes.FAVOURITES,
            title = R.string.favourites,
            selectedIcon = Icons.Filled.Star,
        )

        object FoodDetail: TopLevelDestination(
            route = Routes.FOODDETAIL,
            title = R.string.food_detail,
            navArguments = listOf(navArgument(ArgParams.FOOD_ID) {
                type = NavType.Companion.StringType
            })
        ) {
            fun createRoute(foodId: String) =
                Routes.FOODDETAIL
                    .replace(ArgParams.toPath(ArgParams.FOOD_ID), foodId)
        }
    }
}