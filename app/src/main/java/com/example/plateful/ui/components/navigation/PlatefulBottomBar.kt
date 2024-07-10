package com.example.plateful.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.plateful.navigation.AppScreen

@Composable
fun PlatefulBottomBar(
    navController: NavHostController,
    currentRoute: NavDestination?
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
        AppScreen.Main.Profile,
    )

    NavigationBar {
        navItems.forEach { item ->

            NavigationBarItem(
                selected = currentRoute?.route == item.route,

                label = {
                    Text(
                        text = stringResource(id = item.title!!)
                    )
                },
                icon = {
                    Icon(
                        imageVector = (if (item.route == currentRoute?.route) item.selectedIcon else item.unselectedIcon)!!,
                        contentDescription = stringResource(id = item.title!!)
                    )
                },

                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}