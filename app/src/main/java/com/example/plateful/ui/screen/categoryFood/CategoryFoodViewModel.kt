package com.example.plateful.ui.screen.categoryFood

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
import com.example.plateful.data.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class CategoryFoodViewModel(private val foodRepository: FoodRepository): ViewModel() {
    private val _uiState = MutableStateFlow(CategoryFoodState())
    val uiState: StateFlow<CategoryFoodState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<FoodListState>

    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {
        getRepoCategoryFood()
        Log.i("vm inspection", "CategoryFoodViewModel init")
    }

    private fun getRepoCategoryFood() {
        try {
            viewModelScope.launch { foodRepository.refresh(uiState.value.selectedCategory!!) }

            uiListState = foodRepository
                .getByCategory(uiState.value.selectedCategory!!)
                .map {
                    FoodListState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = FoodListState()
                )

            foodApiState = FoodApiState.Success

            wifiWorkerState = foodRepository
                .wifiWorkInfo
                .map {
                    WorkerState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerState()
                )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    fun setSelectedCategory(category: String) {
        _uiState.update { state ->
            state.copy(
                selectedCategory = category
            )
        }
    }

    companion object {
        private var Instance: CategoryFoodViewModel? = null

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as PlatefulApplication)
                    val foodRepository = application.container.foodRepository
                    Instance = CategoryFoodViewModel(foodRepository)
                }
                Instance!!
            }
        }
    }
}