package com.bdtask.restoraposlite.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bdtask.restoraposlite.Room.Entity.Cmpny

@Dao
interface CompanyDao {
    @Insert
    suspend fun insertDeliveryCompany(deliveryCmpny: Cmpny)

    @Query("SELECT * FROM company")
    fun getDeliveryCompany(): LiveData<List<Cmpny>>

    @Update
    suspend fun updateCompany(cmpny: Cmpny)

    @Delete
    suspend fun deleteCompany(cmpny: Cmpny)
}