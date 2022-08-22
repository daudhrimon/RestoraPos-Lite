package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposroomdbtab.Room.Entity.Customer

@Dao
interface CustomerDao {
    @Insert()
    suspend fun insertCustomer(customer: Customer)

    @Query("SELECT * FROM customer_tbl")
    fun getAllCustomer(): LiveData<List<Customer>>

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)
}