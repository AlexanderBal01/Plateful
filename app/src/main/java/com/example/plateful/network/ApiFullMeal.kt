package com.example.plateful.network

import com.example.plateful.model.FullMeal
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ApiFullMeal(
    @SerializedName("idMeal")
    val id: String,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strCategory")
    val category: String,
    @SerializedName("strArea")
    val area: String,
    @SerializedName("strInstructions")
    val instructions: String,
    @SerializedName("strMealThumb")
    val imgLocation: String,
)

data class ApiFullMealList(
    @SerializedName("meals")
    val meals: List<ApiFullMeal>
)

fun Flow<ApiFullMealList>.asDomainObject(): Flow<List<FullMeal>> {
    return this.map {
        it.meals.asDomainObjects()
    }
}

fun List<ApiFullMeal>.asDomainObjects(): List<FullMeal> {
    return this.map {
        FullMeal(
            id = it.id,
            name = it.name,
            category = it.category,
            area = it.area,
            instructions = it.instructions,
            image = it.imgLocation,
        )
    }
}