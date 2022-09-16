package com.bdtask.restoraposlite.Room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bdtask.restoraposlite.Model.Cart
import com.bdtask.restoraposlite.Model.OdrInf
import com.bdtask.restoraposlite.Model.Pay

@Entity(tableName = "merge_tbl")
data class Mrg (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val refs: MutableList<Long>,
    val sts: Int,
    val dat: String,
    val tkn: String,
    val dis: Double,
    val vat: Double,
    val crg: Double,
    val tPrc: Double,
    val odrInf: OdrInf,
    val cart: MutableList<Cart>,
    val pay: MutableList<Pay>
    )