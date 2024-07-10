package com.example.plateful.ui.components.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.plateful.R
import com.example.plateful.navigation.AppScreen

@Composable
fun NavigationDrawerContent(
    selectedDestination: NavDestination?,
    onTabPressed: ((String) -> Unit),
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        AppScreen.Main.Home,
        AppScreen.Main.Favourites,
        AppScreen.Main.RandomFood,
        AppScreen.Main.Profile,
    )

    Column(modifier = modifier) {
        // Loop through each navItem in OverviewScreens
        navItems.forEach{ item ->

            NavigationDrawerItem(
                // Check if the current navItem is selected
                selected = selectedDestination?.route == item.route,
                label = {
                    Text(
                        text = stringResource(id = item.title!!),
                        modifier = modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header))
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
                ),
                onClick = { onTabPressed(item.route) }
            )
        }
    }
}
