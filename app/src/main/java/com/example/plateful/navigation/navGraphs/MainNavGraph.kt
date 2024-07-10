package com.example.plateful.navigation.navGraphs

import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.main.FavouritesScreen
import com.example.plateful.ui.screen.main.FoodDetailScreen
import com.example.plateful.ui.screen.main.HomeScreen
import com.example.plateful.ui.screen.main.ProfileScreen
import com.example.plateful.ui.screen.main.RandomFoodScreen
import com.example.plateful.ui.screen.main.SearchScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    rootNavBackStackEntry: State<NavBackStackEntry?>
) {
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route
    ) {
        composable(
            route = AppScreen.Main.Home.route
        ) {
            HomeScreen(
                onFoodClick = {
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it.toString())
                    navController.navigate(route)
                },
                onSearch = {
                    val route = AppScreen.Main.Search.createRoute(query = it.toString())
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = AppScreen.Main.Profile.route
        ) {
            ProfileScreen(navigateToLogin = {
                navController.navigate(AppScreen.Auth.route) {
                    popUpTo(AppScreen.Main.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = AppScreen.Main.RandomFood.route
        ) {
            RandomFoodScreen(
                onButtonClick = {
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it.toString())
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = AppScreen.Main.Favourites.route
        ) {
            FavouritesScreen(
                onFoodClick = {
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it.toString())
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = AppScreen.Main.Search.route
        ) {
            val query = rootNavBackStackEntry?.arguments?.getString("query")
            SearchScreen(
                onFoodClick = {
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it.toString())
                    navController.navigate(route)
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = AppScreen.Main.FoodDetail.route
        ) {
            FoodDetailScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}