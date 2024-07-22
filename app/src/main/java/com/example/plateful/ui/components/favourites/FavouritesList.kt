package com.example.plateful.ui.components.favourites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.plateful.model.Food
import com.example.plateful.ui.components.categoryFood.FoodItem

@Composable
fun FavouritesList(
    modifier: Modifier = Modifier,
    favouriteList: List<Food>,
    onFoodClick: (Food) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(favouriteList) { food ->
            FoodItem(food = food, onFoodClick = onFoodClick)
        }
    }
}