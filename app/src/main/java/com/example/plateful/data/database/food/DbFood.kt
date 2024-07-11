package com.example.plateful.data.database.food

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plateful.model.Food

@Entity(tableName = "food")
data class DbFood(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val img: String = "",
    val favourite: Boolean = false,
    val category: String = ""
)

fun DbFood.asDomainFoodObject(): Food {
    return Food(
        id = this.id,
        name = this.name,
        imageUrl = this.img,
        favourite = this.favourite,
        category = this.category
    )
}

fun Food.asDbFoodObject(): DbFood {
    return DbFood(
        id = this.id,
        name = this.name,
        img = this.imageUrl,
        favourite = this.favourite,
        category = this.category
    )
}

fun List<DbFood>.asDomainFoodObjects(): List<Food> {
    return this.map {
        Food(
            id = it.id,
            name = it.name,
            imageUrl = it.img,
            favourite = it.favourite,
            category = it.category
        )
    }
}
