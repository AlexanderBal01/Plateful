package com.example.plateful.data.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DbCategory)

    @Query("SELECT * FROM category")
    fun getAllItems(): Flow<List<DbCategory>>

    @Query("SELECT * FROM category WHERE name = :name")
    fun getItem(name: String): Flow<DbCategory>
}