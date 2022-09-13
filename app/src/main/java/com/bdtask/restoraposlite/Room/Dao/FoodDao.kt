package com.bdtask.restoraposlite.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.Food

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(food: Food)

    @Query("SELECT * FROM food_tbl")
    fun getAllFood(): LiveData<List<Food>>

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT fTitle FROM food_tbl")
    fun getFoodTitle(): LiveData<List<String>>
}