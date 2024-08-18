package com.example.plateful.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.plateful.navigation.AppScreen

/**
 * This composable function renders a navigation rail for the Plateful app.
 *
 * @param modifier (Optional) Modifier to be applied to the NavigationRail.
 * @param selectedDestination (Optional) The currently selected navigation destination.
 * @param navController The NavController instance used for navigation.
 */
@Composable
fun platefulNavigationRail(
    modifier: Modifier = Modifier,
    selectedDestination: NavDestination?,
    navController: NavController,
) {
    NavigationRail(
        modifier =
            modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        NavigationRailItem(
            selected = selectedDestination?.route == AppScreen.Main.Home.route,
            onClick = {
                navController.navigate(AppScreen.Main.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = AppScreen.Main.Home.title!!),
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            },
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Home.title!!),
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            },
        )
        NavigationRailItem(
            selected = selectedDestination?.route == AppScreen.Main.Favourites.route,
            onClick = {
                navController.navigate(AppScreen.Main.Favourites.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(id = AppScreen.Main.Favourites.title!!),
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            },
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Favourites.title!!),
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            },
        )
    }
}
