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
import com.example.plateful.data.FullMealRepository
import com.example.plateful.ui.uiState.categoryFood.CategoryFoodListState
import com.example.plateful.ui.uiState.categoryFood.CategoryFoodState
import com.example.plateful.ui.uiState.categoryFood.FoodApiState
import com.example.plateful.ui.uiState.categoryFood.WorkerStateCategoryFood
import com.example.plateful.ui.uiState.foodDetail.FoodDetailListState
import com.example.plateful.ui.uiState.foodDetail.FullMealApiState
import com.example.plateful.ui.uiState.foodDetail.WorkerStateFoodDetail
import com.example.plateful.ui.uiState.home.CategoryApiState
import com.example.plateful.ui.uiState.home.CategoryListState
import com.example.plateful.ui.uiState.home.HomeState
import com.example.plateful.ui.uiState.home.WorkerStateHome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class PlatefulViewModel(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
    private val fullMealRepository: FullMealRepository
): ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeState())
    val homeUiState: StateFlow<HomeState> = _homeUiState.asStateFlow()

    private val _categoryFoodUiState = MutableStateFlow(CategoryFoodState())
    val categoryFoodUiState: StateFlow<CategoryFoodState> = _categoryFoodUiState.asStateFlow()

    lateinit var homeUiListState: StateFlow<CategoryListState>
    lateinit var categoryFoodUiListState: StateFlow<CategoryFoodListState>
    lateinit var foodDetailUiListState: StateFlow<FoodDetailListState>

    var categoryApiState: CategoryApiState by mutableStateOf(CategoryApiState.Loading)
        private set

    var foodApiState: FoodApiState by mutableStateOf(FoodApiState.Loading)
        private set

    var fullMealApiState: FullMealApiState by mutableStateOf(FullMealApiState.Loading)
        private set

    lateinit var wifiWorkerStateHome: StateFlow<WorkerStateHome>
    lateinit var wifiWorkerStateCategoryFood: StateFlow<WorkerStateCategoryFood>
    lateinit var wifiWorkerStateFoodDetail: StateFlow<WorkerStateFoodDetail>

    init {
        getRepoCategories()
        Log.i("vm inspection", "PlatefulViewModel init")
    }

    private fun getRepoCategories() {
        try {
            viewModelScope.launch { categoryRepository.refresh() }

            homeUiListState = categoryRepository
                .getAll()
                .map {
                    CategoryListState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = CategoryListState()
                )

            categoryApiState = CategoryApiState.Success

            wifiWorkerStateHome = categoryRepository
                .wifiWorkInfo
                .map {
                    WorkerStateHome(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerStateHome()
                )
        } catch (e: IOException) {
            categoryApiState = CategoryApiState.Error
        }
    }

    fun getRepoFoodByCategory() {
        try {
            viewModelScope.launch { foodRepository.refresh(_homeUiState.value.selectedCategory) }

            categoryFoodUiListState = foodRepository
                .getByCategory(_homeUiState.value.selectedCategory)
                .map {
                    CategoryFoodListState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = CategoryFoodListState()
                )

            foodApiState = FoodApiState.Success

            wifiWorkerStateCategoryFood = foodRepository
                .wifiWorkInfo
                .map {
                    WorkerStateCategoryFood(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerStateCategoryFood()
                )
        } catch (e: IOException) {
            foodApiState = FoodApiState.Error
        }
    }

    fun getRepoFullMeal() {
        try {
            viewModelScope.launch { fullMealRepository.refresh(_categoryFoodUiState.value.selectedFood) }

            foodDetailUiListState = fullMealRepository
                .getFullMeal(_categoryFoodUiState.value.selectedFood)
                .map {
                    FoodDetailListState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = FoodDetailListState()
                )

            fullMealApiState = FullMealApiState.Success

            wifiWorkerStateFoodDetail = fullMealRepository
                .wifiWorkInfo
                .map {
                    WorkerStateFoodDetail(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerStateFoodDetail()
                )
        } catch (e: IOException) {
            fullMealApiState = FullMealApiState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", "PlatefulViewModel cleared")
    }

    fun setSelectedCategory(category: String) {
        _homeUiState.update { state ->
            state.copy(
                selectedCategory = category
            )
        }
    }

    fun setSelectedFood(food: String) {
        _categoryFoodUiState.update { state ->
            state.copy(
                selectedFood = food
            )
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
                    val fullMealRepository = application.container.fullMealRepository
                    Instance = PlatefulViewModel(categoryRepository = categoryRepository, foodRepository = foodRepository, fullMealRepository = fullMealRepository)
                }
                Instance!!
            }
        }
    }
}