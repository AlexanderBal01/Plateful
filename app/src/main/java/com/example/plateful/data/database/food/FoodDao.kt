package com.example.plateful.data.database.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing the `DbFood` entity in the local database.
 * This interface provides methods for interacting with the food data stored in the database.
 */
@Dao
interface FoodDao {
    /**
     * Inserts a new food item into the database. If a food item with the same primary key already exists,
     * the insertion is ignored.
     *
     * @param item The [DbFood] entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DbFood)

    /**
     * Updates the favourite status of a food item in the database.
     *
     * @param foodId The unique identifier of the food item to be updated.
     * @param favourite The new favourite status to be set for the food item.
     */
    @Query("UPDATE food SET favourite = :favourite WHERE id = :foodId")
    suspend fun setFavourite(
        foodId: String,
        favourite: Boolean,
    )

    /**
     * Retrieves all food items marked as favourites from the database.
     *
     * @return A [Flow] emitting a list of [DbFood] entities that are marked as favourites.
     */
    @Query("SELECT * FROM food WHERE favourite = true")
    fun getFavourites(): Flow<List<DbFood>>

    /**
     * Retrieves a specific food item by its ID.
     *
     * @param foodId The unique identifier of the food item to retrieve.
     * @return A [Flow] emitting the [DbFood] entity that matches the specified ID.
     */
    @Query("SELECT * FROM food WHERE id = :foodId")
    fun getItem(foodId: String): Flow<DbFood>

    /**
     * Retrieves all food items that belong to a specific category.
     *
     * @param name The name of the category.
     * @return A [Flow] emitting a list of [DbFood] entities that belong to the specified category.
     */
    @Query("SELECT * FROM food WHERE category = :name")
    fun getFoodCategory(name: String): Flow<List<DbFood>>
}
