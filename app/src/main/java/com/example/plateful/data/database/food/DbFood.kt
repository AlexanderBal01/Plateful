package com.example.plateful.data.database.food

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plateful.model.Food

/**
 * Entity class representing a food item in the local database.
 *
 * @property id The unique identifier for the food item, serving as the primary key.
 * @property name The name of the food item.
 * @property img The URL of the image associated with the food item.
 * @property favourite Indicates whether the food item is marked as a favourite.
 * @property category The category to which the food item belongs.
 */
@Entity(tableName = "food")
data class DbFood(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val img: String = "",
    val favourite: Boolean = false,
    val category: String = "",
)

/**
 * Extension function to convert a [DbFood] entity to a [Food] domain model object.
 *
 * @return A [Food] object representing the same data as the [DbFood] entity.
 */
fun DbFood.asDomainFoodObject(): Food =
    Food(
        id = this.id,
        name = this.name,
        imageUrl = this.img,
        favourite = this.favourite,
        category = this.category,
    )

/**
 * Extension function to convert a [Food] domain model object to a [DbFood] entity.
 *
 * @return A [DbFood] object representing the same data as the [Food] domain model.
 */
fun Food.asDbFoodObject(): DbFood =
    DbFood(
        id = this.id,
        name = this.name,
        img = this.imageUrl,
        favourite = this.favourite,
        category = this.category,
    )

/**
 * Extension function to convert a list of [DbFood] entities to a list of [Food] domain model objects.
 *
 * @return A list of [Food] objects representing the same data as the list of [DbFood] entities.
 */
fun List<DbFood>.asDomainFoodObjects(): List<Food> =
    this.map {
        Food(
            id = it.id,
            name = it.name,
            imageUrl = it.img,
            favourite = it.favourite,
            category = it.category,
        )
    }
