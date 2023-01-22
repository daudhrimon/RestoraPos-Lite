package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.models.OrderInfo
import com.bdtask.restoraposlite.models.Payments

@Entity(tableName = "merge_tbl")
data class Merge(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val refer: MutableList<Long>,
    val status: Int,
    val date: String,
    val token: String,
    val discount: Double,
    val vat: Double,
    val charge: Double,
    val subTotal: Double,
    val orderInfo: OrderInfo,
    val cart: MutableList<Cart>,
    val payments: MutableList<Payments>
)