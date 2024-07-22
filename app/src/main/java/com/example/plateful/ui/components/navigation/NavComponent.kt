package com.example.plateful.ui.components.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.plateful.navigation.AppScreen
import com.example.plateful.navigation.navGraphs.mainNavGraph
import com.example.plateful.ui.viewModel.PlatefulViewModel

@Composable
fun NavComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    platefulViewModel: PlatefulViewModel
) {

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        mainNavGraph(navController, platefulViewModel)
    }
}