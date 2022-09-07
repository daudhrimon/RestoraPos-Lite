package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposroomdbtab.Room.Entity.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM order_tbl WHERE dat LIKE :date AND sts LIKE :status")
    fun getTodayOrder(date: String?, status: Int): LiveData<MutableList<Order>>

    @Query("SELECT * FROM order_tbl WHERE sts LIKE :status")
    fun getOngoing(status: Int): LiveData<List<Order>>

    @Update
    suspend fun updateOnGoing(order: Order)

    @Query("UPDATE order_tbl SET spl=:status WHERE id = :id")
    suspend fun updateSplitStatus(status: Int, id: Long)

    @Delete
    suspend fun deleteOnGoing(order: Order)
}