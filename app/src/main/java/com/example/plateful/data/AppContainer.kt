package com.example.plateful.data

import android.content.Context
import com.example.plateful.data.database.PlatefulDb
import com.example.plateful.network.CategoryApiService
import com.example.plateful.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val categoryRepository: CategoryRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val networkCheck = NetworkConnectionInterceptor(context)

    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val baseUrl = "https://www.themealdb.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()

    private val retrofitCategoryService: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    override val categoryRepository: CategoryRepository by lazy {
        CashingCategoryRepository(
            PlatefulDb.getDatabase(context = context).categoryDao(),
            retrofitCategoryService,
            context
        )
    }
}