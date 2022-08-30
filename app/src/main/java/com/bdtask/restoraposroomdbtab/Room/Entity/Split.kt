package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import com.bdtask.restoraposroomdbtab.Model.CustomerInfo
import com.bdtask.restoraposroomdbtab.Model.FoodCart

@Entity(tableName = "split_tbl")
data class Split(
    val id: String,
    val ref: Long,
    val status: Int,
    val customerInfo: CustomerInfo,
    val cartList: List<FoodCart>
)
