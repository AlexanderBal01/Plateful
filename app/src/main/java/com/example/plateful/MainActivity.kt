package com.example.plateful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.plateful.ui.platefulApp
import com.example.plateful.ui.theme.platefulTheme
import com.example.plateful.ui.util.NavigationType

/**
 * The main activity of the Plateful application.
 *
 * This activity serves as the entry point for the app and is responsiblefor setting up the main UI.
 * It typically hosts a single fragment or a Jetpack Compose navigation component.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is starting.
     *
     * This method is responsible for initializing the activity and setting up its content view.
     * It typically inflates the main layout, sets up any necessary UI components, and handles
     * initial setup tasks.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shutdown then this Bundle contains the data it most recently supplied in
     * {@link #onSaveInstanceState}.  **Note: Otherwise it is null.**
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            platefulTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onSecondary)) { innerPadding ->
                    val windowSize = calculateWindowSizeClass(activity = this)

                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            platefulApp(modifier = Modifier.padding(innerPadding), navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            platefulApp(modifier = Modifier.padding(innerPadding), navigationType = NavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            platefulApp(modifier = Modifier, navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            platefulApp(modifier = Modifier.padding(innerPadding), navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                    }
                }
            }
        }
    }
}
