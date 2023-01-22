package com.bdtask.restoraposlite.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.models.CustomerInfo
import com.bdtask.restoraposlite.models.Cart
import com.bdtask.restoraposlite.models.Payments

@Entity(tableName = "split_tbl")
data class Split(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val refer: Long,
    val status: Int,
    var customerInfo: CustomerInfo,
    val discount: Double,
    val vat: Double,
    val charge: Double,
    var subTotal: Double,
    //var gtPrc: Double,
    val cart: MutableList<Cart>,
    val payments: MutableList<Payments>
)
