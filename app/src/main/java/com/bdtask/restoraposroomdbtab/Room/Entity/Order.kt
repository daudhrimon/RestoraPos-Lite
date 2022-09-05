package com.bdtask.restoraposroomdbtab.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposroomdbtab.Model.Cart
import com.bdtask.restoraposroomdbtab.Model.OdrInf
import com.bdtask.restoraposroomdbtab.Model.Pay

@Entity(tableName = "order_tbl")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var sts: Int,
    val spl: Int,
    val mrg: Int,
    var dat: String,
    val tkn: String,
    var dis: Double,
    var vat: Double,
    var crg: Double,
    var tPrc: Double,
    //val gtPrc: Double,
    val odrInf: OdrInf,
    val cart: MutableList<Cart>,
    val pay: MutableList<Pay>
    )
