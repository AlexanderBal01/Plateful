package com.example.plateful.ui.components.foodDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.plateful.R
import com.example.plateful.model.FullMeal

@Composable
fun FullMealItem(
    modifier: Modifier = Modifier,
    fullMeal: FullMeal
) {
    Column {
        Text(
            text = fullMeal.name,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge
        )

        AsyncImage(
            model = fullMeal.image,
            contentDescription = stringResource(id = R.string.img_category),
            modifier = modifier.width(60.dp)
        )

    }
}