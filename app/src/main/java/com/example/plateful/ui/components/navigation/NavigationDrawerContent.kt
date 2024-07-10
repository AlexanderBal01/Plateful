package com.example.plateful.ui.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.plateful.navigation.AppScreen

@Composable
fun NavigationDrawerContent(
    navController: NavHostController,
    selectedDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
    )

    Column(modifier = modifier.fillMaxHeight()) {
        // Loop through each navItem in OverviewScreens
        navItems.forEach{ item ->

            NavigationDrawerItem(
                // Check if the current navItem is selected
                selected = selectedDestination?.route == item.route,
                label = {
                    Text(
                        text = stringResource(id = item.title!!),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.selectedIcon!!,
                        contentDescription = item.route,
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,


                ),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
