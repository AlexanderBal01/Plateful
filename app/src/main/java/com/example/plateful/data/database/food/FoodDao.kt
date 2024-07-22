package com.example.plateful.data.database.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: DbFood)

    @Query("UPDATE food SET favourite = :favourite WHERE id = :foodId")
    suspend fun setFavourite(foodId: String, favourite: Boolean)

    @Query("SELECT * FROM food WHERE favourite = true")
    fun getFavourites(): Flow<List<DbFood>>

    @Query("SELECT * FROM food WHERE id = :foodId")
    fun getItem(foodId: String): Flow<DbFood>

    @Query("SELECT * FROM food WHERE category = :name")
    fun getFoodCategory(name: String): Flow<List<DbFood>>
}