package com.example.plateful.network
import com.example.plateful.model.Category
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Data class representing a category response from the Plateful API.
 *
 * This class maps the JSON structure of a category response to corresponding properties.
 *
 * @property id The unique identifier of the category.
 * @property name The name of the category.
 * @property imageLocation The URL of the category image.
 * @property description A description of the category.
 */
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

/**
 * Data class representing a list of categories response from the Plateful API.
 *
 * This class wraps a list of `ApiCategory` objects representing individual categories.
 *
 * @property category A list of `ApiCategory` objects.
 */
data class ApiCategoryList(
    @SerializedName("categories")
    val category: List<ApiCategory>
)

/**
 * Converts a flow of `ApiCategoryList` to a flow of `List<Category>`.
 *
 * This extension function iterates over the flow of `ApiCategoryList` and transforms each list
 * into a list of `Category` objects by calling the `asDomainObjects` function on the inner list.
 *
 * @receiver A flow of `ApiCategoryList` objects.
 * @return A flow of `List<Category>` objects representing the converted data.
 */
fun Flow<ApiCategoryList>.asDomainObject(): Flow<List<Category>> {
    return this.map {
        it.category.asDomainObjects()
    }

}

/**
 * Converts a list of `ApiCategory` objects to a list of `Category` objects.
 *
 * This extension function iterates over the list of `ApiCategory` and creates a new list of
 * `Category` objects by mapping each `ApiCategory` to its corresponding `Category` representation.
 *
 * @receiver A list of `ApiCategory` objects.
 * @return A list of `Category` objects representing the converted data.
 */
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