package com.example.plateful.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.plateful.R
import com.example.plateful.ui.components.home.categoryList
import com.example.plateful.ui.uiState.CategoryApiState
import com.example.plateful.ui.viewModel.PlatefulViewModel

/**
 * This composable function renders the home screen of the Plateful app.
 *
 * @param modifier (Optional) Modifier to be applied to the screen.
 * @param onCategoryClick The callback function to be triggered when a category item is clicked.
 * @param platefulViewModel The PlatefulViewModel instance used to access data and state.
 */
@Composable
fun homeScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
    platefulViewModel: PlatefulViewModel,
) {
    val categoryListState by platefulViewModel.platefulUiListsState.collectAsState()
    val categoryApiState = platefulViewModel.categoryApiState

    Column {
        Box(modifier = modifier.fillMaxSize()) {
            when (categoryApiState) {
                is CategoryApiState.Loading ->
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            stringResource(id = R.string.loading),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                is CategoryApiState.Error ->
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            stringResource(id = R.string.could_not_load),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                is CategoryApiState.Success ->
                    categoryList(
                        categoryListState = categoryListState,
                        onCategoryClick = onCategoryClick,
                    )
            }
        }
    }
}
