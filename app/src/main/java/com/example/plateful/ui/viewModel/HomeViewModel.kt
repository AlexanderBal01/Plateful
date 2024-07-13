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
import com.example.plateful.ui.screen.home.CategoryApiState
import com.example.plateful.ui.screen.home.CategoryListState
import com.example.plateful.ui.screen.home.HomeState
import com.example.plateful.ui.screen.home.WorkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val categoryRepository: CategoryRepository): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    lateinit var uiListState: StateFlow<CategoryListState>

    var categoryApiState: CategoryApiState by mutableStateOf(CategoryApiState.Loading)
        private set

    lateinit var wifiWorkerState: StateFlow<WorkerState>

    init {
        getRepoCategories()
        Log.i("vm inspection", "HomeViewModel init")
    }

    private fun getRepoCategories() {
        try {
            viewModelScope.launch { categoryRepository.refresh() }

            uiListState = categoryRepository
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

            wifiWorkerState = categoryRepository
                .wifiWorkInfo.
                map {
                    WorkerState(it)
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = WorkerState()
                )
        } catch (e: IOException) {
            categoryApiState = CategoryApiState.Error
        }
    }

    companion object {
        private var Instance: HomeViewModel? = null

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as PlatefulApplication)
                    val categoryRepository = application.container.categoryRepository
                    Instance = HomeViewModel(categoryRepository = categoryRepository)
                }
                Instance!!
            }
        }
    }
}