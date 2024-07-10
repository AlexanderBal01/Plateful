package com.example.plateful.ui.screen.home

import androidx.work.WorkInfo
import com.example.plateful.model.Food

data class HomeState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0
)

data class FoodListState(
    val foodList: List<Food> = listOf()
)

data class WorkerState(
    val workerInfo: WorkInfo? = null
)

sealed class FoodApiState {
    object Success: FoodApiState
    object Error: FoodApiState
    object Loading: FoodApiState
}