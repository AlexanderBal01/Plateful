package com.example.plateful.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
    selectedDestination: NavDestination?
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
    )

    NavigationBar (containerColor = MaterialTheme.colorScheme.primaryContainer) {
        navItems.forEach { item ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary
                ),
                selected = selectedDestination?.route == item.route,

                label = {
                    Text(
                        text = stringResource(id = item.title!!),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.selectedIcon!!,
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