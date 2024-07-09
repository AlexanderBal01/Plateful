package com.example.plateful.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.plateful.R

private object Routes {
    // Auth Graph Route
    const val AUTH = "auth"
    const val LOGIN = "login"
    const val REGISTER = "signup"

    // Main Graph Route
    const val MAIN = "main"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val RANDOMFOOD = "random"
    const val FAVOURITES = "favourites"
    const val SEARCH = "Search/{${ArgParams.SEARCH}}"
    const val FOODDETAIL = "foodDetail/{${ArgParams.FOOD_ID}}"
}

private object ArgParams {
    const val FOOD_ID = "foodId"
    const val SEARCH = "query"

    fun toPath(param: String) = "{${param}}"
}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val icon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)

sealed class AppScreen(val route: String) {
    object Auth: AppScreen(Routes.AUTH) {
        object Login: AppScreen(Routes.LOGIN)
        object Register: AppScreen(Routes.REGISTER)
    }

    object Main: TopLevelDestination(Routes.MAIN) {
        object Home: TopLevelDestination(
            route = Routes.HOME,
            title = R.string.home,
            icon = Icons.Filled.Home,
        )

        object Profile: TopLevelDestination(
            route = Routes.PROFILE,
            title = R.string.profile,
            icon = Icons.Filled.AddCircle,
        )

        object RandomFood: TopLevelDestination(
            route = Routes.RANDOMFOOD,
            title = R.string.random,
            icon = Icons.Filled.Search
        )

        object Favourites: TopLevelDestination(
            route = Routes.FAVOURITES,
            title = R.string.favourites,
            icon = Icons.Filled.Star
        )

        object Search: TopLevelDestination(
            route = Routes.SEARCH,
            title = R.string.search,
            icon = Icons.Filled.Search,
            navArguments = listOf(navArgument(ArgParams.SEARCH) {
                type = NavType.Companion.StringType
            })
        ) {
            fun createRoute(query: String) =
                Routes.SEARCH
                    .replace(ArgParams.toPath(ArgParams.SEARCH), query)
        }

        object FoodDetail: TopLevelDestination(
            route = Routes.FOODDETAIL,
            title = R.string.food_detail,
            icon = Icons.Filled.Fastfood,
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