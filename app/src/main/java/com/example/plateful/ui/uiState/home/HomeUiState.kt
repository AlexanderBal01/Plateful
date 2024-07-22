package com.example.plateful.ui.uiState.home

import androidx.work.WorkInfo
import com.example.plateful.model.Category

data class HomeState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedCategory: String = ""
)

data class CategoryListState(
    val categoryList: List<Category> = listOf()
)

data class WorkerStateHome(
    val workerInfo: WorkInfo? = null
)

sealed interface CategoryApiState {
    data object Success: CategoryApiState
    data object Error: CategoryApiState
    data object Loading: CategoryApiState
}