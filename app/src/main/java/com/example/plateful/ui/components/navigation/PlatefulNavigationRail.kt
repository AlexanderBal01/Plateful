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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.plateful.R
import com.example.plateful.navigation.AppScreen

@Composable
fun PlatefulNavigationRail(
    modifier: Modifier = Modifier,
    selectedDestination: NavDestination?,
    navController: NavController,
) {

    NavigationRail (modifier = modifier
        .fillMaxHeight()
        .background(MaterialTheme.colorScheme.primaryContainer)) {
        NavigationRailItem(
            selected = selectedDestination?.route == AppScreen.Main.Home.route,
            onClick = {
                navController.navigate(AppScreen.Main.Home.route)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = AppScreen.Main.Home.title!!),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Home.title!!),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
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
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.Favourites.title!!),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        )
        NavigationRailItem(
            selected = selectedDestination?.route == AppScreen.Main.RandomFood.route,
            onClick = {
                navController.navigate(AppScreen.Main.RandomFood.route)
            },
            icon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.random),
                    contentDescription = stringResource(id = AppScreen.Main.RandomFood.title!!),
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            label = {
                Text(
                    text = stringResource(id = AppScreen.Main.RandomFood.title!!),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        )
    }
}
