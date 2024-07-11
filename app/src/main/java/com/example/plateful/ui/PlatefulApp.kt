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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.example.plateful.ui.screen.home.HomeViewModel
import com.example.plateful.ui.util.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatefulApp(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    rootNavHostController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val topAppbarTitle = remember { mutableStateOf("") }
    val topAppBarState = rememberTopAppBarState()
    val barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)

    val showBottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val showTopBarState = rememberSaveable { (mutableStateOf(true)) }

    val rootNavBackStackEntry by rootNavHostController.currentBackStackEntryAsState()

    val canNavigateBack = rootNavHostController.previousBackStackEntry != null

    val navigateUp: () -> Unit = { rootNavHostController.navigateUp() }

    when (rootNavBackStackEntry?.destination?.route) {
        AppScreen.Main.Home.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Home.title!!)

        }

        AppScreen.Main.RandomFood.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.RandomFood.title!!)
        }

        AppScreen.Main.Favourites.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Favourites.title!!)
        }

        AppScreen.Main.FoodDetail.route -> {
            showBottomBarState.value = true
            showTopBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.FoodDetail.title!!)
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
                            selectedDestination = rootNavHostController.currentDestination,
                            navController = rootNavHostController,
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
                        .fillMaxSize()
                        .nestedScroll(barScrollBehavior.nestedScrollConnection),
                    topBar = {
                        PlatefulTopAppBar(
                            title = topAppbarTitle.value,
                            canNavigateBack = canNavigateBack,
                            barScrollBehavior = barScrollBehavior,
                            navigateUp = navigateUp
                        )
                    }
                ) { innerPadding ->
                    NavComponent(
                        navController = rootNavHostController,
                        modifier = modifier.padding(innerPadding),
                        homeViewModel = homeViewModel
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
                        barScrollBehavior = barScrollBehavior,
                        navigateUp = navigateUp
                    )
                },
                bottomBar = {
                    PlatefulBottomBar(
                        navController = rootNavHostController,
                        selectedDestination = rootNavHostController.currentDestination
                    )
                }
            ) { innerPadding ->
                NavComponent(
                    navController = rootNavHostController,
                    modifier = modifier.padding(innerPadding),
                    homeViewModel = homeViewModel
                )
            }
        }
        else -> {
                AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                    PlatefulNavigationRail(
                        selectedDestination = rootNavHostController.currentDestination,
                        navController = rootNavHostController
                    )
                }
                Scaffold (
                    containerColor = Color.Transparent,
                    topBar = {
                        PlatefulTopAppBar(
                            title = topAppbarTitle.value,
                            canNavigateBack = canNavigateBack,
                            barScrollBehavior = barScrollBehavior,
                            navigateUp = navigateUp
                        )
                    },
                ) { innerPadding ->
                    // Navigation component for handling different screens.
                    NavComponent(
                        navController = rootNavHostController,
                        modifier = modifier.padding(innerPadding),
                        homeViewModel = homeViewModel
                    )
                }

        }
    }
}