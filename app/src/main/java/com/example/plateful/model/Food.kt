package com.example.plateful.model

data class Food(
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var favourite: Boolean = false,
    var category: String = ""
)
