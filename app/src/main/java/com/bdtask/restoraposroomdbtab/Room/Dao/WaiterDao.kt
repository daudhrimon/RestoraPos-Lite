package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bdtask.restoraposroomdbtab.Room.Entity.Waiter

@Dao
interface WaiterDao {
    @Insert
    suspend fun insertWaiter(waiter: Waiter)

    @Query("SELECT * FROM waiter_tbl")
    fun getAllWaiter(): LiveData<List<Waiter>>
}