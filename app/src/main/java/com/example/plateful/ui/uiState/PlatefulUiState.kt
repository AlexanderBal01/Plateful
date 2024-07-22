package com.example.plateful.ui.uiState

import androidx.work.WorkInfo
import com.example.plateful.model.Category
import com.example.plateful.model.Food

data class PlatefulUiState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedCategory: String = "",
    val selectedFood: String = "",
)

data class PlatefulListsState(
    val categoryList: List<Category> = listOf(),
    val foodList: List<Food> = listOf(),
    val favouritesList: List<Food> = listOf(),
)

data class PlatefulWorkerState(
    val workerInfo: WorkInfo? = null
)

sealed interface CategoryApiState {
    data object Success: CategoryApiState
    data object Error: CategoryApiState
    data object Loading: CategoryApiState
}

sealed interface FoodApiState {
    data object Success: FoodApiState
    data object Error: FoodApiState
    data object Loading: FoodApiState
}