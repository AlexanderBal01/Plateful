package com.example.plateful.ui.components.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.plateful.R

/**
 * This composable function renders a single category item on the home screen.
 *
 * @param modifier - Modifier to be applied to the composable. (Optional)
 * @param name - The name of the category.
 * @param img - The URL of the image representing the category.
 * @param onCategoryClick - A callback function that is triggered when the category item is clicked.
 */
@Composable
fun categoryItem(
    modifier: Modifier = Modifier,
    name: String,
    img: String,
    onCategoryClick: (String) -> Unit,
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
                ).clickable { onCategoryClick(name) },
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
                model = img,
                contentDescription = stringResource(id = R.string.img_category),
            )

            // Text content
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 35.sp,
                style = MaterialTheme.typography.bodyLarge,
            )

            // Right arrow icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.navigate_to_category_item),
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(39.dp), // Used dp instead of dimensionResource for clarity
            )
        }
    }
}
