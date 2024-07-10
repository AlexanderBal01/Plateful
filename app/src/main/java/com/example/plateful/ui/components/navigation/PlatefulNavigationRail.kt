package com.example.plateful.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.plateful.navigation.AppScreen

@Composable
fun PlatefulNavigationRail(
    modifier: Modifier = Modifier,
    selectedDestination: NavDestination?,
    navController: NavHostController,
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
    )

    NavigationRail (modifier = modifier.fillMaxHeight().background(MaterialTheme.colorScheme.onSecondary)) {
        navItems.forEach { item ->
            NavigationRailItem(
                selected = selectedDestination?.route == item.route,
                onClick = { navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = {
                    Icon(
                        imageVector = (if (item.route == selectedDestination?.route) item.selectedIcon else item.unselectedIcon)!!,
                        contentDescription = stringResource(id = item.title!!))
                },
                label = {
                    Text(text = stringResource(id = item.title!!))
                }
            )
        }
    }
}
