package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_company")
data class DeliveryCompany(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var companyName: String
)
