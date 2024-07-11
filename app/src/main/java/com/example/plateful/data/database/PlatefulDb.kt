package com.example.plateful.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plateful.data.database.category.CategoryDao
import com.example.plateful.data.database.category.DbCategory

@Database(entities = [DbCategory::class], version = 2, exportSchema = false)
abstract class PlatefulDb: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var Instance: PlatefulDb? = null

        fun getDatabase(context: Context): PlatefulDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PlatefulDb::class.java, "plateful_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}