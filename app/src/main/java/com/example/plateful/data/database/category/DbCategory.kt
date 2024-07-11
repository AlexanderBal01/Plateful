package com.example.plateful.data.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.plateful.model.Category

@Entity(tableName = "category")
data class DbCategory(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val img: String = ""
)

fun DbCategory.asDomainCategoryObject(): Category {
    return Category(
        id = this.id,
        name = this.name,
        imageUrl = this.img,
        description = this.description
    )
}

fun Category.asDbCategoryObject(): DbCategory {
    return DbCategory(
        id = this.id,
        name = this.name,
        img = this.imageUrl,
        description = this.description
    )
}

fun List<DbCategory>.asDomainCategoryObjects(): List<Category> {
    return this.map {
        Category(
            id = it.id,
            name = it.name,
            imageUrl = it.img,
            description = it.description
        )
    }
}