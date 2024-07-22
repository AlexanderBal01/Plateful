package com.example.plateful.ui.uiState.foodDetail

import androidx.work.WorkInfo
import com.example.plateful.model.FullMeal

data class FoodDetailListState(
    val fullMeal: List<FullMeal> = listOf()
)

data class WorkerStateFoodDetail(
    val workerInfo: WorkInfo? = null
)

sealed interface FullMealApiState {
    object Success: FullMealApiState
    object Error: FullMealApiState
    object Loading: FullMealApiState
}