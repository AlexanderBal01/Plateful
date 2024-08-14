package com.example.plateful.data.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for managing the `DbCategory` entity in the local database.
 * This interface provides methods for interacting with the category data stored in the database.
 */
@Dao
interface CategoryDao {
    /**
     * Inserts a new category into the database. If a category with the same primary key already exists,
     * the insertion is ignored.
     *
     * @param item The [DbCategory] entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DbCategory)

    /**
     * Retrieves all categories from the database.
     *
     * @return A [Flow] emitting a list of all [DbCategory] entities in the database.
     */
    @Query("SELECT * FROM category")
    fun getAllItems(): Flow<List<DbCategory>>

    /**
     * Retrieves a specific category by its name.
     *
     * @param name The name of the category to retrieve.
     * @return A [Flow] emitting the [DbCategory] entity that matches the specified name.
     */
    @Query("SELECT * FROM category WHERE name = :name")
    fun getItem(name: String): Flow<DbCategory>
}