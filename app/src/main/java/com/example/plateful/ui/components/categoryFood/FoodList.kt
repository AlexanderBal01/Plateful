package com.example.plateful.ui.components.categoryFood

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.plateful.model.Food
import com.example.plateful.ui.uiState.PlatefulListsState

/**
 * This composable function renders a vertical list of food items.
 *
 * @param foodListState - The state object containing the list of food items to be displayed.
 * @param onFoodClick - A callback function that is triggered when a food item is clicked.
 */
@Composable
fun foodList(
    foodListState: PlatefulListsState,
    onFoodClick: (Food) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(foodListState.foodList) { food ->
            foodItem(food = food, onFoodClick = onFoodClick)
        }
    }
}
