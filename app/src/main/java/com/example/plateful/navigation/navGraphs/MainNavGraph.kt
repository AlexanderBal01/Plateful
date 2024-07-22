package com.example.plateful.navigation.navGraphs

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.categoryFood.CategoryFoodScreen
import com.example.plateful.ui.screen.favourites.FavouritesScreen
import com.example.plateful.ui.screen.foodDetail.FoodDetailScreen
import com.example.plateful.ui.screen.home.HomeScreen
import com.example.plateful.ui.screen.randomfood.RandomFoodScreen
import com.example.plateful.ui.viewModel.PlatefulViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    platefulViewModel: PlatefulViewModel
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
                    platefulViewModel.setSelectedCategory(it)
                    platefulViewModel.getRepoFoodByCategory()
                    val route = AppScreen.Main.CategoryFood.createRoute(category = it)
                    navController.navigate(route)
                },
                platefulViewModel = platefulViewModel
            )
        }

        composable(
            route = AppScreen.Main.RandomFood.route
        ) {
            RandomFoodScreen(
                onButtonClick = {
                    platefulViewModel.setSelectedFood(it)
                    platefulViewModel.getRepoFullMeal()
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it)
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = AppScreen.Main.Favourites.route
        ) {
            FavouritesScreen(
                onFoodClick = {
                    platefulViewModel.setSelectedFood(it)
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it)
                    platefulViewModel.getRepoFullMeal()
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = AppScreen.Main.CategoryFood.route
        ) {
            CategoryFoodScreen(
                onFoodClick = {
                    platefulViewModel.setSelectedFood(it)
                    platefulViewModel.getRepoFullMeal()
                    val route = AppScreen.Main.FoodDetail.createRoute(foodId = it)
                    navController.navigate(route)
                },
                platefulViewModel = platefulViewModel
            )
        }

        composable(
            route = AppScreen.Main.FoodDetail.route
        ) {
            FoodDetailScreen(platefulViewModel = platefulViewModel)
        }
    }
}