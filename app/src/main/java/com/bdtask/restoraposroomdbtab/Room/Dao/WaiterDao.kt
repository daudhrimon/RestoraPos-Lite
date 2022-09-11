package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposroomdbtab.Room.Entity.Waiter

@Dao
interface WaiterDao {
    @Insert
    suspend fun insertWaiter(waiter: Waiter)

    @Query("SELECT * FROM waiter_tbl")
    fun getAllWaiter(): LiveData<List<Waiter>>

    @Update
    suspend fun updateWaiter(waiter: Waiter)

    @Delete
    suspend fun deleteWaiter(waiter: Waiter)
}