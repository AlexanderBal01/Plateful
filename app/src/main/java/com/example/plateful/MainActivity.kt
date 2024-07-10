package com.example.plateful

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.plateful.ui.PlatefulApp
import com.example.plateful.ui.theme.PlatefulTheme
import com.example.plateful.ui.util.NavigationType

class MainActivity : ComponentActivity() {

    private var isAuthenticated = false

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlatefulTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val windowSize = calculateWindowSizeClass(activity = this)

                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> {
                            PlatefulApp(modifier = Modifier.padding(innerPadding), isAuthenticated = isAuthenticated, navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                        WindowWidthSizeClass.Medium -> {
                            PlatefulApp(modifier = Modifier.padding(innerPadding), isAuthenticated = isAuthenticated, navigationType = NavigationType.NAVIGATION_RAIL)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            PlatefulApp(modifier = Modifier.padding(innerPadding), isAuthenticated = isAuthenticated, navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER)
                        }
                        else -> {
                            PlatefulApp(modifier = Modifier.padding(innerPadding), isAuthenticated = isAuthenticated, navigationType = NavigationType.BOTTOM_NAVIGATION)
                        }
                        }
                }
            }
        }
    }
}