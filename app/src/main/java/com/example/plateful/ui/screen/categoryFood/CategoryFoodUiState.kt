package com.example.plateful.ui.screen.categoryFood

import androidx.work.WorkInfo
import com.example.plateful.model.Food

data class CategoryFoodState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedFood: String? = "",
    val selectedCategory: String? = ""
)

data class FoodListState(
    val foodList: List<Food> = listOf()
)

data class WorkerState(
    val workerInfo: WorkInfo? = null
)

sealed interface FoodApiState {
    object Success: FoodApiState
    object Error: FoodApiState
    object Loading: FoodApiState
}