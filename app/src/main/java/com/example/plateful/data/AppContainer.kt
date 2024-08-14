package com.example.plateful.data

import android.content.Context
import com.example.plateful.data.database.PlatefulDb
import com.example.plateful.network.CategoryApiService
import com.example.plateful.network.FoodApiService
import com.example.plateful.network.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Interface representing the application container that provides access to various repositories.
 *
 * This interface acts as a provider for the repositories used within the application, such as
 * [CategoryRepository] and [FoodRepository].
 */
interface AppContainer {
    val categoryRepository: CategoryRepository
    val foodRepository: FoodRepository
}

/**
 * Default implementation of [AppContainer], providing the necessary setup for network communication
 * and database access.
 *
 * @property context The application context used for accessing resources and initializing the database.
 */
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

    private val retrofitFoodService: FoodApiService by lazy {
        retrofit.create(FoodApiService::class.java)
    }

    /**
     * Provides an instance of [CategoryRepository] with caching and network integration.
     */
    override val categoryRepository: CategoryRepository by lazy {
        CashingCategoryRepository(
            PlatefulDb.getDatabase(context = context).categoryDao(),
            retrofitCategoryService,
            context
        )
    }

    /**
     * Provides an instance of [FoodRepository] with caching and network integration.
     */
    override val foodRepository: FoodRepository by lazy {
        CashingFoodRepository(
            PlatefulDb.getDatabase(context = context).foodDao(),
            retrofitFoodService,
            context
        )
    }

}