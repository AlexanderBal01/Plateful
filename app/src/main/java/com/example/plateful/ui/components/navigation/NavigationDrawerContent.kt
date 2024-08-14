package com.example.plateful.ui.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.plateful.navigation.AppScreen

/**
 * This composable function renders the content for the navigation drawer of the Plateful app.
 *
 * @param navController The NavController instance used for navigation.
 * @param selectedDestination The currently selected navigation destination.
 * @param modifier (Optional) Modifier to be applied to the NavigationDrawerContent.
 */
@Composable
fun NavigationDrawerContent(
    navController: NavController,
    selectedDestination: NavDestination?,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxHeight()) {
        NavigationDrawerItem(
            selected = selectedDestination?.route == AppScreen.Main.Home.route,
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Home.title!!),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = AppScreen.Main.Home.route,
                )
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = {
                navController.navigate(AppScreen.Main.Home.route)
            }
        )
        NavigationDrawerItem(
            selected = selectedDestination?.route == AppScreen.Main.Favourites.route,
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Favourites.title!!),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = AppScreen.Main.Favourites.route,
                )
            },
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
            ),
            onClick = {
                navController.navigate(AppScreen.Main.Favourites.route)
            }
        )
    }
}
