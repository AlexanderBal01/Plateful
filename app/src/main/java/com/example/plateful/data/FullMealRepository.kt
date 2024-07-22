package com.example.plateful.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.plateful.data.database.fullMeal.FullMealDao
import com.example.plateful.data.database.fullMeal.asDbFullMealObject
import com.example.plateful.data.database.fullMeal.asDomainFullMealObjects
import com.example.plateful.model.FullMeal
import com.example.plateful.network.FullMealApiService
import com.example.plateful.network.asDomainObject
import com.example.plateful.network.getMealByIdAsFlow
import com.example.plateful.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import java.util.UUID

interface FullMealRepository {
    fun getFullMeal(mealId: String): Flow<List<FullMeal>>

    suspend fun insertFullMeal(meal: FullMeal)

    suspend fun refresh(mealId: String)

    var wifiWorkInfo: Flow<WorkInfo>
}

class CashingFullMealRepository(
    private val fullMealDao: FullMealDao,
    private val fullMealApiService: FullMealApiService,
    context: Context
): FullMealRepository {
    private var workId = UUID(1,2)
    private val workManager = WorkManager.getInstance(context)

    override var wifiWorkInfo: Flow<WorkInfo> = workManager.getWorkInfoByIdFlow(workId)

    override fun getFullMeal(mealId: String): Flow<List<FullMeal>> {
        return fullMealDao.getItem(mealId).map {
            it.asDomainFullMealObjects()
        }
    }

    override suspend fun insertFullMeal(meal: FullMeal) {
        fullMealDao.insert(meal.asDbFullMealObject())
    }

    override suspend fun refresh(mealId: String) {
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
            fullMealApiService
                .getMealByIdAsFlow(mealId)
                .asDomainObject()
                .collect {
                    for (meal in it) {
                        insertFullMeal(meal)
                    }
                }
        } catch (e: SocketTimeoutException) {
            Log.e("REFRESH", "REFRESH: ${e.stackTraceToString()}")
        }
    }
}