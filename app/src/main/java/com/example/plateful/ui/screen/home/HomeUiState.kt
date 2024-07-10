package com.example.plateful.ui.screen.home

import androidx.work.WorkInfo
import com.example.plateful.model.Category

data class HomeState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedCategory: Category? = null
)

data class CategoryListState(
    val categoryList: List<Category> = listOf()
)

data class WorkerState(
    val workerInfo: WorkInfo? = null
)

sealed interface CategoryApiState {
    object Success: CategoryApiState
    object Error: CategoryApiState
    object Loading: CategoryApiState
}