package com.example.plateful.model

/**
 * Data class representing a food category in the Plateful app.
 *
 * @property id The unique identifier for the category.
 * @property name The name of the category.
 * @property imageUrl The URL of the image associated with the category.
 * @property description A brief description of the category.
 */
data class Category(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var description: String = "",
)