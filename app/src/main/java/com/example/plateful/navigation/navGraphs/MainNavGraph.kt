package com.example.plateful.navigation.navGraphs

import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.favourites.FavouritesScreen
import com.example.plateful.ui.screen.fooddetail.FoodDetailScreen
import com.example.plateful.ui.screen.home.HomeScreen
import com.example.plateful.ui.screen.home.HomeViewModel
import com.example.plateful.ui.screen.randomfood.RandomFoodScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    rootNavBackStackEntry: State<NavBackStackEntry?>,
    homeViewModel: HomeViewModel
) {
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route
    ) {
        composable(
            route = AppScreen.Main.Home.route
        ) {
            HomeScreen(
                onCategoryClick = {
                    val route = AppScreen.Main.CategoryDetail.createRoute(category = it.toString())
                    navController.navigate(route)
                },
                homeViewModel = homeViewModel
            )
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
            route = AppScreen.Main.FoodDetail.route
        ) {
            val foodId = rootNavBackStackEntry.value?.arguments?.getString("foodId")
            if (foodId != null) {
                FoodDetailScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}