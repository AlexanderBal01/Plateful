package com.example.plateful.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.categoryFood.categoryFoodScreen
import com.example.plateful.ui.screen.favourites.favouritesScreen
import com.example.plateful.ui.screen.home.homeScreen
import com.example.plateful.ui.viewModel.PlatefulViewModel

/**
 * Defines the main navigation graph for the application, which includes the Home,
 * Favourites, and CategoryFood screens. This function sets up the navigation
 * routes and connects them to their respective composables.
 *
 * @param navController The [NavHostController] that manages app navigation.
 * @param platefulViewModel The [PlatefulViewModel] that provides data and
 * handles logic for the app's screens.
 */
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    platefulViewModel: PlatefulViewModel,
) {
    navigation(
        route = AppScreen.Main.route,
        startDestination = AppScreen.Main.Home.route,
    ) {
        composable(
            route = AppScreen.Main.Home.route,
        ) {
            platefulViewModel.getRepoCategories()
            homeScreen(
                onCategoryClick = {
                    platefulViewModel.setSelectedCategory(it)
                    val route = AppScreen.Main.CategoryFood.createRoute(category = it)
                    navController.navigate(route)
                },
                platefulViewModel = platefulViewModel,
            )
        }

        composable(
            route = AppScreen.Main.Favourites.route,
        ) {
            platefulViewModel.getRepoFoodByFavourite()
            favouritesScreen(
                onFoodClick = {
                    platefulViewModel.setFavourite(!it.favourite, it.id)
                    platefulViewModel.getRepoFoodByFavourite()
                },
                platefulViewModel = platefulViewModel,
            )
        }

        composable(
            route = AppScreen.Main.CategoryFood.route,
        ) {
            platefulViewModel.getRepoFoodByCategory()
            categoryFoodScreen(
                onFoodClick = {
                    platefulViewModel.setFavourite(!it.favourite, it.id)
                },
                platefulViewModel = platefulViewModel,
            )
        }
    }
}
