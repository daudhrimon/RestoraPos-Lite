package com.bdtask.restoraposlite.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.Model.OdrInf
import com.bdtask.restoraposlite.Model.Pay

@Entity(tableName = "order_tbl")
data class Order(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var sts: Int,
    val spl: Int,
    val mrg: Int,
    var dat: String,
    val tkn: String,
    var dis: Double,
    var vat: Double,
    var crg: Double,
    var tPrc: Double,
    val opr: String,
    var odrInf: OdrInf,
    var cart: MutableList<Cart>,
    val pay: MutableList<Pay>
    )
