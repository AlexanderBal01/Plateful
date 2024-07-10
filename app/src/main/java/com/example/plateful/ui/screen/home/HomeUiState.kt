package com.example.plateful.ui.screen.home

import com.example.plateful.model.Food

data class HomeState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0
)

data class FoodListState(
    val foodList: List<Food> = listOf()
)