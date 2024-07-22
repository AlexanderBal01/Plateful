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
import com.example.plateful.ui.components.categoryFood.FoodList
import com.example.plateful.ui.uiState.categoryFood.FoodApiState
import com.example.plateful.ui.viewModel.PlatefulViewModel

@Composable
fun CategoryFoodScreen(
    modifier: Modifier = Modifier,
    onFoodClick: (String) -> Unit,
    platefulViewModel: PlatefulViewModel
) {
    val categoryFoodListState by platefulViewModel.categoryFoodUiListState.collectAsState()
    val foodApiState = platefulViewModel.foodApiState

    Column {
        Box(modifier = modifier.fillMaxSize()) {
            when (foodApiState) {
                is FoodApiState.Loading ->
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(id = R.string.loading),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                is FoodApiState.Error ->
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(id = R.string.could_not_load),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 45.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                is FoodApiState.Success ->
                    FoodList(
                        foodListState = categoryFoodListState,
                        onFoodClick = onFoodClick
                    )
            }
        }
    }
}