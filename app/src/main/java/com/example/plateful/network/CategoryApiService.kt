package com.example.plateful.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET


interface CategoryApiService {
    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(): ApiCategoryList
}

fun CategoryApiService.getCategoriesAsFlow(): Flow<ApiCategoryList> = flow {
    try {
        emit(getCategories())
    } catch (e: Exception) {
        Log.e("API", "getCategoriesAsFlow: ${e.stackTraceToString()}")
    }
}