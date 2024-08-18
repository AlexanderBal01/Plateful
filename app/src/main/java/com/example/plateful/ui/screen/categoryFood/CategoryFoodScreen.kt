package com.example.plateful.ui.screen.categoryFood

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
import com.example.plateful.model.Food
import com.example.plateful.ui.components.categoryFood.foodList
import com.example.plateful.ui.uiState.FoodApiState
import com.example.plateful.ui.viewModel.PlatefulViewModel

/**
 * This composable function renders the screen for a specific food category.
 *
 * @param modifier (Optional) Modifier to be applied to the screen.
 * @param onFoodClick The callback function to be triggered when a food item is clicked.
 * @param platefulViewModel The PlatefulViewModel instance used to access data and state.
 */
@Composable
fun categoryFoodScreen(
    modifier: Modifier = Modifier,
    onFoodClick: (Food) -> Unit,
    platefulViewModel: PlatefulViewModel,
) {
    val categoryFoodListState by platefulViewModel.platefulUiListsState.collectAsState()
    val foodApiState = platefulViewModel.foodApiState

    Column {
        Box(modifier = modifier.fillMaxSize()) {
            when (foodApiState) {
                is FoodApiState.Loading ->
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

                is FoodApiState.Error ->
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

                is FoodApiState.Success ->
                    foodList(
                        foodListState = categoryFoodListState,
                        onFoodClick = onFoodClick,
                    )
            }
        }
    }
}
