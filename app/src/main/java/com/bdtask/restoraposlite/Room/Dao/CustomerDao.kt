package com.bdtask.restoraposlite.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.Cstmr

@Dao
interface CustomerDao {
    @Insert()
    suspend fun insertCustomer(cstmr: Cstmr)

    @Query("SELECT * FROM customer_tbl")
    fun getAllCustomer(): LiveData<List<Cstmr>>

    @Update
    suspend fun updateCustomer(cstmr: Cstmr)

    @Delete
    suspend fun deleteCustomer(cstmr: Cstmr)
}