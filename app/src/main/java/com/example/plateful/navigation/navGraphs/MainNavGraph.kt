package com.example.plateful.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.categoryFood.CategoryFoodScreen
import com.example.plateful.ui.screen.favourites.FavouritesScreen
import com.example.plateful.ui.screen.home.HomeScreen
import com.example.plateful.ui.viewModel.PlatefulViewModel

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    platefulViewModel: PlatefulViewModel
) {
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route
    ) {
        composable(
            route = AppScreen.Main.Home.route
        ) {
            platefulViewModel.getRepoCategories()
            HomeScreen(
                onCategoryClick = {
                    platefulViewModel.setSelectedCategory(it)
                    val route = AppScreen.Main.CategoryFood.createRoute(category = it)
                    navController.navigate(route)
                },
                platefulViewModel = platefulViewModel
            )
        }

        composable(
            route = AppScreen.Main.Favourites.route
        ) {
            platefulViewModel.getRepoFoodByFavourite()
            FavouritesScreen(
                onFoodClick = {
                    platefulViewModel.setFavourite(!it.favourite, it.id)
                    platefulViewModel.getRepoFoodByFavourite()
                },
                platefulViewModel = platefulViewModel
            )
        }

        composable(
            route = AppScreen.Main.CategoryFood.route
        ) {
            platefulViewModel.getRepoFoodByCategory()
            CategoryFoodScreen(
                onFoodClick = {
                    platefulViewModel.setFavourite(!it.favourite, it.id)
                },
                platefulViewModel = platefulViewModel
            )
        }
    }
}