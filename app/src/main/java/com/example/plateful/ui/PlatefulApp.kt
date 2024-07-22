package com.example.plateful.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plateful.R
import com.example.plateful.navigation.AppScreen
import com.example.plateful.ui.components.navigation.NavComponent
import com.example.plateful.ui.components.navigation.NavigationDrawerContent
import com.example.plateful.ui.components.navigation.PlatefulBottomBar
import com.example.plateful.ui.components.navigation.PlatefulNavigationRail
import com.example.plateful.ui.components.navigation.PlatefulTopAppBar
import com.example.plateful.ui.util.NavigationType
import com.example.plateful.ui.viewModel.PlatefulViewModel

@Composable
fun PlatefulApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    navController: NavHostController = rememberNavController(),
    platefulViewModel: PlatefulViewModel = viewModel(factory = PlatefulViewModel.Factory)
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val topAppbarTitle = remember { mutableStateOf("") }

    val goHome: () -> Unit = {
        navController.popBackStack(
            AppScreen.Main.Home.route,
            inclusive = false
        )
    }

    val goToFavourites = {
        navController.navigate(AppScreen.Main.Favourites.route) {
            launchSingleTop = true
        }
    }

    val showBottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val showTopBarState = rememberSaveable { (mutableStateOf(true)) }

    val canNavigateBack = navController.previousBackStackEntry != null

    val navigateUp: () -> Unit = { navController.navigateUp() }

    when (backStackEntry?.destination?.route) {
        AppScreen.Main.Home.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Home.title!!)

        }

        AppScreen.Main.Favourites.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Favourites.title!!)
        }
        AppScreen.Main.CategoryFood.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(id = AppScreen.Main.CategoryFood.title!!)
        }

        else -> {
            showBottomBarState.value = false
            showTopBarState.value = false
        }
    }

    when (navigationType) {
        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(
                modifier = modifier,
                drawerContent = {
                    PermanentDrawerSheet (
                        modifier = modifier
                            .width(dimensionResource(R.dimen.drawer_width))
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        windowInsets = WindowInsets.ime
                    ) {
                        NavigationDrawerContent(
                            selectedDestination = navController.currentDestination,
                            navController = navController,
                            modifier = modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(
                                    dimensionResource(R.dimen.drawer_padding_content_horizontal),
                                    dimensionResource(R.dimen.drawer_padding_header)
                                )
                        )
                    }
                }
            ) {
                Scaffold(
                    containerColor = Color.Transparent,
                    modifier = modifier
                        .fillMaxSize(),
                    topBar = {
                        PlatefulTopAppBar(
                            title = topAppbarTitle.value,
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    }
                ) { innerPadding ->
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        platefulViewModel = platefulViewModel
                    )
                }
            }
        }

        NavigationType.BOTTOM_NAVIGATION -> {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    PlatefulTopAppBar(
                        title = topAppbarTitle.value,
                        canNavigateBack = canNavigateBack,
                        navigateUp = navigateUp
                    )
                },
                bottomBar = {
                    PlatefulBottomBar(
                        goHome = goHome,
                        goToFavourites = goToFavourites,
                        selectedDestination = navController.currentDestination
                    )
                }
            ) { innerPadding ->
                NavComponent(
                    navController = navController,
                    modifier = modifier.padding(innerPadding),
                    platefulViewModel = platefulViewModel
                )
            }
        }

        else -> {
                AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                    PlatefulNavigationRail(
                        selectedDestination = navController.currentDestination,
                        navController = navController
                    )
                }
                Scaffold (
                    containerColor = Color.Transparent,
                    topBar = {
                        PlatefulTopAppBar(
                            title = topAppbarTitle.value,
                            canNavigateBack = canNavigateBack,
                            navigateUp = navigateUp
                        )
                    },
                ) { innerPadding ->
                    // Navigation component for handling different screens.
                    NavComponent(
                        navController = navController,
                        modifier = modifier.padding(innerPadding),
                        platefulViewModel = platefulViewModel
                    )
                }

        }
    }
}