package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.Model.OrderInfo

@Entity(tableName = "order_tbl")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val status: Int,
    val split: Int,
    val merge: Int,
    val date: String,
    val token: String,
    val orderInfo: OrderInfo,
    val cartList: List<FoodCart>
    )
