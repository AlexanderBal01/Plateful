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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PlatefulViewModel(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository
): ViewModel() {
    private val _platefulUiState = MutableStateFlow(PlatefulUiState())

    lateinit var platefulUiListsState: StateFlow<PlatefulListsState>

    var categoryApiState: CategoryApiState by mutableStateOf(CategoryApiState.Loading)
        private set

    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    private lateinit var platefulWorkerState: StateFlow<PlatefulWorkerState>

    init {
        Log.i("vm inspection", "PlatefulViewModel init")
    }

    fun getRepoCategories() {
        try {
            viewModelScope.launch { categoryRepository.refresh() }

            platefulUiListsState = categoryRepository
                .getAll()
                .map {
                    platefulUiListsState.value.copy(categoryList = it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulListsState()
                )

            categoryApiState = CategoryApiState.Success

            platefulWorkerState = categoryRepository
                .wifiWorkInfo
                .map {
                    PlatefulWorkerState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulWorkerState()
                )
        } catch (e: IOException) {
            categoryApiState = CategoryApiState.Error
        }
    }

    fun getRepoFoodByCategory() {
        try {
            viewModelScope.launch { foodRepository.refresh(_platefulUiState.value.selectedCategory) }

            platefulUiListsState = foodRepository
                .getByCategory(_platefulUiState.value.selectedCategory)
                .map {
                    platefulUiListsState.value.copy(foodList = it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulListsState()
                )

            foodApiState = FoodApiState.Success

             platefulWorkerState = foodRepository
                .wifiWorkInfo
                .map {
                    PlatefulWorkerState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulWorkerState()
                )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    fun getRepoFoodByFavourite() {
        try {
            platefulUiListsState = foodRepository
                .getFavourites()
                .map {
                    platefulUiListsState.value.copy(favouritesList = it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulListsState()
                )

            foodApiState = FoodApiState.Success

            platefulWorkerState = foodRepository
                .wifiWorkInfo
                .map {
                    PlatefulWorkerState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = PlatefulWorkerState()
                )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "PlatefulViewModel cleared")
    }

    fun setSelectedCategory(category: String) {
        _platefulUiState.update { state ->
            state.copy(
                selectedCategory = category
            )
        }
    }

    fun setFavourite(favourite: Boolean, id: String) {
        try {
            viewModelScope.launch {
                foodRepository.setFavourite(id, favourite)
            }
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    companion object {
        private var Instance: PlatefulViewModel? = null

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as PlatefulApplication)
                    val categoryRepository = application.container.categoryRepository
                    val foodRepository = application.container.foodRepository
                    Instance = PlatefulViewModel(categoryRepository = categoryRepository, foodRepository = foodRepository)
                }
                Instance!!
            }
        }
    }
}