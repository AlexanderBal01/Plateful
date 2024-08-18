package com.example.plateful.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.plateful.PlatefulApplication
import com.example.plateful.data.CategoryRepository
import com.example.plateful.data.FoodRepository
import com.example.plateful.ui.uiState.CategoryApiState
import com.example.plateful.ui.uiState.FoodApiState
import com.example.plateful.ui.uiState.PlatefulListsState
import com.example.plateful.ui.uiState.PlatefulUiState
import com.example.plateful.ui.uiState.PlatefulWorkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * PlatefulViewModel manages the UI state and data fetching logic for the Plateful app.
 * It utilizes repositories for accessing category and food data and exposes state flows
 * for UI components to observe changes.
 */
class PlatefulViewModel(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
) : ViewModel() {
    private val platefulUiState = MutableStateFlow(PlatefulUiState())

    val uiState: StateFlow<PlatefulUiState> = platefulUiState.asStateFlow()

    lateinit var platefulUiListsState: StateFlow<PlatefulListsState>

    var categoryApiState: CategoryApiState by mutableStateOf(CategoryApiState.Loading)
        private set

    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    private lateinit var platefulWorkerState: StateFlow<PlatefulWorkerState>

    init {
        Log.i("vm inspection", "PlatefulViewModel init")
    }

    /**
     * Fetches categories from the repository and updates the UI state and state flows.
     */
    fun getRepoCategories() {
        try {
            viewModelScope.launch { categoryRepository.refresh() }

            platefulUiListsState =
                categoryRepository
                    .getAll()
                    .map {
                        platefulUiListsState.value.copy(categoryList = it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulListsState(),
                    )

            categoryApiState = CategoryApiState.Success

            platefulWorkerState =
                categoryRepository
                    .wifiWorkInfo
                    .map {
                        PlatefulWorkerState(it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulWorkerState(),
                    )
        } catch (e: IOException) {
            categoryApiState = CategoryApiState.Error
        }
    }

    /**
     * Fetches food items for the selected category from the repository and updates the UI state and state flows.
     */
    fun getRepoFoodByCategory() {
        try {
            viewModelScope.launch { foodRepository.refresh(platefulUiState.value.selectedCategory) }

            platefulUiListsState =
                foodRepository
                    .getByCategory(platefulUiState.value.selectedCategory)
                    .map {
                        platefulUiListsState.value.copy(foodList = it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulListsState(),
                    )

            foodApiState = FoodApiState.Success

            platefulWorkerState =
                foodRepository
                    .wifiWorkInfo
                    .map {
                        PlatefulWorkerState(it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulWorkerState(),
                    )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    /**
     * Fetches favourite food items from the repository and updates the UI state and state flows.
     */
    fun getRepoFoodByFavourite() {
        try {
            platefulUiListsState =
                foodRepository
                    .getFavourites()
                    .map {
                        platefulUiListsState.value.copy(favouritesList = it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulListsState(),
                    )

            foodApiState = FoodApiState.Success

            platefulWorkerState =
                foodRepository
                    .wifiWorkInfo
                    .map {
                        PlatefulWorkerState(it)
                    }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = PlatefulWorkerState(),
                    )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "PlatefulViewModel cleared")
    }

    /**
     * Updates the selected category in the UI state.
     *
     * This function modifies the `selectedCategory` property of the `_platefulUiState`
     * state flow with the provided `category` value.
     *
     * @param category The new selected category.
     */
    fun setSelectedCategory(category: String) {
        platefulUiState.update { state ->
            state.copy(
                selectedCategory = category,
            )
        }
    }

    /**
     * Sets the favourite status of a food item.
     *
     * This function updates the favourite status of a food item with the given `id` to the specified `favourite` value.
     * It also handles potential exceptions by setting the `foodApiState` to `FoodApiState.Error` in case of an `IOException`.
     *
     * @param favourite The new favourite status for the food item.
     * @param id The unique identifier of the food item.
     */
    fun setFavourite(
        favourite: Boolean,
        id: String,
    ) {
        try {
            viewModelScope.launch {
                foodRepository.setFavourite(id, favourite)
            }
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    companion object {
        /**
         * Singleton instance of the PlatefulViewModel.
         */
        private var instance: PlatefulViewModel? = null

        /**
         * Factory for creating PlatefulViewModel instances.
         *
         * This factory ensures that only a single instance of the ViewModel is created
         * and shared across the application.
         */
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    if (instance == null) {
                        val application = (this[APPLICATION_KEY] as PlatefulApplication)
                        val categoryRepository = application.container.categoryRepository
                        val foodRepository = application.container.foodRepository
                        instance = PlatefulViewModel(categoryRepository = categoryRepository, foodRepository = foodRepository)
                    }
                    instance!!
                }
            }
    }
}
