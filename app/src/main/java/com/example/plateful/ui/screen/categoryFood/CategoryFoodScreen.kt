package com.example.plateful.ui.screen.categoryFood

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoryFoodScreen(
    modifier: Modifier = Modifier,
    onFoodClick: (String) -> Unit
) {
    Text(text = "Beef")
}