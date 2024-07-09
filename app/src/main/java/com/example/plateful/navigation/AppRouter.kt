package com.example.plateful.navigation

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