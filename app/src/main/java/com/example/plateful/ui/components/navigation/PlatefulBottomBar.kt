package com.example.plateful.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.plateful.navigation.AppScreen

/**
 * This composable function renders a bottom navigation bar for the Plateful app.
 *
 * @param selectedDestination (Optional) The currently selected navigation destination.
 * @param onHomeClick The callback function to be triggered when the Home item is clicked.
 * @param onFavouritesClick The callback function to be triggered when the Favourites item is clicked.
 */
@Composable
fun platefulBottomBar(
    goHome: () -> Unit,
    goToFavourites: () -> Unit,
    selectedDestination: NavDestination?,
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        NavigationBarItem(
            colors =
                NavigationBarItemDefaults.colors(
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                ),
            selected = selectedDestination?.route == AppScreen.Main.Home.route,
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Home.title!!),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = AppScreen.Main.Home.title!!),
                )
            },
            onClick = goHome,
        )
        NavigationBarItem(
            colors =
                NavigationBarItemDefaults.colors(
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.onSecondary,
                ),
            selected = selectedDestination?.route == AppScreen.Main.Favourites.route,
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Favourites.title!!),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = AppScreen.Main.Favourites.title!!),
                )
            },
            onClick = goToFavourites,
        )
    }
}
