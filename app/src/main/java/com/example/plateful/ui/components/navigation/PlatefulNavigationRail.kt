package com.example.plateful.ui.components.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.plateful.navigation.AppScreen

@Composable
fun PlatefulNavigationRail(
    modifier: Modifier,
    selectedDestination: NavDestination?,
    onTabPressed: (String) -> Unit
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
        AppScreen.Main.Profile,
    )

    NavigationRail (modifier = modifier) {
        navItems.forEach { item ->
            NavigationRailItem(
                selected = selectedDestination?.route == item.route,
                onClick = { onTabPressed(item.route) },
                icon = {
                    Icon(
                        imageVector = ((if (item.route == selectedDestination?.route) item.selectedIcon else item.unselectedIcon)!!),
                        contentDescription = stringResource(id = item.title!!))
                }
            )
        }
    }
}
