package com.example.plateful

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.plateful.ui.platefulApp
import com.example.plateful.ui.util.NavigationType
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            platefulApp(navigationType = NavigationType.BOTTOM_NAVIGATION, navController = navController)
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
        composeTestRule.onNodeWithText("Favourites").performClick()

        composeTestRule.onNodeWithTag("TopBar").assertIsDisplayed()
    }
}
