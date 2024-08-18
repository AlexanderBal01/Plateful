package com.example.plateful

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plateful.data.database.PlatefulDb
import com.example.plateful.data.database.food.FoodDao
import com.example.plateful.data.database.food.asDbFoodObject
import com.example.plateful.data.database.food.asDomainFoodObject
import com.example.plateful.model.Food
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FoodDaoTest {
    private lateinit var foodDao: FoodDao
    private lateinit var platefulDb: PlatefulDb

    private var food1 =
        Food(
            "1",
            "15-minute chicken & halloumi burgers",
            "https://www.themealdb.com/images/media/meals/vdwloy1713225718.jpg",
            false,
            "Chicken",
        )
    private var food2 =
        Food("2", "Chick-Fil-A Sandwich", "https://www.themealdb.com/images/media/meals/sbx7n71587673021.jpg", true, "Chicken")

    private suspend fun addOneFoodToDb() {
        foodDao.insert(food1.asDbFoodObject())
    }

    private suspend fun addTwoFoodsToDb() {
        foodDao.insert(food1.asDbFoodObject())
        foodDao.insert(food2.asDbFoodObject())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        platefulDb = Room.inMemoryDatabaseBuilder(context, PlatefulDb::class.java).allowMainThreadQueries().build()

        foodDao = platefulDb.foodDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        platefulDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_InsertOneFoodIntoDb() =
        runBlocking {
            addOneFoodToDb()
            val allItems = foodDao.getFoodCategory("Chicken").first()
            Assert.assertEquals(allItems[0].asDomainFoodObject(), food1)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetFoodCategory_returnsAllFoodsByCategoryFromDB() =
        runBlocking {
            addTwoFoodsToDb()
            val allItems = foodDao.getFoodCategory("Chicken").first()
            Assert.assertEquals(allItems[0].asDomainFoodObject(), food1)
            Assert.assertEquals(allItems[1].asDomainFoodObject(), food2)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsOneFoodItemByIdFromDB() =
        runBlocking {
            addTwoFoodsToDb()
            val item1 = foodDao.getItem("1").first()
            val item2 = foodDao.getItem("2").first()

            Assert.assertEquals(item1.asDomainFoodObject(), food1)
            Assert.assertEquals(item2.asDomainFoodObject(), food2)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetFavourites_returnsFavouriteFoodsFromDB() =
        runBlocking {
            addTwoFoodsToDb()
            val allItems = foodDao.getFavourites().first()

            Assert.assertEquals(allItems[0].asDomainFoodObject(), food2)
        }

    @Test
    @Throws(Exception::class)
    fun daoSetFavourite_SetFoodToFavourite() =
        runBlocking {
            addOneFoodToDb()
            foodDao.setFavourite("1", true)
            val allItems1 = foodDao.getFavourites().first()

            Assert.assertEquals(allItems1.size, 1)

            foodDao.setFavourite("1", false)
            val allItems2 = foodDao.getFavourites().first()

            Assert.assertEquals(allItems2.size, 0)
        }
}
