package com.example.plateful.ui.components.categoryFood

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.plateful.ui.screen.categoryFood.FoodListState

@Composable
fun FoodList(
    foodListState: FoodListState,
    onFoodClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(foodListState.foodList) { food ->
            FoodItem(name = food.name, img = food.imageUrl, id = food.id, onFoodClick = onFoodClick)
        }
    }
}