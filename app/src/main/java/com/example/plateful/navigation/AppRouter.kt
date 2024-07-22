package com.example.plateful.navigation

import com.example.plateful.R

private object Routes {
    // Main Graph Route
    const val MAIN = "main"
    const val HOME = "home"
    const val FAVOURITES = "favourites"
    const val CATEGORY_FOOD = "categoryFood/{${ArgParams.CATEGORY}}"
}

private object ArgParams {
    const val CATEGORY = "category"

    fun toPath(param: String) = "{${param}}"
}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
)

sealed class AppScreen {
    data object Main: TopLevelDestination(Routes.MAIN) {
        data object Home: TopLevelDestination(
            route = Routes.HOME,
            title = R.string.home,
        )

        data object Favourites: TopLevelDestination(
            route = Routes.FAVOURITES,
            title = R.string.favourites,
        )

        data object CategoryFood: TopLevelDestination(
            route = Routes.CATEGORY_FOOD,
            title = R.string.category_detail
        ) {
            fun createRoute(category: String) =
                Routes.CATEGORY_FOOD
                    .replace(ArgParams.toPath(ArgParams.CATEGORY), category)
        }
    }
}