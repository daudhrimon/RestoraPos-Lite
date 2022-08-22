package com.bdtask.restoraposroomdbtab.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bdtask.restoraposroomdbtab.Room.Entity.DeliveryCompany

@Dao
interface DeliveryCompanyDao {
    @Insert
    suspend fun insertDeliveryCompany(deliveryCompany: DeliveryCompany)

    @Query("SELECT * FROM delivery_company")
    fun getDeliveryCompany(): LiveData<List<DeliveryCompany>>
}