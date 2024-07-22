package com.example.plateful.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.plateful.data.database.food.FoodDao
import com.example.plateful.data.database.food.asDbFoodObject
import com.example.plateful.data.database.food.asDomainFoodObject
import com.example.plateful.data.database.food.asDomainFoodObjects
import com.example.plateful.model.Food
import com.example.plateful.network.FoodApiService
import com.example.plateful.network.asDomainObject
import com.example.plateful.network.getFoodsByCategoryAsFlow
import com.example.plateful.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import java.util.UUID

interface FoodRepository {
    fun getFood(food: String): Flow<Food?>

    fun getFavourites(): Flow<List<Food>>

    fun getByCategory(category: String): Flow<List<Food>>

    suspend fun insertFood(food: Food)

    suspend fun setFavourite(food: String, favourite: Boolean)

    suspend fun refresh(category: String)

    var wifiWorkInfo: Flow<WorkInfo>
}

class CashingFoodRepository(
    private val foodDao: FoodDao,
    private val foodApiService: FoodApiService,
    context: Context
): FoodRepository {
    private var workId = UUID(1,2)
    private val workManager = WorkManager.getInstance(context)

    override var wifiWorkInfo: Flow<WorkInfo> = workManager.getWorkInfoByIdFlow(workId)

    override fun getFood(food: String): Flow<Food?> {
        return foodDao.getItem(foodId = food).map {
            it.asDomainFoodObject()
        }
    }

    override fun getFavourites(): Flow<List<Food>> {
        return foodDao.getFavourites().map {
            it.asDomainFoodObjects()
        }
    }

    override fun getByCategory(category: String): Flow<List<Food>> {
        return foodDao.getFoodCategory(category).map {
            it.asDomainFoodObjects()
        }
    }

    override suspend fun insertFood(food: Food) {
        foodDao.insert(food.asDbFoodObject())
    }

    override suspend fun setFavourite(food: String, favourite: Boolean) {
        foodDao.setFavourite(food, favourite)
    }

    override suspend fun refresh(category: String) {
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val requestBuilder = OneTimeWorkRequestBuilder<WifiNotificationWorker>()
        val request = requestBuilder.setConstraints(constraints).build()
        workManager.enqueue(request)
        workId = request.id
        wifiWorkInfo = workManager.getWorkInfoByIdFlow(request.id)

        try {
            foodApiService
                .getFoodsByCategoryAsFlow(category)
                .asDomainObject()
                .collect {
                    for (food in it) {
                        val newFood = food.copy(category = category)
                        insertFood(newFood)
                    }
                }
        } catch (e: SocketTimeoutException) {
            Log.e("REFRESH", "REFRESH: ${e.stackTraceToString()}")
        }
    }
}