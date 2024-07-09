package com.example.plateful.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument

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
    const val FOODDETAIL = "foodDetail/{${ArgParams.FOOD_ID}}"
}

private object ArgParams {
    const val FOOD_ID = "foodId"

    fun toPath(param: String) = "{${param}}"
}

sealed class TopLevelDestination(
    val route: String,
    val title: Int? = null,
    val icon: ImageVector? = null,
    val navArguments: List<NamedNavArgument> = emptyList()
)