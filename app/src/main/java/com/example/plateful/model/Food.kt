package com.example.plateful.model

/**
 * Data class representing a food item in the Plateful app.
 *
 * @property id The unique identifier for the food item.
 * @property name The name of the food item.
 * @property imageUrl The URL of the image associated with the food item.
 * @property favourite Indicates whether the food item is marked as a favourite.
 * @property category The category to which the food item belongs.
 */
data class Food(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var favourite: Boolean = false,
    var category: String = "",
)
