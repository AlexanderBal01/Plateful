package com.example.plateful.navigation.navGraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.screen.auth.LoginScreen
import com.example.plateful.ui.screen.auth.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = AppScreen.Auth.route,
        startDestination = AppScreen.Auth.Login.route
    ) {
        composable(
            route = AppScreen.Auth.Login.route
        ) {
            LoginScreen(
                navigateHome = {
                    navController.navigate(AppScreen.Main.route) {
                        popUpTo(AppScreen.Auth.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToRegister = {
                    navController.navigate(AppScreen.Auth.Register.route)
                }
            )
        }

        composable(
            route = AppScreen.Auth.Register.route
        ) {
            RegisterScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}