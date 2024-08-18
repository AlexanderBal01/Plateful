package com.example.plateful.ui.components.categoryFood

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.plateful.R
import com.example.plateful.model.Food

/**
 * This composable function renders a single food item in a list.
 *
 * @param modifier - Modifier to be applied to the composable.
 * @param food - The Food data object representing the food item.
 * @param onFoodClick - A callback function that is triggered when the food item is clicked.
 */
@Composable
fun foodItem(
    modifier: Modifier = Modifier,
    food: Food,
    onFoodClick: (Food) -> Unit,
) {
    Card(
        modifier =
            modifier
                .padding(dimensionResource(R.dimen.card_outer_padding))
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec =
                        spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium,
                        ),
                ).clickable {
                    onFoodClick(food)
                },
        // Use clickable for better semantics
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .padding(dimensionResource(R.dimen.card_padding))
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = food.imageUrl,
                contentDescription = stringResource(id = R.string.img_category),
                modifier = modifier.width(60.dp),
            )

            Column(modifier = modifier.width(dimensionResource(id = R.dimen.text_column_width))) {
                Text(
                    text = food.name,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Icon(
                imageVector = if (food.favourite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = stringResource(R.string.navigate_to_category_item),
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(39.dp), // Used dp instead of dimensionResource for clarity
            )
        }
    }
}
