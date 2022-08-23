package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.FoodCart
import com.bdtask.restoraposroomdbtab.Model.OrderInfo

@Entity(tableName = "order_tbl")
data class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var status: String,
    var date: String,
    var token: String,
    var cartList: List<FoodCart>,
    var orderInfoList: List<OrderInfo>
    )
