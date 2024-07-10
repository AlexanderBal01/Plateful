package com.example.plateful.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
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
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
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
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )

        object Profile: TopLevelDestination(
            route = Routes.PROFILE,
            title = R.string.profile,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        )

        object RandomFood: TopLevelDestination(
            route = Routes.RANDOMFOOD,
            title = R.string.random,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        )

        object Favourites: TopLevelDestination(
            route = Routes.FAVOURITES,
            title = R.string.favourites,
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star
        )

        object Search: TopLevelDestination(
            route = Routes.SEARCH,
            title = R.string.search,
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