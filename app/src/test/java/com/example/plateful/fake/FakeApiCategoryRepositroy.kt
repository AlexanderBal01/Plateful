package com.example.plateful.fake

import androidx.work.WorkInfo
import com.example.plateful.data.CategoryRepository
import com.example.plateful.model.Category
import kotlinx.coroutines.flow.Flow

class FakeApiCategoryRepositroy : CategoryRepository {
    override fun getCategory(category: String): Flow<Category?> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }

    override var wifiWorkInfo: Flow<WorkInfo>
        get() = TODO("Not yet implemented")
        set(value) {}
}
