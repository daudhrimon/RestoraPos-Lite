package com.bdtask.restoraposlite.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.Model.CsInf
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.Model.Pay

@Entity(tableName = "split_tbl")
data class Split(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val ref: Long,
    val sts: Int,
    var csInf: CsInf,
    val dis: Double,
    val vat: Double,
    val crg: Double,
    var tPrc: Double,
    //var gtPrc: Double,
    val cart: MutableList<Cart>,
    val pay: MutableList<Pay>
)
