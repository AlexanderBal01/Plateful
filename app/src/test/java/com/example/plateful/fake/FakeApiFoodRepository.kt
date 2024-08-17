package com.example.plateful.fake

import androidx.work.WorkInfo
import com.example.plateful.data.FoodRepository
import com.example.plateful.model.Food
import kotlinx.coroutines.flow.Flow

class FakeApiFoodRepository : FoodRepository {
    override fun getFood(food: String): Flow<Food?> {
        TODO("Not yet implemented")
    }

    override fun getFavourites(): Flow<List<Food>> {
        TODO("Not yet implemented")
    }

    override fun getByCategory(category: String): Flow<List<Food>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFood(food: Food) {
        TODO("Not yet implemented")
    }

    override suspend fun setFavourite(food: String, favourite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(category: String) {
        TODO("Not yet implemented")
    }

    override var wifiWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")
        set(value) {}
}