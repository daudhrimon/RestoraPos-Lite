package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposroomdbtab.Room.Entity.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM order_tbl")
    fun getTodayOrder(): LiveData<List<Order>>

    @Query("SELECT * FROM order_tbl WHERE date LIKE :date AND status LIKE :status")
    fun getTodayOrder(date: String?, status: String): LiveData<List<Order>>

    @Query("SELECT * FROM order_tbl WHERE status LIKE :status")
    fun getOngoing(status: Int): LiveData<List<Order>>

    @Update
    suspend fun updateOnGoing(order: Order)

    @Query("UPDATE order_tbl SET split=:status WHERE id = :id")
    suspend fun updateSplitStatus(status: Int, id: Long)

    @Delete
    suspend fun deleteOnGoing(order: Order)
}