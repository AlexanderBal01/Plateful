package com.example.plateful.data.database.fullMeal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plateful.model.FullMeal

@Entity(tableName = "fullMeal")
data class DbFullMeal (
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val instructions: String = "",
    val image: String = "",
)

fun DbFullMeal.asDomainFullMealObject(): FullMeal {
    return FullMeal(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        image = this.image,
    )
}

fun FullMeal.asDbFullMealObject(): DbFullMeal {
    return DbFullMeal(
        id = this.id,
        name = this.name,
        instructions = this.instructions,
        image = this.image,
    )
}

fun List<DbFullMeal>.asDomainFullMealObjects(): List<FullMeal> {
    return this.map {
        FullMeal(
            id = it.id,
            name = it.name,
            instructions = it.instructions,
            image = it.image,
        )
    }
}