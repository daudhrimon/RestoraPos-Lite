package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.models.OrderInfo
import com.bdtask.restoraposlite.models.Payments

@Entity(tableName = "order_tbl")
data class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var status: Int,
    val split: Int,
    val merge: Int,
    var date: String,
    val token: String,
    var discount: Double,
    var vat: Double,
    var vatTotal: Double,
    var charge: Double,
    var chargeTotal: Double,
    var subTotal: Double,
    var grandTotal: Double,
    val operator: String,
    var orderInfo: OrderInfo,
    var cart: MutableList<Cart>,
    val payments: MutableList<Payments>
)
