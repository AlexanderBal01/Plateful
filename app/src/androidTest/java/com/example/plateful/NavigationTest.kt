package com.example.plateful

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.plateful.ui.PlatefulApp
import com.example.plateful.ui.util.NavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    private val someCategory: String = "Beef"

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            PlatefulApp(navigationType = NavigationType.BOTTOM_NAVIGATION, navController = navController)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithTag("TopBar")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToFavourites() {
        composeTestRule.onNodeWithContentDescription("Favourites").performClick()

        composeTestRule.onNodeWithTag("TopBar").assertIsDisplayed()
    }
}