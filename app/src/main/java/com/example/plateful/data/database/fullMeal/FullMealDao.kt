package com.example.plateful.data.database.fullMeal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FullMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbFullMeal)

    @Query("SELECT * FROM fullMeal WHERE id = :mealId")
    fun getItem(mealId: String): Flow<List<DbFullMeal>>
}