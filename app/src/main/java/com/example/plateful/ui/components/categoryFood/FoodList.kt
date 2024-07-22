package com.example.plateful.ui.components.categoryFood

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.plateful.model.Food
import com.example.plateful.ui.uiState.PlatefulListsState

@Composable
fun FoodList(
    foodListState: PlatefulListsState,
    onFoodClick: (Food) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(foodListState.foodList) { food ->
            FoodItem(food = food, onFoodClick = onFoodClick)
        }
    }
}