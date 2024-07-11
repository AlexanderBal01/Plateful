package com.example.plateful.data

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.plateful.data.database.category.CategoryDao
import com.example.plateful.data.database.category.asDbCategoryObject
import com.example.plateful.data.database.category.asDomainCategoryObject
import com.example.plateful.data.database.category.asDomainCategoryObjects
import com.example.plateful.model.Category
import com.example.plateful.network.CategoryApiService
import com.example.plateful.network.asDomainObject
import com.example.plateful.network.getCategoriesAsFlow
import com.example.plateful.workerUtils.WifiNotificationWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.SocketTimeoutException
import java.util.UUID

interface CategoryRepository {
    fun getCategory(category: String): Flow<Category?>

    fun getAll(): Flow<List<Category>>

    suspend fun insertCategory(category: Category)

    suspend fun refresh()

    var wifiWorkInfo: Flow<WorkInfo>
}

class CashingCategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryApiService: CategoryApiService,
    context: Context
): CategoryRepository {
    private var workId = UUID(1,2)
    private val workManager = WorkManager.getInstance(context)

    override var wifiWorkInfo: Flow<WorkInfo> = workManager.getWorkInfoByIdFlow(workId)

    override fun getCategory(category: String): Flow<Category?> {
        return categoryDao.getItem(category).map {
            it.asDomainCategoryObject()
        }
    }

    override fun getAll(): Flow<List<Category>> {
        return categoryDao.getAllItems().map {
            it.asDomainCategoryObjects()
        }
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insert(category.asDbCategoryObject())
    }

    override suspend fun refresh() {
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
            categoryApiService
                .getCategoriesAsFlow()
                .asDomainObject()
                .collect {
                    for (category in it) {
                        insertCategory(category)
                    }
                }
        } catch (e: SocketTimeoutException) {
            Log.e("REFRESH", "REFRESH: ${e.stackTraceToString()}")
        }
    }
}