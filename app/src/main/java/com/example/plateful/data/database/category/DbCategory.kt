package com.example.plateful.data.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plateful.model.Category

/**
 * Entity class representing a category in the local database.
 *
 * @property id The unique identifier for the category, serving as the primary key.
 * @property name The name of the category.
 * @property description A brief description of the category.
 * @property img The URL of the image associated with the category.
 */
@Entity(tableName = "category")
data class DbCategory(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val img: String = "",
)

/**
 * Extension function to convert a [DbCategory] entity to a [Category] domain model object.
 *
 * @return A [Category] object representing the same data as the [DbCategory].
 */
fun DbCategory.asDomainCategoryObject(): Category =
    Category(
        id = this.id,
        name = this.name,
        imageUrl = this.img,
        description = this.description,
    )

/**
 * Extension function to convert a [Category] domain model object to a [DbCategory] entity.
 *
 * @return A [DbCategory] object representing the same data as the [Category].
 */
fun Category.asDbCategoryObject(): DbCategory =
    DbCategory(
        id = this.id,
        name = this.name,
        img = this.imageUrl,
        description = this.description,
    )

/**
 * Extension function to convert a list of [DbCategory] entities to a list of [Category] domain model objects.
 *
 * @return A list of [Category] objects representing the same data as the list of [DbCategory] entities.
 */
fun List<DbCategory>.asDomainCategoryObjects(): List<Category> =
    this.map {
        Category(
            id = it.id,
            name = it.name,
            imageUrl = it.img,
            description = it.description,
        )
    }
