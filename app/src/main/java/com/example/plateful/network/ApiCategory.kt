package com.example.plateful.network
import com.example.plateful.model.Category
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class ApiCategory(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val imageLocation: String,
    @SerializedName("strCategoryDescription")
    val description: String
)

data class ApiCategoryList(
    @SerializedName("categories")
    val category: List<ApiCategory>
)


fun Flow<ApiCategoryList>.asDomainObject(): Flow<List<Category>> {
    return this.map {
        it.category.asDomainObjects()
    }

}

fun List<ApiCategory>.asDomainObjects(): List<Category> {
    return this.map {
        Category(
            id = it.id,
            name = it.name,
            imageUrl = it.imageLocation,
            description = it.description
        )
    }
}