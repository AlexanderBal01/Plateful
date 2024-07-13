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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import com.example.plateful.R
import com.example.plateful.navigation.AppScreen

@Composable
fun PlatefulBottomBar(
    goHome: () -> Unit,
    goToFavourites: () -> Unit,
    goToRandom: () -> Unit,
    selectedDestination: NavDestination?
) {
    NavigationBar (containerColor = MaterialTheme.colorScheme.primaryContainer) {
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
            selected = selectedDestination?.route == AppScreen.Main.Home.route,

            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Home.title!!),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = AppScreen.Main.Home.title!!)
                )
            },

            onClick = goHome
        )
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
            selected = selectedDestination?.route == AppScreen.Main.Favourites.route,

            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Favourites.title!!),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = AppScreen.Main.Favourites.title!!)
                )
            },

            onClick = goToFavourites
        )
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
            selected = selectedDestination?.route == AppScreen.Main.RandomFood.route,

            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.RandomFood.title!!),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.random),
                    contentDescription = stringResource(id = AppScreen.Main.RandomFood.title!!)
                )
            },
            onClick = goToRandom
        )
    }
}
