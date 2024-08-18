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

/**
 * Interface defining the contract for category data management.
 *
 * This interface provides methods for retrieving, inserting, and refreshing categories,
 * as well as monitoring the status of background work for data synchronization.
 */
interface CategoryRepository {
    /**
     * Retrieves a specific category by its name.
     *
     * @param category The name of the category to retrieve.
     * @return A [Flow] emitting the [Category] if found, or null if not found.
     */
    fun getCategory(category: String): Flow<Category?>

    /**
     * Retrieves all categories.
     *
     * @return A [Flow] emitting a list of all [Category] objects.
     */
    fun getAll(): Flow<List<Category>>

    /**
     * Inserts a new category into the database.
     *
     * @param category The [Category] object to insert.
     */
    suspend fun insertCategory(category: Category)

    /**
     * Refreshes the category data by fetching it from the network and updating the database.
     * Initiates a background work request to handle network operations.
     */
    suspend fun refresh()

    /**
     * A [Flow] providing information about the background work for category data synchronization.
     */
    var wifiWorkInfo: Flow<WorkInfo>
}

/**
 * Implementation of [CategoryRepository] that handles data synchronization with a remote API
 * and caching in a local database.
 *
 * @param categoryDao The [CategoryDao] for accessing the local database.
 * @param categoryApiService The [CategoryApiService] for fetching category data from the network.
 * @param context The application context used for initializing the [WorkManager].
 */
class CashingCategoryRepository(
    private val categoryDao: CategoryDao,
    private val categoryApiService: CategoryApiService,
    context: Context,
) : CategoryRepository {
    private var workId = UUID(1, 2)
    private val workManager = WorkManager.getInstance(context)

    /**
     * A [Flow] emitting information about the background work status for data synchronization.
     */
    override var wifiWorkInfo: Flow<WorkInfo> = workManager.getWorkInfoByIdFlow(workId)

    /**
     * Retrieves a specific category by its name from the local database.
     *
     * @param category The name of the category to retrieve.
     * @return A [Flow] emitting the [Category] if found, or null if not found.
     */
    override fun getCategory(category: String): Flow<Category?> =
        categoryDao.getItem(category).map {
            it.asDomainCategoryObject()
        }

    /**
     * Retrieves all categories from the local database.
     *
     * @return A [Flow] emitting a list of all [Category] objects.
     */
    override fun getAll(): Flow<List<Category>> =
        categoryDao.getAllItems().map {
            it.asDomainCategoryObjects()
        }

    /**
     * Inserts a new category into the local database.
     *
     * @param category The [Category] object to insert.
     */
    override suspend fun insertCategory(category: Category) {
        categoryDao.insert(category.asDbCategoryObject())
    }

    /**
     * Refreshes the category data by fetching it from the network and updating the local database.
     * Initiates a background work request to handle network operations and synchronize data.
     *
     * In case of a network timeout, logs the error stack trace.
     */
    override suspend fun refresh() {
        val constraints =
            Constraints
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
