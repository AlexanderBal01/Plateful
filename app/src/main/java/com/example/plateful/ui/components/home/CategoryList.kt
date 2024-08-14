package com.example.plateful.ui.components.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.plateful.ui.uiState.PlatefulListsState

/**
 * This composable function renders a vertical list of category items on the home screen.
 *
 * @param categoryListState - The state object containing the list of categories to be displayed.
 * @param onCategoryClick - A callback function that is triggered when a category item is clicked.
 */
@Composable
fun CategoryList(
    categoryListState: PlatefulListsState,
    onCategoryClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(categoryListState.categoryList) { category ->
            CategoryItem(name = category.name, img = category.imageUrl, onCategoryClick = onCategoryClick)
        }
    }
}