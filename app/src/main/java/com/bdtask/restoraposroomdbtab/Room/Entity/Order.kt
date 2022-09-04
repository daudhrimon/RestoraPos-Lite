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
    val sts: Int,
    val spl: Int,
    val mrg: Int,
    val dat: String,
    val tkn: String,
    var dis: Double,
    var vat: Double,
    var crg: Double,
    //val tPrc: Double,
    //val gtPrc: Double,
    val odrInf: OdrInf,
    val cart: MutableList<Cart>,
    val pay: MutableList<Pay>
    )
