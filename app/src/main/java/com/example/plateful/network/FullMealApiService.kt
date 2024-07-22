package com.example.plateful.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface FullMealApiService {
    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): ApiFullMealList
}

fun FullMealApiService.getMealByIdAsFlow(id: String): Flow<ApiFullMealList> = flow {
    try {
        Log.i("API", "getMealByIdAsFlow: $id")
        emit(getMealById(id))
    } catch (e: Exception) {
        Log.e("API", "getMealByIdAsFlow: ${e.stackTraceToString()}")
    }
}