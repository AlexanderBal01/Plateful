package com.example.plateful

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plateful.data.database.PlatefulDb
import com.example.plateful.data.database.category.CategoryDao
import com.example.plateful.data.database.category.asDbCategoryObject
import com.example.plateful.data.database.category.asDomainCategoryObject
import com.example.plateful.model.Category
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var platefulDb: PlatefulDb

    private var category1 =
        Category(
            "1",
            "Beef",
            "https://www.themealdb.com/images/category/beef.png",
            "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times. Beef is a source of high-quality protein and essential nutrients.",
        )
    private var category2 =
        Category(
            "2",
            "Chicken",
            "https://www.themealdb.com/images/category/chicken.png",
            "Chicken is a type of domesticated fowl, a subspecies of the red junglefowl. It is one of the most common and widespread domestic animals, with a total population of more than 19 billion as of 2011.[1] Humans commonly keep chickens as a source of food (consuming both their meat and eggs) and, more rarely, as pets.",
        )

    private suspend fun addOneCategoryToDb() {
        categoryDao.insert(category1.asDbCategoryObject())
    }

    private suspend fun addTwoCategoriesToDb() {
        categoryDao.insert(category1.asDbCategoryObject())
        categoryDao.insert(category2.asDbCategoryObject())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        platefulDb = Room.inMemoryDatabaseBuilder(context, PlatefulDb::class.java).allowMainThreadQueries().build()

        categoryDao = platefulDb.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        platefulDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_InsertOneCategoryIntoDb() =
        runBlocking {
            addOneCategoryToDb()
            val allItems = categoryDao.getAllItems().first()
            Assert.assertEquals(allItems[0].asDomainCategoryObject(), category1)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetAllCategories_returnsAllCategoriesFromDB() =
        runBlocking {
            addTwoCategoriesToDb()
            val allItems = categoryDao.getAllItems().first()
            Assert.assertEquals(allItems[0].asDomainCategoryObject(), category1)
            Assert.assertEquals(allItems[1].asDomainCategoryObject(), category2)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetCategory_returnsOneCategoryFromDB() =
        runBlocking {
            addTwoCategoriesToDb()
            val item1 = categoryDao.getItem("Beef").first()
            val item2 = categoryDao.getItem("Chicken").first()
            Assert.assertEquals(item1.asDomainCategoryObject(), category1)
            Assert.assertEquals(item2.asDomainCategoryObject(), category2)
        }
}
