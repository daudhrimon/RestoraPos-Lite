package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bdtask.restoraposroomdbtab.Room.Entity.Cmpny

@Dao
interface CompanyDao {
    @Insert
    suspend fun insertDeliveryCompany(deliveryCmpny: Cmpny)

    @Query("SELECT * FROM company")
    fun getDeliveryCompany(): LiveData<List<Cmpny>>
}