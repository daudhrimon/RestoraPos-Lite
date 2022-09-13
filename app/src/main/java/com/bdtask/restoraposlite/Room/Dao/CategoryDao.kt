package com.bdtask.restoraposlite.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.Catgry

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(catgry: Catgry)

    @Query("SELECT * FROM category_tbl")
    fun getAllCategory(): LiveData<List<Catgry>>

    @Update
    suspend fun updateCategory(catgry: Catgry)

    @Delete
    suspend fun deleteCategory(catgry: Catgry)

    @Query("SELECT fCat FROM category_tbl")
    fun getCategories(): LiveData<List<String>>
}