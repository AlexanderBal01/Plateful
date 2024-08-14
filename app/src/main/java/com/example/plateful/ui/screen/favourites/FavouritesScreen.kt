package com.example.plateful.ui.screen.favourites

import androidx.compose.foundation.layout.Arrangement
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
import com.example.plateful.ui.components.favourites.FavouritesList
import com.example.plateful.ui.viewModel.PlatefulViewModel

/**
 * This composable function renders the screen for displaying favourited foods.
 *
 * @param modifier (Optional) Modifier to be applied to the screen.
 * @param onFoodClick The callback function to be triggered when a food item is clicked.
 * @param platefulViewModel The PlatefulViewModel instance used to access data and state.
 */
@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    onFoodClick: (Food) -> Unit,
    platefulViewModel: PlatefulViewModel
) {
    val platefulListsState by platefulViewModel.platefulUiListsState.collectAsState()

    val favouritesList = platefulListsState.favouritesList
    Column {
        if (favouritesList.isEmpty()) {
            Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(stringResource(id = R.string.no_favourites), color = MaterialTheme.colorScheme.primary, fontSize = 45.sp, textAlign = TextAlign.Center, lineHeight = 50.sp)
            }
        } else {
            FavouritesList(modifier, favouritesList, onFoodClick)
        }
    }

}