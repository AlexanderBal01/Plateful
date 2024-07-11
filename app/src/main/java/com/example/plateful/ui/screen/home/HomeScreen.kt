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
import com.example.plateful.ui.components.home.CategoryList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit,
    homeViewModel: HomeViewModel,
) {
    val categoryOverviewState by homeViewModel.uiState.collectAsState()
    val categoryListState by homeViewModel.uiListState.collectAsState()
    val workerState by homeViewModel.wifiWorkerState.collectAsState()
    val categoryApiState = homeViewModel.categoryApiState

    Column {
        Box(modifier = modifier.fillMaxSize()) {
            when(categoryApiState) {
                is CategoryApiState.Loading ->
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(id = R.string.loading), color = MaterialTheme.colorScheme.primary, fontSize = 45.sp, textAlign = TextAlign.Center)
                    }
                is CategoryApiState.Error ->
                    Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(id = R.string.could_not_load), color = MaterialTheme.colorScheme.primary, fontSize = 45.sp, textAlign = TextAlign.Center)
                    }
                is CategoryApiState.Success ->
                    CategoryList(
                        
                        categoryListState = categoryListState,
                        onCategoryClick = onCategoryClick
                    )
            }
        }
    }
}