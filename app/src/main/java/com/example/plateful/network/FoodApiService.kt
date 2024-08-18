package com.example.plateful.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining API endpoints for accessing food data.
 */
interface FoodApiService {
    /**
     * Fetches a list of foods based on a specified category from the Plateful API.
     *
     * This function uses a GET request to the "/api/json/v1/1/filter.php" endpoint with a query parameter "c".
     * The value of the "c" parameter should be the category name for which you want to retrieve food items.
     * The response is expected to be a JSON object with a "meals" key containing an array of objects
     * representing individual food items.
     *
     * @param category The name of the category to filter by.
     * @return An `ApiFoodList` object containing the list of foods for the specified category.
     * @throws Exception If there is an error fetching data from the API.
     */
    @GET("api/json/v1/1/filter.php")
    suspend fun getFoodByCategory(
        @Query("c") category: String,
    ): ApiFoodList
}

/**
 * Converts the `getFoodByCategory` function of `FoodApiService` to a flow of `ApiFoodList`.
 *
 * This extension function wraps the `getFoodByCategory` suspend function in a flow builder.
 * It retrieves the list of foods for the provided category and emits it as an `ApiFoodList` object within the flow.
 * In case of an exception, it logs the error message.
 *
 * @receiver An instance of `FoodApiService`.
 * @param category The name of the category to filter by.
 * @return A flow of `ApiFoodList` representing the response from the API for the specified category.
 */
fun FoodApiService.getFoodsByCategoryAsFlow(category: String): Flow<ApiFoodList> =
    flow {
        try {
            emit(getFoodByCategory(category))
        } catch (e: Exception) {
            Log.e("API", "getFoodsByCategoryAsFlow: ${e.stackTraceToString()}")
        }
    }
