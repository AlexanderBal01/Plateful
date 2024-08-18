package com.example.plateful.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

/**
 * Interface defining API endpoints for accessing category data.
 */
interface CategoryApiService {
    /**
     * Fetches a list of categories from the Plateful API.
     *
     * This function uses a GET request to the "/api/json/v1/1/categories.php" endpoint.
     * The response is expected to be a JSON object with a "categories" key containing an array
     * of objects representing individual categories.
     *
     * @return An `ApiCategoryList` object containing the list of categories.
     * @throws Exception If there is an error fetching data from the API.
     */
    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(): ApiCategoryList
}

/**
 * Converts the `getCategories` function of `CategoryApiService` to a flow of `ApiCategoryList`.
 *
 * This extension function wraps the `getCategories` suspend function in a flow builder.
 * It retrieves the list of categories and emits it as an `ApiCategoryList` object within the flow.
 * In case of an exception, it logs the error message.
 *
 * @receiver An instance of `CategoryApiService`.
 * @return A flow of `ApiCategoryList` representing the response from the API.
 */
fun CategoryApiService.getCategoriesAsFlow(): Flow<ApiCategoryList> =
    flow {
        try {
            emit(getCategories())
        } catch (e: Exception) {
            Log.e("API", "getCategoriesAsFlow: ${e.stackTraceToString()}")
        }
    }
