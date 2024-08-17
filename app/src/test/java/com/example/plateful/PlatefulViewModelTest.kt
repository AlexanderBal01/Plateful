package com.example.plateful

import com.example.plateful.fake.FakeApiCategoryRepositroy
import com.example.plateful.fake.FakeApiFoodRepository
import com.example.plateful.ui.viewModel.PlatefulViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class PlatefulViewModelTest {
    private val someCategoryName = "Beef"

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun settingNameChangesState() {
        val viewModel = PlatefulViewModel(
            foodRepository = FakeApiFoodRepository(),
            categoryRepository = FakeApiCategoryRepositroy()
        )
        viewModel.setSelectedCategory(someCategoryName)
        Assert.assertEquals(viewModel.uiState.value.selectedCategory, someCategoryName)
    }
}

class TestDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}