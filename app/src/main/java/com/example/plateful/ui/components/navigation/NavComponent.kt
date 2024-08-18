package com.example.plateful.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.plateful.navigation.AppScreen
import com.example.plateful.navigation.navGraphs.mainNavGraph
import com.example.plateful.ui.viewModel.PlatefulViewModel

/**
 * The main navigation component for the Plateful app.
 *
 * @param navController The NavHostController to be used for navigation.
 * @param modifier Modifier to be applied to the NavHost.
 * @param platefulViewModel The PlatefulViewModel instance.
 */
@Composable
fun navComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    platefulViewModel: PlatefulViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
        modifier = modifier,
    ) {
        mainNavGraph(navController, platefulViewModel)
    }
}
