package com.example.plateful.navigation

import com.example.plateful.R

/**
 * Object containing the route definitions used within the app's navigation.
 * These constants define the paths used in navigation routes.
 */
private object Routes {
    /** Route for the main navigation graph. */
    const val MAIN = "main"

    /** Route for the home screen. */
    const val HOME = "home"

    /** Route for the favourites screen. */
    const val FAVOURITES = "favourites"

    /** Route for the category food screen with a category parameter. */
    const val CATEGORY_FOOD = "categoryFood/{${ArgParams.CATEGORY}}"
}

/**
 * Object containing argument parameter keys used in navigation routes.
 */
private object ArgParams {
    /** Key for the category parameter used in the category food route. */
    const val CATEGORY = "category"

    /**
     * Utility function to format a parameter key for use in a route path.
     *
     * @param param The parameter key to be formatted.
     * @return The formatted parameter path as a string.
     */
    fun toPath(param: String) = "{${param}}"
}

/**
 * Represents a top-level destination within the app's navigation hierarchy.
 *
 * @property route The route associated with this destination.
 * @property title The resource ID of the title string associated with this destination (nullable).
 */
sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
)

/**
 * Represents the various screens available in the app, organized under the main navigation graph.
 */
sealed class AppScreen {
    /**
     * Represents the main navigation graph, containing the home, favourites, and category food screens.
     */
    data object Main : TopLevelDestination(Routes.MAIN) {
        /**
         * Represents the home screen within the main navigation graph.
         */
        data object Home : TopLevelDestination(
            route = Routes.HOME,
            title = R.string.home,
        )

        /**
         * Represents the favourites screen within the main navigation graph.
         */
        data object Favourites : TopLevelDestination(
            route = Routes.FAVOURITES,
            title = R.string.favourites,
        )

        /**
         * Represents the category food screen within the main navigation graph.
         * This screen displays food items based on a selected category.
         */
        data object CategoryFood : TopLevelDestination(
            route = Routes.CATEGORY_FOOD,
            title = R.string.category_detail
        ) {
            /**
             * Creates a fully qualified route for the category food screen by replacing the
             * category parameter placeholder with the actual category value.
             *
             * @param category The category value to insert into the route.
             * @return The fully qualified route string.
             */
            fun createRoute(category: String) =
                Routes.CATEGORY_FOOD
                    .replace(ArgParams.toPath(ArgParams.CATEGORY), category)
        }
    }
}
