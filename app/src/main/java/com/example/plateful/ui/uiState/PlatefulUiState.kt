package com.example.plateful.ui.uiState

import androidx.work.WorkInfo
import com.example.plateful.model.Category
import com.example.plateful.model.Food

/**
 * This file defines data classes and interfaces representing the various UI states
 * used within the Plateful application.
 */

/**
 * Data class representing the overall UI state of the Plateful app.
 *
 * @property scrollActionIdx: Index of the last scroll action performed (optional)
 * @property scrollToItemIndex: Index of the item to scroll to (optional)
 * @property selectedCategory: Currently selected category (empty string if none)
 * @property selectedFood: Currently selected food item (empty string if none)
 */
data class PlatefulUiState(
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
    val selectedCategory: String = "",
    val selectedFood: String = "",
)

/**
 * Data class representing the state of various lists displayed within the app.
 *
 * @property categoryList: List of available categories
 * @property foodList: List of available food items
 * @property favouritesList: List of favourited food items
 */
data class PlatefulListsState(
    val categoryList: List<Category> = listOf(),
    val foodList: List<Food> = listOf(),
    val favouritesList: List<Food> = listOf(),
)

/**
 * Data class representing the state of the background worker used
 * for fetching data.
 *
 * @property workerInfo: Information about the worker's execution status (optional)
 */
data class PlatefulWorkerState(
    val workerInfo: WorkInfo? = null
)

/**
 * Sealed interface representing the possible API states for category data retrieval.
 */
sealed interface CategoryApiState {
    data object Success: CategoryApiState
    data object Error: CategoryApiState
    data object Loading: CategoryApiState
}

/**
 * Sealed interface representing the possible API states for food data retrieval.
 */
sealed interface FoodApiState {
    data object Success: FoodApiState
    data object Error: FoodApiState
    data object Loading: FoodApiState
}