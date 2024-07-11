package com.example.plateful.network

import com.example.plateful.model.Food
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ApiFood (
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val imgLocation: String,
    @SerializedName("idMeal")
    val id: String
)

data class ApiFoodList(
    @SerializedName("meals")
    val meals: List<ApiFood>
)

fun Flow<ApiFoodList>.asDomainObject(): Flow<List<Food>> {
    return this.map {
        it.meals.asDomainObjects()
    }
}

fun List<ApiFood>.asDomainObjects(): List<Food> {
    return this.map {
        Food(
            id = it.id,
            name = it.name,
            imageUrl = it.imgLocation,
            favourite = false
        )
    }
}