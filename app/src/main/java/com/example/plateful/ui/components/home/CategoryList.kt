package com.example.plateful.ui.components.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.example.plateful.ui.uiState.PlatefulListsState

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