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

/**
 * Interface defining the contract for managing food data.
 *
 * This interface provides methods for retrieving food items, managing favorites,
 * inserting new food items, and refreshing data from the network. It also exposes
 * the status of background work for data synchronization.
 */
interface FoodRepository {
    /**
     * Retrieves a specific food item by its ID.
     *
     * @param food The ID of the food item to retrieve.
     * @return A [Flow] emitting the [Food] if found, or null if not found.
     */
    fun getFood(food: String): Flow<Food?>

    /**
     * Retrieves all favorite food items.
     *
     * @return A [Flow] emitting a list of favorite [Food] items.
     */
    fun getFavourites(): Flow<List<Food>>

    /**
     * Retrieves all food items belonging to a specific category.
     *
     * @param category The category name to filter food items by.
     * @return A [Flow] emitting a list of [Food] items for the specified category.
     */
    fun getByCategory(category: String): Flow<List<Food>>

    /**
     * Inserts a new food item into the database.
     *
     * @param food The [Food] object to insert.
     */
    suspend fun insertFood(food: Food)

    /**
     * Sets the favorite status of a food item.
     *
     * @param food The ID of the food item.
     * @param favourite The new favorite status.
     */
    suspend fun setFavourite(
        food: String,
        favourite: Boolean,
    )

    /**
     * Refreshes the food data for a specific category by fetching it from the network
     * and updating the local database.
     *
     * @param category The category name to refresh food items for.
     */
    suspend fun refresh(category: String)

    /**
     * A [Flow] providing information about the background work status for data synchronization.
     */
    var wifiWorkInfo: Flow<WorkInfo>
}

/**
 * Implementation of [FoodRepository] that manages food data synchronization with a remote API
 * and caching in a local database.
 *
 * @param foodDao The [FoodDao] for accessing the local database.
 * @param foodApiService The [FoodApiService] for fetching food data from the network.
 * @param context The application context used for initializing the [WorkManager].
 */
class CashingFoodRepository(
    private val foodDao: FoodDao,
    private val foodApiService: FoodApiService,
    context: Context,
) : FoodRepository {
    private var workId = UUID(1, 2)
    private val workManager = WorkManager.getInstance(context)

    /**
     * A [Flow] emitting information about the background work status for data synchronization.
     */
    override var wifiWorkInfo: Flow<WorkInfo> = workManager.getWorkInfoByIdFlow(workId)

    /**
     * Retrieves a specific food item by its ID from the local database.
     *
     * @param food The ID of the food item to retrieve.
     * @return A [Flow] emitting the [Food] if found, or null if not found.
     */
    override fun getFood(food: String): Flow<Food?> =
        foodDao.getItem(foodId = food).map {
            it.asDomainFoodObject()
        }

    /**
     * Retrieves all favorite food items from the local database.
     *
     * @return A [Flow] emitting a list of favorite [Food] items.
     */
    override fun getFavourites(): Flow<List<Food>> =
        foodDao.getFavourites().map {
            it.asDomainFoodObjects()
        }

    /**
     * Retrieves all food items belonging to a specific category from the local database.
     *
     * @param category The category name to filter food items by.
     * @return A [Flow] emitting a list of [Food] items for the specified category.
     */
    override fun getByCategory(category: String): Flow<List<Food>> =
        foodDao.getFoodCategory(category).map {
            it.asDomainFoodObjects()
        }

    /**
     * Inserts a new food item into the local database.
     *
     * @param food The [Food] object to insert.
     */
    override suspend fun insertFood(food: Food) {
        foodDao.insert(food.asDbFoodObject())
    }

    /**
     * Sets the favorite status of a food item.
     *
     * @param food The ID of the food item.
     * @param favourite The new favorite status.
     */
    override suspend fun setFavourite(
        food: String,
        favourite: Boolean,
    ) {
        foodDao.setFavourite(food, favourite)
    }

    /**
     * Refreshes the food data for a specific category by fetching it from the network
     * and updating the local database. Initiates a background work request to handle
     * network operations and synchronize data.
     *
     * In case of a network timeout, logs the error stack trace.
     *
     * @param category The category name to refresh food items for.
     */
    override suspend fun refresh(category: String) {
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
