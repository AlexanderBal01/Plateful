package com.example.plateful.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("api/json/v1/1/filter.php")
    suspend fun getFoodByCategory(
        @Query("c") category: String
    ): ApiFoodList
}

fun FoodApiService.getFoodsByCategoryAsFlow(category: String): Flow<ApiFoodList> = flow {
    try {
        emit(getFoodByCategory(category))
    } catch (e: Exception) {
        Log.e("API", "getFoodsByCategoryAsFlow: ${e.stackTraceToString()}")
    }
}