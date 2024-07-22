package com.example.plateful.ui.uiState.categoryFood

import androidx.work.WorkInfo
import com.example.plateful.model.Food

data class CategoryFoodState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedFood: String = ""
)

data class CategoryFoodListState(
    val foodList: List<Food> = listOf()
)

data class WorkerStateCategoryFood(
    val workerInfo: WorkInfo? = null
)

sealed interface FoodApiState {
    object Success: FoodApiState
    object Error: FoodApiState
    object Loading: FoodApiState
}