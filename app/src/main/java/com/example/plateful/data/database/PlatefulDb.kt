package com.example.plateful.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plateful.data.database.category.CategoryDao
import com.example.plateful.data.database.category.DbCategory
import com.example.plateful.data.database.food.DbFood
import com.example.plateful.data.database.food.FoodDao

/**
 * Main database class for the Plateful app.
 * This class serves as the database holder and the main access point for the underlying connection to the app's SQLite database.
 *
 * It is an abstract class that extends [RoomDatabase], and it provides DAOs (Data Access Objects)
 * for interacting with the app's database tables.
 *
 * @property categoryDao Provides access to CRUD operations for the `DbCategory` table.
 * @property foodDao Provides access to CRUD operations for the `DbFood` table.
 */
@Database(entities = [DbCategory::class, DbFood::class], version = 5, exportSchema = false)
abstract class PlatefulDb : RoomDatabase() {
    /**
     * Provides access to the [CategoryDao] for performing operations on the `DbCategory` table.
     *
     * @return [CategoryDao] object to interact with the `DbCategory` table.
     */
    abstract fun categoryDao(): CategoryDao

    /**
     * Provides access to the [FoodDao] for performing operations on the `DbFood` table.
     *
     * @return [FoodDao] object to interact with the `DbFood` table.
     */
    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var instance: PlatefulDb? = null

        /**
         * Returns a singleton instance of [PlatefulDb].
         *
         * This method ensures that only one instance of the database is created, even when multiple
         * threads are accessing it simultaneously. It uses the synchronized block to prevent the
         * creation of multiple instances.
         *
         * @param context The application context used to build the database.
         * @return The singleton instance of [PlatefulDb].
         */
        fun getDatabase(context: Context): PlatefulDb =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, PlatefulDb::class.java, "plateful_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}
