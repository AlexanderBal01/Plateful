package com.example.plateful.network

import com.example.plateful.model.Food
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Data class representing a food response from the Plateful API.
 *
 * This class maps the JSON structure of a food response to corresponding properties.
 *
 * @property id The unique identifier of the food.
 * @property name The name of the food.
 * @property imgLocation The URL of the food image.
 */
data class ApiFood(
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val imgLocation: String,
    @SerializedName("idMeal")
    val id: String,
)

/**
 * Data class representing a list of food responses from the Plateful API.
 *
 * This class wraps a list of `ApiFood` objects representing individual food items.
 *
 * @property meals A list of `ApiFood` objects.
 */
data class ApiFoodList(
    @SerializedName("meals")
    val meals: List<ApiFood>,
)

/**
 * Converts a flow of `ApiFoodList` to a flow of `List<Food>`.
 *
 * This extension function iterates over the flow of `ApiFoodList` and transforms each list
 * into a list of `Food` objects by calling the `asDomainObjects` function on the inner list.
 *
 * @receiver A flow of `ApiFoodList` objects.
 * @return A flow of `List<Food>` objects representing the converted data.
 */
fun Flow<ApiFoodList>.asDomainObject(): Flow<List<Food>> =
    this.map {
        it.meals.asDomainObjects()
    }

/**
 * Converts a list of `ApiFood` objects to a list of `Food` objects.
 *
 * This extension function iterates over the list of `ApiFood` and creates a new list of
 * `Food` objects by mapping each `ApiFood` to its corresponding `Food` representation.
 *
 * Note that the `favourite` property of `Food` is set to `false` by default in this conversion.
 *
 * @receiver A list of `ApiFood` objects.
 * @return A list of `Food` objects representing the converted data.
 */
fun List<ApiFood>.asDomainObjects(): List<Food> =
    this.map {
        Food(
            id = it.id,
            name = it.name,
            imageUrl = it.imgLocation,
            favourite = false,
        )
    }
